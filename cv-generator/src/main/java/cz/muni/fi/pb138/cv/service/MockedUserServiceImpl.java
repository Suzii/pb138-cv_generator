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
    public boolean registerNewUser(String login, String password) {
        return !login.equals("admin");
    }

    @Override
    public boolean verifyCredentials(String login, String password) {
        String usr = "admin";
        String psswd = "admin";
        return login.equals(usr) && password.equals(psswd);
    }
    
}
