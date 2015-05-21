/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.test;

import cz.muni.fi.pb138.cv.service.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Jozef Živčic
 */
public class SkXMLToTeXMaxElementsTest {
    
    private static final String DB = "C:\\pb138-database";
    private static final String MIKTEX = "C:\\Program Files (x86)\\MiKTeX 2.9\\miktex\\bin";
    private static final String LANGUAGE = "sk";
    private static final String INPUT_XML = "anicka";
    private static final String OUTPUT_FILE_NAME = "resultCV.tex";
    private static final String OUTPUT_TEX = Config.DBUTIL + "\\" + OUTPUT_FILE_NAME;
    private String file;
    private String constants;
    
    public SkXMLToTeXMaxElementsTest() throws IOException {
        CvServiceImpl cvService;
        cvService = new CvServiceImpl(DB,MIKTEX);
        cvService.transformToTexFile(INPUT_XML, LANGUAGE);
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(OUTPUT_TEX))) {
            for (String s = br.readLine(); s != null; s=br.readLine()) {
                sb.append(s);
                sb.append(System.lineSeparator());
            }
            file = sb.toString();
        }
        constants = ExtractConstants.getStringConstants(LANGUAGE);
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
}
