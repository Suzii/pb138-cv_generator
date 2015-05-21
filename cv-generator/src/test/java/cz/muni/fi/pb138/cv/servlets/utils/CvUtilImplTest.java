/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zuzana
 */
public class CvUtilImplTest {

    private final CvUtil instance;
    private static final String SAMPLE_FILE = "samplefile.txt";
    private File sampleFile;
    private File resultFile;
    //http://www.w3schools.com/json/
    private static final String SAMPLE_JSON_CONTENT = "{\"employees\":[\n"
            + "    {\"firstName\":\"John\", \"lastName\":\"Doe\"},\n"
            + "    {\"firstName\":\"Anna\", \"lastName\":\"Smith\"},\n"
            + "    {\"firstName\":\"Peter\", \"lastName\":\"Jones\"}\n"
            + "]}";

    public CvUtilImplTest() {
        this.instance = new CvUtilImpl();
    }

    @Before
    public void setUp() {
        resultFile = createFile("result", "");
        sampleFile = createFile(SAMPLE_FILE, SAMPLE_JSON_CONTENT);
    }

    @After
    public void tearDown() {
        sampleFile.delete();
        resultFile.delete();
    }

    /**
     * Test of attachFile method, of class CvUtilImpl.
     */
    @Test
    public void testAttachFile_ok() throws Exception {
        System.out.println("attachFile");

        OutputStream out = new FileOutputStream(resultFile);
        boolean expResult = true;
        boolean result = instance.attachFile(out, sampleFile);
        assertEquals("File was not written to.", expResult, result);

        assertEquals("Size of written content.", sampleFile.length(), resultFile.length());

    }

    /**
     * Test of attachFile method, of class CvUtilImpl.
     */
    @Test
    public void testAttachFile_passedFileNull() throws Exception {
        System.out.println("attachFile passed null");

        OutputStream out = new FileOutputStream(resultFile);
        boolean expResult = false;
        boolean result = instance.attachFile(out, null);
        assertEquals("should return false", expResult, result);
    }

    /**
     * Test of attachFile method, of class CvUtilImpl.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAttachFile_passedOutputStreamNull() throws Exception {
        System.out.println("attachFile outputstream is null");

        instance.attachFile(null, sampleFile);
    }

    /**
     * Test of extractUserData method, of class CvUtilImpl.
     */
    @Test
    public void testExtractUserData_ok() throws FileNotFoundException {
        System.out.println("extractUserData");
        BufferedReader reader = new BufferedReader(
                new FileReader(createFile(SAMPLE_FILE, SAMPLE_JSON_CONTENT)));
        JSONObject expResult = new JSONObject(SAMPLE_JSON_CONTENT);
        JSONObject result = instance.extractUserData(reader);
        assertEquals("Length of extracted content", expResult.length(), result.length());
        assertEquals("Content", expResult.toString(), result.toString());
    }
    
    /**
     * Test of extractUserData method, of class CvUtilImpl.
     */
    @Test(expected = JSONException.class)
    public void testExtractUserData_invalidJSONpassed() throws FileNotFoundException {
        System.out.println("extractUserData invalid JSON string passed");
        BufferedReader reader = new BufferedReader(
                new FileReader(createFile(SAMPLE_FILE, "This is not valid JSON content.")));
        
        instance.extractUserData(reader);
    }

    private File createFile(String fileName, String content) {
        try {

            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
