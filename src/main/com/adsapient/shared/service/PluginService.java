package com.adsapient.shared.service;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Document;
import com.adsapient.api.Banner;
import com.adsapient.api.AdsapientException;
import com.adsapient.gui.forms.BannerUploadActionForm;
import com.adsapient.gui.forms.PluginManagerActionForm;
import com.adsapient.shared.mappable.Type;
import com.adsapient.shared.mappable.BannerImpl;
import com.adsapient.shared.mappable.PluginsImpl;
import com.adsapient.shared.exceptions.PluginInstallerExeption;
import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.dao.HibernateEntityDao;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class PluginService {
    static Logger logger = Logger.getLogger(PluginService.class);
    private HibernateEntityDao hibernateEntityDao;

    public Banner createBanner(BannerUploadActionForm form)
            throws AdsapientException {
        if (Type.IMAGE_TYPE_ID.equals(form.getTypeId())
                || Type.FLESH_TYPE_ID.equals(form.getTypeId())
                || Type.HTML_TYPE_ID.equals(form.getTypeId())
                || Type.SUPERSTITIAL_BANNER.equals(form.getTypeId())) {
            Banner banner = new BannerImpl(form);

            return banner;
        }

        Type bannerType = (Type) hibernateEntityDao.loadObject(Type.class, form
                .getTypeId());
        PluginsImpl plugin = (PluginsImpl) hibernateEntityDao
                .loadObjectWithCriteria(PluginsImpl.class, "typeId", bannerType
                        .getTypeId());
        Class bannerClass = null;
//        AbstractPluginBannerClass banner = null;

        try {
            bannerClass = Class.forName(plugin.getClassName());
        } catch (ClassNotFoundException e) {
            logger.error("cant create plugin banner. Reason class not found:"
                    + plugin.getClassName());
            throw new AdsapientException(
                    "cant find class from external plugin ");
        }

        try {
            Constructor con = bannerClass.getConstructor(new Class[] { form
                    .getClass() });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Banner loadBannerById(Integer bannerId) {
        Banner banner = (Banner) hibernateEntityDao.loadObjectWithCriteria(
                BannerImpl.class, "bannerId", bannerId);

        if (banner == null) {
            logger.warn("cant find banner with id:" + bannerId);
        }

        return banner;
    }

    public void removeBanner(Integer bannerId) {
        logger.warn("remove banner with id:" + bannerId);
        hibernateEntityDao.removeWithCriteria(BannerImpl.class, "bannerId",
                bannerId);
    }



    public void init() {
        Configuration configuration = new Configuration();
        Iterator pluginsIterator = hibernateEntityDao.viewAll(PluginsImpl.class)
                .iterator();
        logger.info("begin setup additional banners");

        while (pluginsIterator.hasNext()) {
            PluginsImpl plugin = (PluginsImpl) pluginsIterator.next();
            logger.info("begin setup banner:" + plugin.getClassName());

            Class pluginClass = null;

            try {
                pluginClass = Class.forName(plugin.getClassName());

//                AbstractPluginBannerClass pluginObject = (AbstractPluginBannerClass) pluginClass
//                        .newInstance();

                logger.info("add new mapping documents:"
                        + plugin.getMappingData());
            } catch (Exception e) {
                logger.info("Cant find plugin:" + plugin.getClassName());
                plugin.setPresent(false);
                hibernateEntityDao.updateObject(plugin);

                continue;
            }

            String configurationMetedata = plugin.getMappingData();

            if ((configurationMetedata == null)
                    || AdsapientConstants.EMPTY
                            .equalsIgnoreCase(configurationMetedata)) {
                logger
                        .info("plugin "
                                + plugin.getClassName()
                                + " have now configuration file or theris error while trying to add them");

                continue;
            }

            StringTokenizer tokenizer = new StringTokenizer(
                    configurationMetedata, ";");

            if (tokenizer.hasMoreTokens()) {
                while (tokenizer.hasMoreTokens()) {
                    String resourceName = tokenizer.nextToken();
                    Document mappingDocument = XmlService.parseXmlFile(
                            resourceName, false, pluginClass);
                    configuration.configure(mappingDocument);

                    logger.info("add mapping document:" + resourceName);
                }
            }
        }

        configuration.configure("/hibernate.cfg.xml");

        AdSapientHibernateService.rebuildSessionFactory(configuration);
    }

    public static void pluginAddon(PluginManagerActionForm form)
            throws PluginInstallerExeption {
        String className = form.getClassName();

        try {
//            Class test = PluginManager.class.getClassLoader().loadClass(
//                    className);
            Class pluginClass = Class.forName(className);
            logger.info("success find class:" + pluginClass);

            Object pluginObject = pluginClass.newInstance();
            logger.info("create object from class");

//            if (pluginObject instanceof AbstractPluginBannerClass) {
//                AbstractPluginBannerClass pluginBanner = (AbstractPluginBannerClass) pluginObject;
//                logger.info("cast object to :" + pluginBanner.getClass());

//                if (pluginBanner.plugin()) {
//                    Type bannerType = new Type();
//                    bannerType.setType(form.getPluginName());
//
//                    Integer pluginTypeId = new Integer(MyHibernateUtil
//                            .addObject(bannerType));
//                    logger.info("add new banner type:" + bannerType.getType());
//
//                    PluginsImpl plugin = new PluginsImpl();
//                    plugin.setClassName(className);
//                    plugin.setMappingData(pluginBanner.getMappingDocuments());
//                    plugin.setPresent(true);
//                    plugin.setPluginName(form.getPluginName());
//                    plugin.setTypeId(pluginTypeId);
//                    MyHibernateUtil.addObject(plugin);
//
//                    init();
//                }

//                logger.info("install plugin " + className);
//            } else {
//                logger.warn("cant plugin object  that is not extend:"
//                        + AbstractPluginBannerClass.class);
//                throw new PluginInstallerExeption("");
//            }
        } catch (Exception ex) {
            logger.warn("while try to add plugin", ex);
            throw new PluginInstallerExeption("error.while.install.plugin");
        }
    }

    public void unpluginAddon(PluginManagerActionForm form) {
        PluginsImpl plugin = (PluginsImpl) hibernateEntityDao.loadObject(
                PluginsImpl.class, form.getId());

        hibernateEntityDao.removeObject(Type.class, plugin.getTypeId());

        hibernateEntityDao.removeObject(plugin);
    }
        public void executeSQL(String filePath, Class pluginClass)
            throws PluginInstallerExeption {
        logger.info("Begin install for SQL resource:" + filePath);

        InputStream stream = pluginClass.getResourceAsStream(filePath);

        if (stream == null) {
            stream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(filePath);
        }

        if (stream == null) {
            logger.warn(filePath + " not found");
            throw new PluginInstallerExeption(filePath + " not found");
        }

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String str;

            while ((str = in.readLine()) != null) {
                buffer.append(str);
            }

            in.close();

            hibernateEntityDao.executeNativeSQL(buffer.toString());
            logger.info("execute sql script while setup:" + buffer.toString());
        } catch (IOException e) {
            logger.warn("while read resource:" + filePath);
        }
    }

    public HibernateEntityDao getHibernateEntityDao() {
        return hibernateEntityDao;
    }

    public void setHibernateEntityDao(HibernateEntityDao hibernateEntityDao) {
        this.hibernateEntityDao = hibernateEntityDao;
    }
}
