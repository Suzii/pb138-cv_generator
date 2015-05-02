/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

/**
 * Service for user accounts. 
 * 
 * @author Zuzana
 */
public interface UserService {
    
    /**
     * Checks whether given login already exists in database.
     * @param login login to be checked
     * @return true, if login exists, false otherwise
     */
    boolean checkIfExists(String login);
    
    /**
     * Registers new user with provided credentials.
     * First checks, if login is not already used.
     * 
     * @param login login of new user
     * @param passwordHash hash of user's password
     * @return true if process was successful, false otherwise
     */
    boolean registerNewUser(String login, String passwordHash);
    
    /**
     * Verifies provided user credentials.
     * 
     * @param login login to look for
     * @param passwordHash hash of password associated with login
     * @return true if login and passwordHash match any entry in database
     */
    boolean verifyCredentials(String login, String passwordHash);
}
