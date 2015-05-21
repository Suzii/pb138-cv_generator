/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author pato
 */
public class CvServiceImplTest {

    private final CvService cvService;
    private Document anickaCV;
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(CvServiceImplTest.class);
    private static final String dbPath = "src\\main\\resources\\TestData\\pb138-database";

    public CvServiceImplTest() {
        final Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/cvgenerator.properties"));
        } catch (IOException ex) {
            log.error("Problem with latexbin.", ex);
            throw new RuntimeException();
        }
        cvService = new CvServiceImpl(dbPath, properties.getProperty("latex.folder"));
        anickaCV = null;
        File doc = new File(dbPath + "/anicka.xml");
        log.debug("Path of doc : " + doc.getAbsolutePath());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            anickaCV = dBuilder.parse(doc);
            log.debug(anickaCV.getDocumentElement().getNodeName());
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            log.error("Error when opening(loading) users document. ", ex);
        }
    }

    /**
     * Test of loadCvJSON method, of class CvServiceImpl.
     */
    @Test
    public void testLoadCvJSON() {
        assertNotSame("Load JSON object.", cvService.loadCvJSON("anicka"), null);
        assertFalse("Return JSON object not null.", cvService.loadCvJSON("anicka").equals(null));
    }

    /**
     * Test of loadCvJSON method, of class CvServiceImpl.
     */
    @Test
    public void testLoadCvJSONwithNullLogin() {
        assertEquals(null, cvService.loadCvJSON(null));
    }

    /**
     * Test of loadCvXML method, of class CvServiceImpl.
     */
    @Test
    public void testLoadCvXML() {
        assertNotSame("Load XML file.", cvService.loadCvXML("anicka"), null);
        assertFalse("Return XML file not null.", cvService.loadCvXML("anicka").equals(null));
    }

    /**
     * Test of loadCvXML method, of class CvServiceImpl.
     */
    @Test
    public void testLoadCvXMLwithNullLogin() {
        assertEquals(null, cvService.loadCvXML(null));
    }

    /**
     * Test of saveCv method, of class CvServiceImpl.
     */
    @Test
    public void testSaveCv_String_Document() {
        File anickaTest = new File(dbPath + "/anickaTest.xml");
        assertTrue("Successful saving of cv.", cvService.saveCv("anickaTest", anickaCV));
        assertTrue("Check file.", anickaTest.exists());
        anickaTest.delete();
    }

    /**
     * Test of saveCv method, of class CvServiceImpl.
     */
    @Test
    public void testSaveCv_String_Document_NullLogin() {;
        assertFalse("Saving cv with null login.", cvService.saveCv(null, anickaCV));
    }

    /**
     * Test of saveCv method, of class CvServiceImpl.
     */
    @Test
    public void testSaveCv_String_Document_NullCV() {
        Document cv = null;
        assertFalse("Saving cv with null file.", cvService.saveCv("anickaTest", cv));
    }

    /**
     * Test of generatePdf method, of class CvServiceImpl.
     */
    @Test
    public void testGeneratePdfAndCleanFiles() {
        cvService.generatePdf("anicka", "sk");
        File pdf = new File(dbPath + "/Utils/resultCV.pdf");
        assertTrue("Generated pdf exists.", pdf.exists());
        //cleaning
        cvService.cleanAfterGeneratingPDF();
        assertFalse("Generated pdf not exists.", pdf.exists());
        assertFalse("Generated aux not exists.", new File(dbPath + "/Utils/resultCV.aux").exists());
        assertFalse("Generated tex not exists.", new File(dbPath + "/Utils/resultCV.tex").exists());
        assertFalse("Generated log not exists.", new File(dbPath + "/Utils/resultCV.log").exists());
    }

    /**
     * Test of generatePdf method, of class CvServiceImpl.
     */
    @Test
    public void testGeneratePdfwithNullLogin() {
        assertEquals(null, cvService.generatePdf(null, "sk"));
    }

    /**
     * Test of checkValidity method, of class CvServiceImpl.
     */
    @Test
    public void testCheckValidity_Document() {
       // assertEquals("Not error message by validation.", null, cvService.checkValidity(anickaCV));
    }

    /**
     * Test of checkValidity method, of class CvServiceImpl.
     */
    @Test
    public void testCheckValidity_Document_NullDocument() {
        Document logins = null;
        assertFalse("Error message by validation.", cvService.checkValidity(logins).equals(null));
    }

}
