/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;

/**
 * Service for CVs.
 * Every method is duplicated, depending on whether it deals with CV in JSON or
 * XML format. Frontend uses only JSON, so we need to abstract it from XML conversion.
 * CVs are stored in XML and all the logic is in XML so every method dealing 
 * with JSON will first convert the JSON to XML and then delegate work to XML method.
 * 
 * @author Zuzana
 */
public interface CvService {
    
    /**
     * Loads CV of user associated with given login.
     * @param login login of user whom CV should be loaded
     * @return JSON object of users CV, null if CV does not exist
     */
    JSONObject loadCvJSON(String login);
    
    /**
     * Loads CV of user associated with given login.
     * @param login login of user whom CV should be loaded
     * @return DOM of users CV (XML format), null if CV does not exist
     */
    Document loadCvXML(String login);
    
    /**
     * Stores given CV to database and associates it with given login.
     * @param login login of CV to be stored (login has to exist in users' db)
     * @param cv CV to be stored to database in JSON format
     * @return true if saved, false on error
     */
    boolean saveCv(String login, JSONObject cv);
    
    /**
     * Stores given CV to database and associates it with given login.
     * @param login login of CV to be stored (login has to exist in users' db)
     * @param cv CV to be stored to database in XML format
     * @return true if saved, false on error
     */
    boolean saveCv(String login, Document cv);
    
    /**
     * Generates pdf of CV associated with given login
     * @param login login of user whom CV should be generated
     * @return file containing CV in pdf format of user with given login
     */
    File generatePdf(String login);
    
    /**
     * Checks validity of given DOM (CV in XML format) against defined XML Schema.
     * @param cv cv in XML format whose validity should be checked
     * @return message with error, null if ok
     */
    String checkValidity(Document cv);
    
    /**
     * Checks validity of given CV in JSON format against defined XML Schema.
     * @param cv cv in JSON format whose validity should be checked
     * @return message with error, null if ok
     */
    String checkValidity(JSONObject cv);
}
