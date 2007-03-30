/*
 * AdSapient - Open Source Ad Server
 * http://www.sourceforge.net/projects/adsapient
 * http://www.adsapient.com
 *
 * Copyright (C) 2001-06 Vitaly Sazanovich
 * Vitaly.Sazanovich@gmail.com
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Library General Public License  as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */
package com.adsapient.shared.service;

import com.adsapient.gui.forms.BannerUploadActionForm;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.shared.mappable.*;
import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class UploadService {
    private static Logger logger = Logger.getLogger(UploadService.class);
    private HibernateEntityDao hibernateEntityDao;
    private FiltersService filtersService;

    public static String readFile(File file) {
        StringBuffer contents = new StringBuffer();

        BufferedReader input = null;

        try {
            input = new BufferedReader(new FileReader(file));

            char[] buffer = new char[2048];

            int length = 0;

            while ((length = input.read(buffer)) != -1) {
                String test = new String(buffer);

                contents.append(buffer);
            }
        } catch (FileNotFoundException ex) {
            logger.error("Cant find file " + file, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return contents.toString();
    }

    public static void removeAllUsersFile(UserImpl user) {
        File userHomeDirectory = new File("banners/" + user.getId());

        if (userHomeDirectory.exists()) {
            File[] filesList = userHomeDirectory.listFiles();

            for (int fileIndex = 0; fileIndex < filesList.length; fileIndex++) {
                filesList[fileIndex].delete();
            }

            userHomeDirectory.delete();
        }
    }

    public void removeBanner(Integer bannerId) {
        BannerImpl ban = (BannerImpl) hibernateEntityDao.loadObjectWithCriteria(
                BannerImpl.class, "bannerId", bannerId);

        if (ban != null) {

            hibernateEntityDao.removeObject(ban);
            filtersService.removeAllFiltersForGivenBanner(bannerId);

            if (ban.getRateId() != null) hibernateEntityDao.removeObject(RateImpl.class, ban.getRateId());
        } else {
            logger.error("Error in removeBanner :can't load  banner with id="
                    + bannerId);
        }
    }

    public static void removeFile(String filePath) {
        File file2Remove = new File(filePath);

        if (!file2Remove.exists()) {
            logger.warn("WARNING in remove file : file-" + filePath
                    + "dont exist");
        } else {
            file2Remove.delete();
            logger.debug("file " + filePath + "was removed from system");
        }
    }

    public static String saveDefaultBannerFile(FormFile file) {
        String data = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream stream = file.getInputStream();

            StringBuffer sb = new StringBuffer();
//            sb.append(ApplicationManagment.application).append("/banners/")
//                    .append("default");

            File tempFile = new File(sb.toString());

            tempFile.mkdirs();

            OutputStream bos = new FileOutputStream(sb.append("/").append(
                    file.getFileName()).toString());

            int bytesRead = 0;
            byte[] buffer = new byte[8192];

            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.close();

            stream.close();

            return sb.toString();
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe.getMessage());

            return "";
        } catch (IOException ioe) {
            System.out.println("Exception in save file");
            System.err.println(ioe.getMessage());

            return "";
        }
    }

    public static void saveFile(BannerUploadActionForm form) {
        String data = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream stream = form.getTheFile().getInputStream();

            String path = AdsapientConstants.PATH_TO_BANNERS + form.getUserId();
            File tempFile = new File(path);

            tempFile.mkdirs();

            OutputStream bos = new FileOutputStream(path + "/"
                    + form.getBannerId());

            form.setFile(form.getUserId() + "/" + form.getBannerId());

            int bytesRead = 0;
            byte[] buffer = new byte[8192];

            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bos.close();

            stream.close();
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Exception in save file");
            System.err.println(ioe.getMessage());
        }
    }

    public Collection viewCampainBanners(Integer campainId,
                                         boolean enableTransformation) {
        Collection banners = new ArrayList();

        banners = hibernateEntityDao.viewWithCriteria(BannerImpl.class,
                "campainId", campainId, "bannerId");

        if (!enableTransformation) {
            return banners;
        }

        Collection transformetedBannersCollection = new ArrayList();
        Iterator bannersIterator = banners.iterator();

        while (bannersIterator.hasNext()) {
            BannerImpl userBanner = (BannerImpl) bannersIterator.next();
            userBanner.setSize((Size) hibernateEntityDao.loadObject(Size.class,
                    userBanner.getSizeId()));

            transformetedBannersCollection.add(new BannerRepresentation(
                    userBanner));
        }

        return transformetedBannersCollection;
    }

    public Collection viewUserBanners(Class objClass, Integer userId,
                                      boolean enableTransformation) {
        Collection bannersCollection = new ArrayList();

        bannersCollection = hibernateEntityDao.viewWithCriteria(objClass,
                "userId", userId, "bannerId");

        if (!enableTransformation) {
            return bannersCollection;
        }

        Collection transformetedBannersCollection = new ArrayList();
        Iterator bannersIterator = bannersCollection.iterator();

        while (bannersIterator.hasNext()) {
            Object userObject = bannersIterator.next();

            if (userObject instanceof ResourceImpl) {
                continue;
            }

            BannerImpl userBanner = (BannerImpl) userObject;
            userBanner.setSize((Size) hibernateEntityDao.loadObject(Size.class,
                    userBanner.getSizeId()));

            transformetedBannersCollection.add(new BannerRepresentation(
                    userBanner));
        }

        return transformetedBannersCollection;
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }

    public FiltersService getFiltersService() {
        return filtersService;
    }

    public void setFiltersService(FiltersService filtersService) {
        this.filtersService = filtersService;
    }
}
