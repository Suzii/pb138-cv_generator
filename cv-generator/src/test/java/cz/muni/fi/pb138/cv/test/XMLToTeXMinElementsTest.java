/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.test;

import cz.muni.fi.pb138.cv.service.Config;
import cz.muni.fi.pb138.cv.service.CvServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jozef Živčic
 */
public class XMLToTeXMinElementsTest {
    
    private String file;
    private String constants;
    private static final String language = "en";
    private static final String outputFileName = "resultCV.tex";
    
    @Before
    public void setUp() throws IOException {
        CvServiceImpl cvService;
        cvService = new CvServiceImpl();
        cvService.transforToTexFile("minElements", language);
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
    public void testAllOptionallyElementsNotPresent() {
        assertFalse(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<state>")+ "}"));
        assertFalse(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<education>")+ "}"));
        assertFalse(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<work-experience>")+ "}"));
        assertFalse(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<certificates>")+ "}"));
        assertFalse(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<language-skills>")+ "}"));
        assertFalse(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<computer-skills>")+ "}"));
        assertFalse(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<driving-licence>")+ "}"));
        assertFalse(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<characteristics>")+ "}"));
        assertFalse(file.contains("\\NewPart{" + ExtractConstants.getConstant(constants,"<hobbies>")+ "}"));
    }
}