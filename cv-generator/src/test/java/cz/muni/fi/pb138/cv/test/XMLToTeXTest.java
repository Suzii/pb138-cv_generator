/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.test;

import cz.muni.fi.pb138.cv.service.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;
import org.junit.After;
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
        }catch(IOException ex) {
            
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
    }
}
