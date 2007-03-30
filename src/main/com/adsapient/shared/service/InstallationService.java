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

import com.adsapient.shared.exceptions.PluginInstallerExeption;
import com.adsapient.shared.mappable.InstallItem;
import com.adsapient.shared.service.CookieManagementService;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;
import com.adsapient.gui.ContextAwareGuiBean;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class InstallationService {
    private static Logger logger = Logger.getLogger(InstallationService.class);

    public static void updateTables() throws Exception {
        executeScriptsIn("/update/sql");
    }

    public static boolean createTables()
            throws Exception {
        return executeScriptsIn("/setup/sql");
    }

    public static boolean executeScriptsIn(String pathToSqlFiles) throws Exception {
        Collection sqlRequests = new ArrayList();
        String extension = ".sql";
        String[] sqlFiles = new String[]{};
        List<String> fileNamesList = new ArrayList<String>();

        try {
            File f = new File(InstallationService.class.getResource(
                    pathToSqlFiles).getFile());

            if (f.getPath().indexOf(".jar") != -1) {
                String pathToJarFile = f.getPath().substring(6,
                        f.getPath().indexOf(".jar") + 4);
                fileNamesList = listFileNamesInJar(pathToJarFile,
                        pathToSqlFiles, extension);
            } else {
                f.toURI();

                File[] ffs = f.listFiles();

                for (File file : ffs) {
                    if (file.getName().endsWith(".sql")) {
                        fileNamesList.add(file.getName());
                    }
                }
            }

            sqlFiles = fileNamesList.toArray(new String[fileNamesList.size()]);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        for (int fileCount = 0; fileCount < sqlFiles.length; fileCount++) {
            logger.info("parsing ..." + sqlFiles[fileCount]);

            InputStream stream = InstallationService.class.getClassLoader()
                    .getResourceAsStream(
                            pathToSqlFiles + "/" + sqlFiles[fileCount]);
            if (stream == null) {
                URL u = InstallationService.class.getResource(pathToSqlFiles + "/" + sqlFiles[fileCount]);
                stream = u.openStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    stream));
            String str;

            try {
                while ((str = reader.readLine()) != null) {
                    if (str.startsWith("#")
                            || (AdsapientConstants.EMPTY.equalsIgnoreCase(str))) {
                        continue;
                    }

                    StringBuffer buffer = new StringBuffer();
                    buffer.append(str);

                    if (str.indexOf(";") > -1) {
                        sqlRequests.add(str);

                        continue;
                    }

                    String str2;

                    while ((str2 = reader.readLine()) != null) {
                        if (str2.startsWith("#")) {
                            continue;
                        }

                        buffer.append(str2);

                        if (str2.indexOf(";") > -1) {
                            break;
                        }
                    }

                    sqlRequests.add(buffer.toString());
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            JDBCService.test(sqlRequests);
        } catch (Exception e) {
            logger.info("Problem with database acces" + e.getMessage(), e);
            throw new PluginInstallerExeption("Problem with database acces"
                    + e.getMessage());
        }

        return true;
    }

    private static List<String> listFileNamesInJar(String pathToJar,
                                                   String pathToFolderInJar, String extension) {
        CharSequence linux1 = "linux";
        CharSequence linux2 = "unix";

        if (System.getProperty("os.name").toLowerCase().contains(linux1) ||
                System.getProperty("os.name").toLowerCase().contains(linux2)) {
            pathToJar = "/" + pathToJar;
        }

        if (pathToFolderInJar.startsWith("/")) {
            pathToFolderInJar = pathToFolderInJar.substring(1,
                    pathToFolderInJar.length());
        }

        List<String> l = new ArrayList<String>();

        try {
            JarFile zf = new JarFile(pathToJar);
            Enumeration entries = zf.entries();

            while (entries.hasMoreElements()) {
                JarEntry ze = (JarEntry) entries.nextElement();

                if (ze.getName().startsWith(pathToFolderInJar)
                        && ze.getName().endsWith(extension)) {
                    String fileName = ze.getName().substring(pathToFolderInJar.length(), ze.getName().length());
                    if (fileName.startsWith("/")) fileName = fileName.substring(1, fileName.length());
                    l.add(fileName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return l;
    }

    public static boolean setup(ActionServlet actionServlet) {
        HibernateEntityDao hibernateEntityDao =  (HibernateEntityDao) ContextAwareGuiBean.getContext().getBean("hibernateEntityDao");
        Collection setupCommands = new ArrayList();

        if (new File("banners/resource/GeoIP.dat").exists()) {
            logger.info("The application files is correct. Skip installation ");

            return false;
        }

        Collection installItems = hibernateEntityDao.viewAll(InstallItem.class);
        logger.info("Begin application installetion..");

        Iterator itemsIterator = installItems.iterator();

        while (itemsIterator.hasNext()) {
            InstallItem item = (InstallItem) itemsIterator.next();

            if (InstallItem.COMMAND_COPY.equalsIgnoreCase(item.getCommand())) {
                try {
                    InputStream in = actionServlet.getServletContext()
                            .getResourceAsStream(item.getServerURL());

                    if (in == null) {
                        logger.error("Couldn't locate on server:"
                                + item.getServerURL());

                        continue;
                    }

                    OutputStream out = new FileOutputStream(item.getFilePath());

                    int bytesRead = 0;

                    while ((bytesRead = in.read()) != -1) {
                        out.write(bytesRead);
                    }

                    in.close();
                    out.close();
                } catch (IOException e) {
                    logger.error("Error while setup application data", e);
                }

                logger.info("Copy file from " + item.getServerURL() + " to "
                        + item.getFilePath());
            }

            if (InstallItem.COMMAND_MKDIR.equalsIgnoreCase(item.getCommand())) {
                logger.info("execute mkdir command");
                new File(item.getFilePath()).mkdirs();
                logger.info("success create directorys " + item.getFilePath());
            }
        }

        logger.info("Finish application installer");

        return true;
    }
}
