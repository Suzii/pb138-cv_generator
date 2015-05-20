/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

import cz.muni.fi.pb138.cv.servlets.utils.LoginsUtil;
import cz.muni.fi.pb138.cv.service.UserService;
import cz.muni.fi.pb138.cv.service.UserServiceImpl;

/**
 *
 * @author Zuzana
 */
public class LoginsUtilImpl implements LoginsUtil {

    private UserService userService;

    public LoginsUtilImpl(UserService service) {
        this.userService = service;
    }

    @Override
    public String verifyCredentials(String login, String password) {
        String result = null;
        //find out if user exists
        if (!userService.checkIfExists(login)) {
            result = "Username: " + login + " does not exists";
            //check password
        } else if (!userService.verifyCredentials(login, password)) {
            result = "Wrong password.";
        }
        return result;
    }

    @Override
    public String tryToCreateAnAccount(String login, String password, String password2) {
        String result = null;
        if (login == null || password == null || password2 == null) {
            result = "All fields are required!";
        } else if (!password.equals(password2)) {
            result = "Passwords do not match!";
        } else if (userService.checkIfExists(login)) {
            result = "Username: " + login + " already taken.";
        } else if (!userService.registerNewUser(login, password)) {
            result = "Error while creating account.";
        }
        return result;
    }

}
