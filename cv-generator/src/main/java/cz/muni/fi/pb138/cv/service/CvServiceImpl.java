/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import cz.muni.fi.pb138.cv.transformation.Json2XmlImpl;
import cz.muni.fi.pb138.cv.transformation.Xml2JsonImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author pato
 */
public class CvServiceImpl implements CvService {

    private Json2XmlImpl json2xml;
    private Xml2JsonImpl xml2json;

    public CvServiceImpl() {
        json2xml = new Json2XmlImpl();
        xml2json = new Xml2JsonImpl();
    }

    @Override
    public JSONObject loadCvJSON(String login) {
        Document cvXml = loadCvXML(login);
        if (cvXml == null) {
            return null;
        }

        return xml2json.transform(cvXml);
    }

    @Override
    public Document loadCvXML(String login) {
        String filePath = Config.DIRECTORY + "/" + login + ".xml";
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
            Logger.getLogger(FileService.class
                    .getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * TODO
     * vsetko musis takto uzavriet do try catch a idealne aj logovat v debug mode..
     * @param login
     * @param cv
     * @return 
     */
    @Override
    public boolean saveCv(String login, JSONObject cv) {
        //System.out.println("Converting JSON to XML");
        try {
            Document cvXml = json2xml.transform(cv);
            //System.out.println("Transformation finished OK.");
            boolean result = saveCv(login, cvXml);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean saveCv(String login, Document cv) {
        String filePath = Config.DIRECTORY + "/" + login + ".xml";
        //System.out.println("Filepath for new CV: " + filePath);
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
            return false;
        }

    }

    @Override
    public File generatePdf(String login, String lang) {
        // todo vyriesit ako mat ten xml subor ulozeny alebo odkial ho nacucat + ako presne volat xslt transformator
        StreamSource xml = new StreamSource(new File(Config.DIRECTORY + "/" + login + ".xml"));
        StreamSource xslt = new StreamSource(new File("src/main/java/cz/muni/fi/pb138/cv/transformation/xml-to-tex.xsl")); // XSLT FILE
        
        StreamResult result;
        try {
            result = new StreamResult(new FileOutputStream("resultCV.tex"));
            TransformerFactory tf = TransformerFactory.newInstance();
            //tf.setAttribute("lang", lang);
            Transformer transformer = tf.newTransformer(xslt);
            transformer.setParameter("cv-language", lang);
            transformer.transform(xml, result);
            
        } catch (FileNotFoundException | TransformerException ex) {
            Logger.getLogger(CvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        /*ProcessBuilder pb = new ProcessBuilder("pdflatex -synctex=1 -interaction=nonstopmode" +System.getProperty("user.dir")+"\\resultCV.tex");
        try {
            pb.start();
        } catch (IOException ex) {
            Logger.getLogger(CvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        return null;
    }

    @Override
    public String checkValidity(Document cv) {
        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource(new File("mySchema.xsd")); // TODO create schema
        try {
            Schema schema = factory.newSchema(schemaFile);
            // create a Validator instance, which can be used to validate an instance document
            Validator validator = schema.newValidator();
            // validate the DOM tree
            validator.validate(new DOMSource(cv));
        } catch (SAXException e) {
            // instance document is invalid!
            return e.getMessage();
        } catch (IOException ex) {
            Logger.getLogger(CvServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String checkValidity(JSONObject cv) {
        Document cvXml = json2xml.transform(cv);
        String result = checkValidity(cvXml);
        return result;
    }

}
