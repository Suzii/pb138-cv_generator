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
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 *
 * @author pato
 */
public class CvServiceImplTest {
        private final CvService cvService;
    private static final String users = "users.xml";
    private static File usersFile;
    private static Document logins;
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(UserServiceImplTest.class);


    public CvServiceImplTest() {
        cvService = new CvServiceImpl("str","str");
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
        
    }

    /**
     * Test of loadCvXML method, of class CvServiceImpl.
     */
    @Test
    public void testLoadCvXML() {

    }

    /**
     * Test of saveCv method, of class CvServiceImpl.
     */
    @Test
    public void testSaveCv_String_JSONObject() {

    }

    /**
     * Test of saveCv method, of class CvServiceImpl.
     */
    @Test
    public void testSaveCv_String_Document() {

    }

    /**
     * Test of generatePdf method, of class CvServiceImpl.
     */
    @Test
    public void testGeneratePdf() {

    }

    /**
     * Test of checkValidity method, of class CvServiceImpl.
     */
    @Test
    public void testCheckValidity_Document() {

    }

    /**
     * Test of checkValidity method, of class CvServiceImpl.
     */
    @Test
    public void testCheckValidity_JSONObject() {

    }

    /**
     * Test of cleanAfterGeneratingPDF method, of class CvServiceImpl.
     */
    @Test
    public void testCleanAfterGeneratingPDF() {
        
    }
    
}
