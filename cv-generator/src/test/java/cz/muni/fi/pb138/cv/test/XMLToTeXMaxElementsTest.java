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
public class XMLToTeXMaxElementsTest {
    private String file;
    private String constants;
    private static final String language = "en";
    private static final String outputFileName = "resultCV.tex";
    @Before
    public void setUp() throws IOException {
        CvServiceImpl cvService;
        cvService = new CvServiceImpl();
        cvService.transforToTexFile("anicka", language);
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(Config.DBUTIL + "\\" + outputFileName))) {
            for (String s = br.readLine(); s != null; s=br.readLine()) {
                sb.append(s);
                sb.append(System.lineSeparator());
            }
            file = sb.toString();
        }
        constants = ExtractConstants.getStringConstants(language);
    }
    
    @After
    public void tearDown() {
        File f = new File(Config.DBUTIL + "\\" + outputFileName);
        f.delete();
    }
    
    @Test
    public void testAllMandatoryElements() {
        assertTrue(file.contains("\\begin{document}"));
        assertTrue(file.contains("\\end{document}"));
        assertTrue(file.contains("\\MySlogan{" + ExtractConstants.getConstant(constants,"<curriculum-vitae>")+ "}"));
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<personal-details>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<name>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<surname>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<date-of-birth>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<country>")+ "}"));
    }
    
    @Test
    public void testState() {
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<state>")+ "}"));
    }
    
    @Test
    public void testPhones() {
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<phones>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<phone>")+ "}"));
    }
    
    @Test
    public void testMails() {
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<emails>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<email>")+ "}"));
    }
    
    @Test
    public void testSites() {
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<my-sites>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<my-site>")+ "}"));
    }
    
    @Test
    public void testAllOptionalParts() {
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<education>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<work-experience>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<certificates>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<language-skills>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<computer-skills>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<driving-licence>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<characteristics>")+ "}{}"));
        assertTrue(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<hobbies>")+ "}{}"));
    }

//    private String getStringConstants(String language) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        try(BufferedReader br = new BufferedReader(new FileReader(Config.DBUTIL + "\\texts.xml"))) {
//            for (String s = br.readLine(); s != null; s=br.readLine()) {
//                sb.append(s);
//                sb.append(System.lineSeparator());
//            }
//        }
//        String temp = sb.toString();
//        String array[] = temp.split("<language id=");
//        for (int i = 0 ; i < array.length; i++) {
//            if (array[i].contains("\"" + language + "\""))
//                temp = array[i];
//        }
//        return temp;
//    }
//    
//    private String getConstant(String element) {
//        String closeElement = "</" + element.substring(1);
//        int first = constants.indexOf(element);
//        int last = constants.indexOf(closeElement);
//        String output = constants.substring(first + element.length(),last);
//        return output;
//    }
    
}
