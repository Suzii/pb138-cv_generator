/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;

/**
 *
 * @author pato
 */
public class CvServiceImplTest {
    
    public CvServiceImplTest() {
    }
    
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadCvJSON method, of class CvServiceImpl.
     */
    @Test
    public void testLoadCvJSON() {
        System.out.println("loadCvJSON");
        String login = "";
        CvServiceImpl instance = null;
        JSONObject expResult = null;
        JSONObject result = instance.loadCvJSON(login);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCvXML method, of class CvServiceImpl.
     */
    @Test
    public void testLoadCvXML() {
        System.out.println("loadCvXML");
        String login = "";
        CvServiceImpl instance = null;
        Document expResult = null;
        Document result = instance.loadCvXML(login);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveCv method, of class CvServiceImpl.
     */
    @Test
    public void testSaveCv_String_JSONObject() {
        System.out.println("saveCv");
        String login = "";
        JSONObject cv = null;
        CvServiceImpl instance = null;
        boolean expResult = false;
        boolean result = instance.saveCv(login, cv);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveCv method, of class CvServiceImpl.
     */
    @Test
    public void testSaveCv_String_Document() {
        System.out.println("saveCv");
        String login = "";
        Document cv = null;
        CvServiceImpl instance = null;
        boolean expResult = false;
        boolean result = instance.saveCv(login, cv);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generatePdf method, of class CvServiceImpl.
     */
    @Test
    public void testGeneratePdf() {
        System.out.println("generatePdf");
        String login = "";
        String lang = "";
        CvServiceImpl instance = null;
        File expResult = null;
        File result = instance.generatePdf(login, lang);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkValidity method, of class CvServiceImpl.
     */
    @Test
    public void testCheckValidity_Document() {
        System.out.println("checkValidity");
        Document cv = null;
        CvServiceImpl instance = null;
        String expResult = "";
        String result = instance.checkValidity(cv);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkValidity method, of class CvServiceImpl.
     */
    @Test
    public void testCheckValidity_JSONObject() {
        System.out.println("checkValidity");
        JSONObject cv = null;
        CvServiceImpl instance = null;
        String expResult = "";
        String result = instance.checkValidity(cv);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanAfterGeneratingPDF method, of class CvServiceImpl.
     */
    @Test
    public void testCleanAfterGeneratingPDF() {
        System.out.println("cleanAfterGeneratingPDF");
        CvServiceImpl instance = null;
        instance.cleanAfterGeneratingPDF();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
