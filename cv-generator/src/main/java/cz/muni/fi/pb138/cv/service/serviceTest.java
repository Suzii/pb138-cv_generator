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
public class serviceTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("welcome in main;");
        UserServiceImpl us = new UserServiceImpl("C:\\pb138-database");
        CvServiceImpl cvs = new CvServiceImpl("C:\\pb138-database","C:\\Programy\\MiKTeX 2.9\\miktex\\bin\\");
        String p = "pato";
        System.out.println(us.registerNewUser("jozo",p));
        System.out.println(us.verifyCredentials(p, p));
        System.out.println(us.checkIfExists("jozo"));
        System.out.println(us.registerNewUser("dddd",p));
        System.out.println(us.checkIfExists("dddd"));
        System.out.println("Trying xslt");
        cvs.generatePdf("sample-cv", "sk");
    }
    
}
