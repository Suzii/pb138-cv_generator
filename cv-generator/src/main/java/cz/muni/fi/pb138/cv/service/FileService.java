/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author pato
 */
public class FileService {
    
    private Document document = null;
    /**
     * method witch chcek if directory for all CV files exist, if not create directory
     * @return true if diretory exist or is created , otherwise false
     */
    public  boolean checkDirectory(){
        File dir = new File(Config.DIRECTORY);
        if( !dir.exists()){
            try{
                dir.mkdir();
            }catch(SecurityException ex){
                return false;
            }
        }
        return dir.exists();
    }
    
    /**
     * method which checked if file for user logins already exist
     * @return true if file exist or is created , otherwise false
     */
    public boolean checkUsersFile(){
        File xmlFile =  new File(Config.LOGINS);
        try{
           if( xmlFile.createNewFile()){
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(false);
            dbFactory.setNamespaceAware(false);
            DocumentBuilder dBuilder;           
               try {
                   dBuilder = dbFactory.newDocumentBuilder();
                   document = dBuilder.parse(Config.LOGINS);
               } catch (SAXException|ParserConfigurationException ex) {
                   Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
                   return false;
               }
               
             Element rootElem = document.createElement("users");
             return true;
        }
        }catch( IOException ex){
            return false;
        }
        return xmlFile.exists();
    }
    
    /**
     * Method to get users document
     * @return document of users
     */
    public Document getDocument(){
        return document;
    }
}
