/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.test;

import cz.muni.fi.pb138.cv.service.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jozef Živčic
 */
public class XMLToTeXTest {
    private String file;
    private String constants;
    private static final String language = "sk";
    
    @Before
    public void setUp() throws IOException {
        CvServiceImpl cvService;
        cvService = new CvServiceImpl();
        cvService.transforToTexFile("anicka", language);
        StringBuffer sb = new StringBuffer();
        try(BufferedReader br = new BufferedReader(new FileReader(Config.DBUTIL + "\\resultCV.tex"))) {
            for (String s = br.readLine(); s != null; s=br.readLine()) {
                sb.append(s);
                sb.append(System.lineSeparator());
            }
            file = sb.toString();
        }
        constants = getStringConstants(language);
    }
    
    @After
    public void tearDown() {
        File f = new File(Config.DBUTIL + "\\resultCV.tex");
        //f.delete();
    }
    
    @Test
    public void testAllMandatoryElements() {
        assertTrue(file.contains("\\begin{document}"));
        assertTrue(file.contains("\\end{document}"));
        assertTrue(file.contains("\\MySlogan{" + getConstant("<curriculum-vitae>")+ "}"));
        assertTrue(file.contains("\\NewPart{" + getConstant("<personal-details>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + getConstant("<name>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + getConstant("<surname>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + getConstant("<date-of-birth>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + getConstant("<country>")+ "}"));
    }
    
    @Test
    public void testState() {
        assertTrue(file.contains("\\PersonalEntry{" + getConstant("<state>")+ "}"));
    }
    
    @Test
    public void testPhones() {
        assertTrue(file.contains("\\PersonalEntry{" + getConstant("<phones>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + getConstant("<phone>")+ "}"));
    }
    
    @Test
    public void testMails() {
        assertTrue(file.contains("\\PersonalEntry{" + getConstant("<emails>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + getConstant("<email>")+ "}"));
    }
    
    @Test
    public void testSites() {
        assertTrue(file.contains("\\PersonalEntry{" + getConstant("<my-sites>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + getConstant("<my-site>")+ "}"));
    }
    
    @Test
    public void testAllOptionalParts() {
        assertTrue(file.contains("\\NewPart{" + getConstant("<education>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + getConstant("<work-experience>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + getConstant("<certificates>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + getConstant("<language-skills>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + getConstant("<computer-skills>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + getConstant("<driving-licence>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + getConstant("<characteristics>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + getConstant("<hobbies>")+ "}{}"));
    }

    private String getStringConstants(String language) throws IOException {
        StringBuffer sb = new StringBuffer();
        try(BufferedReader br = new BufferedReader(new FileReader(Config.DBUTIL + "\\texts.xml"))) {
            for (String s = br.readLine(); s != null; s=br.readLine()) {
                sb.append(s);
                sb.append(System.lineSeparator());
            }
        }
        String temp = sb.toString();
        String array[] = temp.split("<language id=");
        for (int i = 0 ; i < array.length; i++) {
            if (array[i].contains("\"" + language + "\""))
                temp = array[i];
        }
        return temp;
    }
    
    private String getConstant(String element) {
        String closeElement = "</" + element.substring(1);
        int first = constants.indexOf(element);
        int last = constants.indexOf(closeElement);
        String output = constants.substring(first + element.length(),last);
        return output;
    }
    
}
