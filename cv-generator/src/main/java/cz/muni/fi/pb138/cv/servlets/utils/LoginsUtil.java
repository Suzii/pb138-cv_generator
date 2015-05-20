/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

/**
 *
 * @author Zuzana
 */
public interface LoginsUtil {
    
    /**
     * Verifies if user with provided creadentials exists.
     * If yes, returns null. If not, returns meaningfull message.
     * 
     * @param login login of user to look for
     * @param password password for provided login to check
     * @return null if credentials are ok, message otherwise
     */
    String verifyCredentials(String login, String password);
    
    /**
     * Tries creating an accout from provided credentials.
     * If passwords match and no such login is used so far, creates new account 
     * and returns null. Otherwise returns meaningful message.
     * @param login login to be used for new account
     * @param password password for provided login
     * @param password2 password for check
     * @return null if new account was created, message otherwise
     */
    String tryToCreateAnAccount(String login, String password, String password2);
}
