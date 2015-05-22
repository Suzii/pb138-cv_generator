/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.xslt;

import cz.muni.fi.pb138.cv.service.Config;
import cz.muni.fi.pb138.cv.service.CvServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Jozef Živčic
 */
public class SkXMLToTeXOnePhoneSocialMailTest {
    private static final String DB = Config.TEST_DB;
    private static final String MIKTEX = new Config().getLatexFolder();
    private static final String LANGUAGE = "sk";
    private static final String OUTPUT_FILE_NAME = "resultCV.tex";
    private static final String OUTPUT_TEX = DB + "\\Utils\\" + OUTPUT_FILE_NAME;
    private static final String INPUT_XML = "one_element_phone_mail_social";
    private String file;
    private String constants;
    
    public SkXMLToTeXOnePhoneSocialMailTest() throws IOException {
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
    
    @After
    public void tearDown() {
        File f = new File(Config.DBUTIL + "\\" + OUTPUT_FILE_NAME);
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
    public void testOneElements() {
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<phone>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<phones>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<email>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<emails>")+ "}"));
        assertTrue(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<my-site>")+ "}"));
        assertFalse(file.contains("\\PersonalEntry{" + ExtractConstants.getConstant(constants,"<my-sites>")+ "}"));
    }
}
