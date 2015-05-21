/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.transformation;

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
 * @author Peto
 */
public class Xml2JsonImplTest {
    
    public Xml2JsonImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Xml2JsonImpl.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Xml2JsonImpl.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class Xml2JsonImpl.
     */
    @Test
    public void testTransform_Document() {
        System.out.println("transform");
        Document dom = null;
        Xml2JsonImpl instance = new Xml2JsonImpl();
        JSONObject expResult = null;
        JSONObject result = instance.transform(dom);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class Xml2JsonImpl.
     */
    @Test
    public void testTransform_String() {
        System.out.println("transform");
        String data = "";
        Xml2JsonImpl instance = new Xml2JsonImpl();
        JSONObject expResult = null;
        JSONObject result = instance.transform(data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
