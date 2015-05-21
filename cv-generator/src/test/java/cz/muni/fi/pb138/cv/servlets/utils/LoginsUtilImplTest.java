/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

import cz.muni.fi.pb138.cv.service.MockedUserServiceImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zuzana
 */
public class LoginsUtilImplTest {
    
    private final LoginsUtil instance;
    
    public LoginsUtilImplTest() {
        this.instance = new LoginsUtilImpl(new MockedUserServiceImpl());
    }
        
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of verifyCredentials method, of class LoginsUtilImpl.
     * This test tests existing user with valid password.
     */
    @Test
    public void testVerifyCredentials_ok() {
        System.out.println("verifyCredentials should pass");
        String login = "admin";
        String password = "admin";
        String expResult = null;
        String result = instance.verifyCredentials(login, password);
        assertEquals("Admin login with admin password should be ok", expResult, result);
    }

    /**
     * Test of verifyCredentials method, of class LoginsUtilImpl.
     * This test tests existing user with wrong password.
     */
    @Test
    public void testVerifyCredentials_wrongPsswd() {
        System.out.println("verifyCredentials with wrong passowrd");
        String login = "admin";
        String password = "too_bad";
        String expResult = "Wrong password!";
        String result = instance.verifyCredentials(login, password);
        assertEquals("Admin login with wrong password", expResult, result);
    }
    
    /**
     * Test of verifyCredentials method, of class LoginsUtilImpl.
     * This test tests non-existing user.
     */
    @Test
    public void testVerifyCredentials_wrongUser() {
        System.out.println("verifyCredentials with non-existing user");
        String login = "iDontExist";
        String password = "too_bad";
        String expResult = "Username: " + login + " does not exists!";
        String result = instance.verifyCredentials(login, password);
        assertEquals("Non existing user", expResult, result);
    }
    
    /**
     * Test of tryToCreateAnAccount method, of class LoginsUtilImpl.
     * Should pass, user Anicka does not exist yet.
     */
    @Test
    public void testTryToCreateAnAccount_ok() {
        System.out.println("tryToCreateAnAccount, valid data");
        String login = "Anicka";
        String password = "Anicka";
        String password2 = "Anicka";
        String expResult = null;
        String result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of tryToCreateAnAccount method, of class LoginsUtilImpl.
     * Should return error message, user admin already exists.
     */
    @Test
    public void testTryToCreateAnAccount_usernameAlreadyTaken() {
        System.out.println("tryToCreateAnAccount username already taken");
        String login = "admin";
        String password = "admin";
        String password2 = "admin";
        String expResult = "Username: " + login + " already taken!";
        String result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of tryToCreateAnAccount method, of class LoginsUtilImpl.
     * Should return error message, given passwords do not match.
     */
    @Test
    public void testTryToCreateAnAccount_passwdsDoNotMatch() {
        System.out.println("tryToCreateAnAccount passwords do not match");
        String login = "admin";
        String password = "admin1";
        String password2 = "admin2";
        String expResult = "Passwords do not match!";
        String result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of tryToCreateAnAccount method, of class LoginsUtilImpl.
     * Should return error message, login property is empty.
     */
    @Test
    public void testTryToCreateAnAccount_invalidLogin() {
        System.out.println("tryToCreateAnAccount invalid login provided");
        String login = null;
        String password = "admin1";
        String password2 = "admin2";
        String expResult = "All fields are required!";
        String result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals("Login is null, should not be accepted", expResult, result);
        
        login = "";
        result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals("Login is of length 0, should not be accepted", expResult, result);
        
        login = "    ";
        result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals("Login consists solely of whitespaces, should not be accepted", expResult, result);
    }
    
    /**
     * Test of tryToCreateAnAccount method, of class LoginsUtilImpl.
     * Should return error message, given passwords do not match.
     */
    @Test
    public void testTryToCreateAnAccount_passwdIsEmpty() {
        System.out.println("tryToCreateAnAccount one of passwords is empty");
        String login = "anicka";
        String expResult = "All fields are required!";
        
        String password = null;
        String password2 = "admin2";
        String result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals("First password null.", expResult, result);
        
        password = "";
        password2 = "admin2";
        result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals("First password empty.", expResult, result);
        
        
        password = "admin1";
        password2 = null;
        result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals("Second password null.", expResult, result);
        
        
        password = "admin1";
        password2 = "";
        result = instance.tryToCreateAnAccount(login, password, password2);
        assertEquals("Second password empty.", expResult, result);
    }
}
