/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

/**
 *
 * @author Zuzana
 */
public class MockedUserServiceImpl implements UserService{

    @Override
    public boolean checkIfExists(String login) {
        return login.equals("admin");
    }

    @Override
    public boolean registerNewUser(String login, String passwordHash) {
        return true;
    }

    @Override
    public boolean verifyCredentials(String login, String passwordHash) {
        String usr = "admin";
        String psswd = "21232f297a57a5a743894a0e4a801fc3"; //MD5 of "admin"
        return login.equals(usr) && passwordHash.equals(psswd);
    }
    
}
