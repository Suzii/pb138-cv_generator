/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

/**
 *
 * @author pato
 */
public class Config {
        //musi to byt na takejto absolutnej ceste, lebo inak by sa k tomu nedalo pristupit normalne (servlet bezi v inom prieciku)
    public static final String SAMPLE_JSON_PATH = "C:/sample-data.json";
    public static final String SAMPLE_PDF_PATH = "C:/cv_9.pdf";
    public static final String DIRECTORY = "C:/pb138-database";
    public static final String LOGINS = "C:/pb138-database/users.xml";
    public static final String DBUTIL="C:/pb138-database/Utils";
    public static final String PATHTOLATEXBIN="C:\\Programy\\MiKTeX 2.9\\miktex\\bin\\";
    // public static final String XMLVALIDATIONFILE;    TODO
}
