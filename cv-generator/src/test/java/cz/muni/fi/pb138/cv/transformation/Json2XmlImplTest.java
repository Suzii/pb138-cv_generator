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
import org.w3c.dom.Document;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.custommonkey.xmlunit.*;
import org.xml.sax.SAXException;

import cz.muni.fi.pb138.cv.service.Config;

/**
 *
 * @author Peto
 */
public class Json2XmlImplTest extends XMLTestCase{
    
    Json2XmlImpl inst;
    String str_json;
    Document sample_dom;
    
    public Json2XmlImplTest() {}
    
    @BeforeClass
    public static void setUpClass() {}
    
    @AfterClass
    public static void tearDownClass() {}
    
    @Before
    public void setUp() {
        inst = new Json2XmlImpl();
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(Config.SAMPLE_JSON_PATH));
            str_json = String.join("", lines);
        } catch (IOException ex) {
            fail("io error" + ex);
        }
        
        try {
            DocumentBuilder docBuilder;
            File file = new File(Config.SAMPLE_XML_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            docBuilder = dbFactory.newDocumentBuilder();
            sample_dom = docBuilder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            fail("io error" + ex);
        }
        
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
    }
    
    @After
    public void tearDown() {}
    
    /**
     * Get text from first element
     */
    private String getElementContent(Element e, String name){
        return ((Element) e.getElementsByTagName(name).item(0)).getTextContent();
    }

    /**
     * Test of transform method, of class Json2XmlImpl.
     */
    @Test
    public void testTransform_String() {
        System.out.println("transform from string");
        
        Document doc = inst.transform(str_json);
        
        assertXMLEqual(sample_dom, doc);
    }

    /**
     * Test of transform method, of class Json2XmlImpl.
     */
    @Test
    public void testTransform_JSONObject() {
        System.out.println("transform from json object");
        
        Document doc = inst.transform(new JSONObject(str_json));
        
        assertXMLEqual(sample_dom, doc);
    }
    
    @Test
    public void testOther(){
        Document doc = inst.transform(str_json);
        
        Element pd = (Element) doc.getElementsByTagName("personal-details").item(0);
        assertEquals("Anca Maria", getElementContent(pd, "given-names"));
    }
    
}
