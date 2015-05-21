/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Zuzana
 */
public class xmlSchemaTest {

    private final static Logger log = LoggerFactory.getLogger(xmlSchemaTest.class);

    public static void main(String[] args) {
        CvService service = new CvServiceImpl("C:\\pb138-database","C:\\Programy\\MiKTeX 2.9\\miktex\\bin\\");
        Document cv = service.loadCvXML("anicka");
        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        log.debug("Checking validity of XML file.");
        // load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource("C:\\Users\\Zuzana\\Disk Google\\prg\\pb138\\pb138-cv_generator\\cv-generator\\src\\main\\resources\\cv.xsd"); // TODO create schema
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
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            log.error("Error when checking validiotion with XML schema.(IO) " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
        log.debug("Validation successful.");

    }
}
