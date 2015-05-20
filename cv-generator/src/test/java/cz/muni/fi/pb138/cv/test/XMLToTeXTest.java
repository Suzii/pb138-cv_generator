/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.test;

import cz.muni.fi.pb138.cv.service.*;
import java.io.BufferedReader;
import java.io.File;
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
    
    @Before
    public void setUp() {
        CvServiceImpl cvService;
        cvService = new CvServiceImpl();
        cvService.transforToTexFile("anicka", "en");
        StringBuffer sb = new StringBuffer();
        try(BufferedReader br = new BufferedReader(new FileReader(Config.DBUTIL + "\\resultCV.tex"))) {
            for (String s = br.readLine(); s != null; s=br.readLine()) {
                sb.append(s);
                sb.append(System.lineSeparator());
            }
            file = sb.toString();
        }catch(IOException ex){
        }
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
        assertTrue(file.contains("\\MySlogan{Curriculum vitae}"));
        assertTrue(file.contains("\\NewPart{PERSONAL DETAILS}"));
        assertTrue(file.contains("\\PersonalEntry{Name}"));
        assertTrue(file.contains("\\PersonalEntry{Surname}"));
        assertTrue(file.contains("\\PersonalEntry{Date of birth}"));
        assertTrue(file.contains("\\PersonalEntry{Country}"));
    }
    
    @Test
    public void testState() {
        assertTrue(file.contains("\\PersonalEntry{State}"));
    }
    
    @Test
    public void testPhones() {
        assertTrue(file.contains("\\PersonalEntry{Phones}"));
        assertFalse(file.contains("\\PersonalEntry{Phone}"));
    }
    
    @Test
    public void testMails() {
        assertTrue(file.contains("\\PersonalEntry{Emails}"));
        assertFalse(file.contains("\\PersonalEntry{Email}"));
    }
    
    @Test
    public void testSites() {
        assertTrue(file.contains("\\PersonalEntry{My sites}"));
        assertFalse(file.contains("\\PersonalEntry{My site}"));
    }
    
    @Test
    public void testAllOptionalParts() {
        assertTrue(file.contains("\\NewPart{EDUCATION}{}"));
        assertTrue(file.contains("\\NewPart{WORK EXPERIENCE}{}"));
        assertTrue(file.contains("\\NewPart{CERTIFICATES}{}"));
        assertTrue(file.contains("\\NewPart{LANGUAGE SKILLS}{}"));
        assertTrue(file.contains("\\NewPart{COMPUTER SKILLS}{}"));
        assertTrue(file.contains("\\NewPart{DRIVING LICENCE}{}"));
        assertTrue(file.contains("\\NewPart{CHARACTERISTICS}{}"));
        assertTrue(file.contains("\\NewPart{HOBBIES}{}"));
    }
    
}
