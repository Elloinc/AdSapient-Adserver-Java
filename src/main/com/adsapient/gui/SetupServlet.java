package com.adsapient.gui;

import com.adsapient.shared.AdsapientConstants;
import com.adsapient.shared.mappable.InstallItem;
import com.adsapient.shared.service.InstallationService;
import org.apache.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

public class SetupServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(SetupServlet.class);
    private String pathToBanners;
    public static boolean INSTALLING = false;

    private DocumentFactory factory = DocumentFactory.getInstance();


    public void init() throws ServletException {
        installIfNecessary();
    }

    public void install() {
        try {
            INSTALLING = true;
            InstallationService.createTables();
            setupFiles();
        } catch (Exception e) {
            logger.error("Proble fill database", e);
        }
    }

    public void installIfNecessary() {
        try {
            Properties props = new Properties();
            props.load(ContextAwareGuiBean.class.getClassLoader()
                    .getResourceAsStream("adsapient.properties"));
            pathToBanners = (String) props.get("path.banners");

            Connection con;
            String createString;
            Statement stmt;
            Class.forName((String) props.get("db.driver"));

            String qweryString = "select * from banner;";

            con = DriverManager.getConnection((String) props.get("db.url"),
                    (String) props.get("db.username"), (String) props
                    .get("db.password"));
            stmt = con.createStatement();

            stmt.executeQuery(qweryString);
            stmt.close();
            con.close();
        } catch (Exception ex) {
            install();
        }
    }

    private Document parseWithSAX(InputStream is) throws DocumentException {
        SAXReader xmlReader = new SAXReader();
        return xmlReader.read(is);
    }

    protected TreeMap<Integer, InstallItem> getInstallItems() {
        TreeMap<Integer, InstallItem> installItems = new TreeMap<Integer, InstallItem>();
        try {
            Document doc = parseWithSAX(ContextAwareGuiBean.class.getClassLoader().getResourceAsStream("setup/install.xml"));
            XPath xpathSelector = DocumentHelper.createXPath("/install/*");
            XPath step = DocumentHelper.createXPath("@step");
            XPath file = DocumentHelper.createXPath("@file");
            XPath dir = DocumentHelper.createXPath("@dir");
            XPath toFile = DocumentHelper.createXPath("@toFile");
            List results = xpathSelector.selectNodes(doc);
            for (Iterator iter = results.iterator(); iter.hasNext();) {
                Element element = (Element) iter.next();
                InstallItem ii = new InstallItem();
                ii.setId(Integer.valueOf(step.selectSingleNode(element).getText()));
                ii.setCommand(element.getName());
                if (ii.getCommand().equals(InstallItem.COMMAND_COPY)) {
                    ii.setFilePath(file.selectSingleNode(element).getText());
                    ii.setServerURL(toFile.selectSingleNode(element).getText());
                } else if (ii.getCommand().equals(InstallItem.COMMAND_MKDIR)) {
                    ii.setServerURL(dir.selectSingleNode(element).getText());
                }

                installItems.put(ii.getId(), ii);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return installItems;
    }


    public boolean setupFiles() {
        clean();
        TreeMap<Integer, InstallItem> installItems = getInstallItems();
        logger.info("Begin application installation..");

        for (Integer key : installItems.keySet()) {
            InstallItem item = installItems.get(key);
            String path2install = item.getServerURL();

            if (InstallItem.COMMAND_COPY.equalsIgnoreCase(item.getCommand())) {
                try {
                    logger.info("Copy file from " + item.getServerURL()
                            + " to " + path2install);

                    InputStream in = ContextAwareGuiBean.class.getClassLoader()
                            .getResourceAsStream(item.getFilePath());

                    if (in == null) {
                        logger.error("Couldn't locate on server:"
                                + item.getServerURL());

                        continue;
                    }

                    OutputStream out = new FileOutputStream(path2install);
                    byte[] b = new byte[in.available()];
                    int bytesRead = in.read(b);

                    if (bytesRead != -1) {
                        out.write(b);
                    }

                    in.close();
                    out.close();
                } catch (IOException e) {
                    logger.error("Error while setup application data", e);
                }
            }

            if (InstallItem.COMMAND_MKDIR.equalsIgnoreCase(item.getCommand())) {
                logger.info("execute mkdir command");
                new File(path2install).mkdirs();
                logger.info("success create directorys " + path2install);
            }
        }

        logger.info("Finish application installer");

        return true;
    }

    public void clean() {
        try {
            File f = new File(pathToBanners);
            deleteDir(f);
            f.mkdirs();
        } catch (Exception e) {
            logger.error("Unable to clean directory "
                    + AdsapientConstants.PATH_TO_BANNERS, e);
        }
    }

    public boolean deleteDir(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++)
                    if (files[i].isDirectory()) {
                        deleteDir(files[i]);
                    } else {
                        files[i].delete();
                    }
            }
        }

        return (dir.delete());
    }


}
