package com.adsapient.shared.service;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

public class XmlService {
    private static Logger logger = Logger.getLogger(XmlService.class);
    public static Document parseXmlFile(String filename, boolean validating,
                                        Class classLoaderSource) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            factory.setValidating(validating);

            Document doc = factory.newDocumentBuilder().parse(
                    classLoaderSource.getClassLoader().getResourceAsStream(
                            filename));

            return doc;
        } catch (SAXException e) {
            logger.warn("while trying open document" + filename, e);
        } catch (ParserConfigurationException e) {
            logger.warn("while trying open document" + filename, e);
        } catch (IOException e) {
            logger.warn("while trying open document" + filename, e);
        }

        return null;
    }
}
