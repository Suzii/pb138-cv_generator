/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.IOException;
import static java.lang.Math.log;
import java.util.Properties;

/**
 *
 * @author pato
 */
public class Config {

    //musi to byt na takejto absolutnej ceste, lebo inak by sa k tomu nedalo
    //pristupit normalne (servlet bezi v inom prieciku)
    public static final String SAMPLE_JSON_PATH = "src\\main\\resources\\TestData\\pb138-database\\sample-data.json";
    public static final String SAMPLE_XML_PATH = "src\\main\\resources\\TestData\\pb138-database\\sample-cv.xml";
    public static final String TEST_DB = "src\\main\\resources\\TestData\\pb138-database";

    public static final String SAMPLE_PDF_PATH = "C:/cv_9.pdf";
    public static final String DIRECTORY = "C:/pb138-database";
    public static final String LOGINS = "C:/pb138-database/users.xml";
    public static final String DBUTIL = "C:/pb138-database/Utils";
    // public static final String PATHTOLATEXBIN="C:\\Programy\\MiKTeX 2.9\\miktex\\bin\\";  //Cypo
    public static final String PATHTOLATEXBIN = "C:\\Program Files (x86)\\MiKTeX 2.9\\miktex\\bin\\"; //Suzii
    // public static final String XMLVALIDATIONFILE;    TODO

    public String getLatexFolder() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/cvgenerator.properties"));
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        return properties.getProperty("latex.folder");
    }
}
