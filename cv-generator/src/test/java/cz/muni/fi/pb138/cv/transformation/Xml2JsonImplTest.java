/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.transformation;

import cz.muni.fi.pb138.cv.service.Config;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static junit.framework.Assert.fail;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.skyscreamer.jsonassert.JSONAssert;
import static org.junit.Assert.*;

/**
 *
 * @author Peto
 */
public class Xml2JsonImplTest{
    Xml2JsonImpl inst;
    String str_xml;
    Document dom;
    JSONObject json_sample;
    
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
   public void setUp() {
        inst = new Xml2JsonImpl();
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(Config.SAMPLE_JSON_PATH));
            json_sample = new JSONObject(String.join("", lines));
        } catch (IOException ex) {
            fail("io error" + ex);
        }
        
        try {
            DocumentBuilder docBuilder;
            List<String> lines = Files.readAllLines(Paths.get(Config.SAMPLE_XML_PATH));
            str_xml = String.join("", lines);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            docBuilder = dbFactory.newDocumentBuilder();
            dom = docBuilder.parse(new ByteArrayInputStream(str_xml.getBytes()));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail("io error" + ex);
        }
        
    }
    
    @After
    public void tearDown() {}


    /**
     * Test of transform method, of class Xml2JsonImpl.
     */
    @Test
    public void testTransform_Document() {
        System.out.println("transform");
        JSONObject json_object = inst.transform(str_xml);
        
        JSONAssert.assertEquals(json_sample.toString(), json_object.toString(), false);
    }

    /**
     * Test of transform method, of class Xml2JsonImpl.
     */
    @Test
    public void testTransform_String() {
        System.out.println("transform");
        
        JSONObject json_object = inst.transform(dom);
        
        JSONAssert.assertEquals(json_sample.toString(4), json_object.toString(4), false);
    }
    
    @Test
    public void testOther(){
        JSONObject json_object = inst.transform(str_xml);
        
        assertEquals(json_object.getJSONObject("curriculum-vitae").getJSONObject("personal-details").get("given-names").toString(), "Anca Maria");
        assertEquals(json_object.getJSONObject("curriculum-vitae").getJSONObject("personal-details").getJSONObject("address").get("number").toString(), "42");
    }
    
}
