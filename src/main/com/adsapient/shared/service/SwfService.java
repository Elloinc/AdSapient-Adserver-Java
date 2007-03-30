package com.adsapient.shared.service;

import com.kinesis.KineticFusion;
import org.infozone.tools.xml.queries.XUpdateQuery;
import org.infozone.tools.xml.queries.XUpdateQueryFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class SwfService {
    private void transform2SWF(String swfFileName) throws IOException {
        String[] args = {"processFile", "toSWF", "/banner/temp/result.xml",
                swfFileName};

        KineticFusion.main(args);
    }

    private void transform2XML(String swfFileName) throws IOException {
        String[] args = {"processFile", "toXML", swfFileName,
                "banner/temp/temp.xml"};

        KineticFusion.main(args);
    }

    private void transformXML() throws Exception {
        String inputFile = "banners/temp/temp.xml";
        String updateFile = "banners/temp/xupdate.xml";

        String resultFile = "banners/temp/result.xml";


        Node result = null;
        result = updateFile(updateFile, inputFile);

        Source source = new DOMSource(result);

        File file = new File(resultFile);
        Result resultStream = new StreamResult(file);

        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, resultStream);
    }

    public synchronized boolean patchSWFFile(String fileName) throws Exception {
        this.transform2XML(fileName);

        this.transformXML();

        this.transform2SWF("banners/temp/upd.swf");

        return true;
    }

    public void applyAdsapientPatch() {
    }

    protected Node updateFile(String queryFile, String inputFile)
            throws Exception {
        String query = parseUpdateFile(queryFile);
        Document result = parseInputFile(inputFile);

        XUpdateQuery xupdate = XUpdateQueryFactory.newInstance()
                .newXUpdateQuery();
        xupdate.setQString(query);
        xupdate.execute(result);

        return result;
    }

    private String prepareXMLFile(String inputFileName) throws Exception {
        if (inputFileName == null) {
            throw new IllegalArgumentException(
                    "name of update file must not be null !");
        }

        File file = new File(inputFileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        char[] characters = new char[new Long(file.length()).intValue()];
        br.read(characters, 0, new Long(file.length()).intValue());
        br.close();

        // create file as string
        String xmlFileString = new String(characters);
        xmlFileString = xmlFileString.replaceFirst(
                "xmlns='http://www.kineticfusion.org/RVML/1.0'", "");

        return xmlFileString;
    }

    protected String parseUpdateFile(String filename) throws Exception {
        if (filename == null) {
            throw new IllegalArgumentException(
                    "name of update file must not be null !");
        }

        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        char[] characters = new char[new Long(file.length()).intValue()];
        br.read(characters, 0, new Long(file.length()).intValue());
        br.close();

        return new String(characters);
    }

    protected Document parseInputFile(String filename) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            factory.setValidating(false);
            factory.setIgnoringComments(true);
            factory.setNamespaceAware(true);
            factory.setCoalescing(false);

            Document doc = factory.newDocumentBuilder().parse(
                    new ByteArrayInputStream(prepareXMLFile(filename)
                            .getBytes()));

            return doc;
        } catch (SAXException e) {
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
