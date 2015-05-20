/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

import javax.servlet.http.HttpServletRequest;
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
public class SessionServiceImplTest {
    
    public SessionServiceImplTest() {
    }
      
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createSessionLogin method, of class SessionServiceImpl.
     */
    //@Test
    public void testCreateSessionLogin() {
        System.out.println("createSessionLogin");
        HttpServletRequest request = null;
        String login = "";
        SessionServiceImpl instance = new SessionServiceImpl();
        instance.createSessionLogin(request, login);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSessionLogin method, of class SessionServiceImpl.
     */
    //@Test
    public void testGetSessionLogin() {
        System.out.println("getSessionLogin");
        HttpServletRequest request = null;
        SessionServiceImpl instance = new SessionServiceImpl();
        String expResult = "";
        String result = instance.getSessionLogin(request);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteSessionLogin method, of class SessionServiceImpl.
     */
    //@Test
    public void testDeleteSessionLogin() {
        System.out.println("deleteSessionLogin");
        HttpServletRequest request = null;
        SessionServiceImpl instance = new SessionServiceImpl();
        instance.deleteSessionLogin(request);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
