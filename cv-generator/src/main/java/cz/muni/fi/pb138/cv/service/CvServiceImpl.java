/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import cz.muni.fi.pb138.cv.transformation.Json2XmlImpl;
import cz.muni.fi.pb138.cv.transformation.Xml2JsonImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author pato
 *
 * !!!!!!! cv.xsd and xml-to-tex.xsl and texts.xml MUST BE COPIED TO PREPARED
 * DIRECTORY C:\pb138-database\Utils !!!!!!
 *
 */
public class CvServiceImpl implements CvService {

    private Json2XmlImpl json2xml;
    private Xml2JsonImpl xml2json;
    private String databasePath;
    private String pathToPdflatexBin;
    private final static Logger log = LoggerFactory.getLogger(CvServiceImpl.class);

    public CvServiceImpl(String DBpath , String pathTolatexBin) {
        json2xml = new Json2XmlImpl();
        xml2json = new Xml2JsonImpl();
        databasePath = DBpath;
        pathToPdflatexBin = pathTolatexBin;
    }

    @Override
    public JSONObject loadCvJSON(String login) {
        if(login == null) return null;
        log.debug("LoadCvJson login:" + login);
        Document cvXml = loadCvXML(login);
        if (cvXml == null) {
            return null;
        }

        return xml2json.transform(cvXml);
    }

    @Override
    public Document loadCvXML(String login) {
        if(login == null) return null;
        String filePath = databasePath + "/" + login + ".xml";
        log.debug("Load xml : " + filePath);
        File doc = new File(filePath);
        if (!doc.exists()) {
            return null;
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(doc);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            log.error("Error when loading xml for user : " + login, ex);
            return null;
        }
    }

    /**
     * TODO vsetko musis takto uzavriet do try catch a idealne aj logovat v
     * debug mode..
     *
     * @param login
     * @param cv
     * @return
     */
    @Override
    public boolean saveCv(String login, JSONObject cv) {
        if(login == null || cv == null) return false;
        //System.out.println("Converting JSON to XML");
        try {
            Document cvXml = json2xml.transform(cv);
            //System.out.println("Transformation finished OK.");
            log.debug("Saving cv with JSON , login : " + login);
            boolean result = saveCv(login, cvXml);
            return result;
        } catch (Exception ex) {
            log.error("Error when saving JsnoObjectCV with login : " + login, ex);
        }

        return false;
    }

    @Override
    public boolean saveCv(String login, Document cv) {
        if(login == null || cv == null) return false;
        String filePath = databasePath + "/" + login + ".xml";
        //System.out.println("Filepath for new CV: " + filePath);
        log.debug("Saving cv XML , login : " + login + " FILEPATH : " + filePath);
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(cv);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);
            return true;
        } catch (TransformerFactoryConfigurationError | TransformerException ex) {
            log.error("Error when saving user cvXML file , login : " + login, ex);
            return false;
        }

    }

    @Override
    public File generatePdf(String login, String lang) {
        if(login == null || lang == null) return null;
        if (transformToTexFile(login, lang)) {

            File tex = new File(databasePath + "/Utils/resultCV.tex");
            
            List<String> params = new ArrayList<String>();
            params.add(pathToPdflatexBin + "pdflatex");
            params.add(tex.getAbsolutePath());
            log.debug("TEX INFO : " + tex.getAbsolutePath() + " " + tex.getParentFile());
            
            ProcessBuilder pb = new ProcessBuilder();
            pb.directory(tex.getParentFile());
            pb.redirectErrorStream(true);
            
            try {
                log.debug("Generating PDF.");
                pb.command(params);
                
                log.debug("Starting command: " + pb.command());
                Process pdfCreation = pb.start();
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(pdfCreation.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    //log.debug(line);
                }
            } catch (IOException ex) {
                log.error("Error generating PDF file.",ex);
                return null;
            }
            return new File(databasePath + "/Utils/resultCV.pdf");
        }
        return null;
        
    }

    @Override
    public String checkValidity(Document cv) {
        if(cv == null) return "CV is NULL";
        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        log.debug("Checking validity of XML file.");
        // load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource(new File(databasePath + "/Utils/cv.xsd")); // TODO create schema
        //log.debug("Schema : ");
        try {
            Schema schema = factory.newSchema(schemaFile);
            // create a Validator instance, which can be used to validate an instance document
            Validator validator = schema.newValidator();
            // validate the DOM tree
            validator.validate(new DOMSource(cv));
        } catch (SAXException e) {
            // instance document is invalid!
            log.error("Error when checking validiotion with XML schema.(SAX) " + e.getMessage());
            return e.getMessage();
        } catch (IOException ex) {
            log.error("Error when checking validiotion with XML schema.(IO) " + ex.getMessage());
            return ex.getMessage();
        }
        log.debug("Validation successful.");
        return null;
    }

    @Override
    public String checkValidity(JSONObject cv) {
        if(cv == null) return "CV is NULL.";
        log.debug("Checking validity for JSONobject.");
        Document cvXml = json2xml.transform(cv);
        String result = checkValidity(cvXml);
        return result;
    }

    public boolean transformToTexFile(String login, String lang) {
        // todo vyriesit ako mat ten xml subor ulozeny alebo odkial ho nacucat
        File cv = new File(databasePath + "/" + login + ".xml");
        if (!cv.exists()) return false;
        StreamSource xml = new StreamSource(new File(databasePath + "/" + login + ".xml"));
        StreamSource xslt = new StreamSource(new File(databasePath + "/Utils/xml-to-tex.xsl")); // XSLT FILE

        StreamResult result;
        try {
            FileOutputStream os = new FileOutputStream(databasePath + "/Utils/resultCV.tex");
            result = new StreamResult(os);
            TransformerFactory tf = TransformerFactory.newInstance();

            Transformer transformer = tf.newTransformer(xslt);
            transformer.setParameter("cv-language", lang);
            transformer.transform(xml, result);
            os.close();
        } catch (TransformerException|IOException ex) {
            log.error("Error whe tranformating XML cv to tex file for login : " + login + " in language: " + lang, ex);
            return false;
        }
        return true;
    }

    /*
     * `Delete all files which has been generated by creating PDF CV
     */
    public void cleanAfterGeneratingPDF() {
        /*File dvi = new File(Config.DBUTIL + "/resultCV.dvi");
        dvi.delete();*/
        //delete intermediate .out file
        /*File out = new File(Config.DBUTIL + "/resultCV.out");
        out.delete();*/
        
        //delete intermediate .aux file       
        File aux = new File(databasePath + "/Utils/resultCV.aux");
        aux.delete();

        //delete intermediate .log file
        File logF = new File(databasePath + "/Utils/resultCV.log");
        logF.delete();
        
        File tex = new File(databasePath + "/Utils/resultCV.tex");
        tex.delete();
        
        File pdf = new File(databasePath + "/Utils/resultCV.pdf");
        pdf.delete();
    }
}
