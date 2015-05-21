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
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Zuzana
 */
public class SessionServiceImplTest {
    
    MockHttpServletRequest request;
    SessionServiceImpl instance = new SessionServiceImpl();

    @Before
    public void setUp() {
        this.request = new MockHttpServletRequest();
    }

    /**
     * Test of createSessionLogin method, of class SessionServiceImpl.
     */
    @Test
    public void testCreateSessionLogin() {
        System.out.println("createSessionLogin");
        String login = "anicka";        
        instance.createSessionLogin(request, login);
        assertEquals(login, request.getSession().getAttribute("login"));
    }
    
    /**
     * Test of createSessionLogin method, of class SessionServiceImpl.
     */
    @Test
    public void testCreateSessionLogin_update() {
        System.out.println("createSessionLogin when one already existed");
        String login_former = "hackre";     
        String login = "anicka";     
        request.getSession().setAttribute("login", login_former);
        instance.createSessionLogin(request, login);
        assertEquals(login, request.getSession().getAttribute("login"));
    }

    /**
     * Test of getSessionLogin method, of class SessionServiceImpl.
     */
    @Test
    public void testGetSessionLogin() {
        System.out.println("getSessionLogin");
        String expResult = "anicka";
        request.getSession().setAttribute("login", expResult);
        String result = instance.getSessionLogin(request);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getSessionLogin method, of class SessionServiceImpl.
     */
    @Test
    public void testGetSessionLogin_notExisting() {
        System.out.println("getSessionLogin no login attr set");
        String expResult = null;
        request.getSession().setAttribute("login_bad", "whatever");
        String result = instance.getSessionLogin(request);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteSessionLogin method, of class SessionServiceImpl.
     */
    @Test
    public void testDeleteSessionLogin() {
        System.out.println("deleteSessionLogin");
        request.getSession().setAttribute("login", "anicka");
        instance.deleteSessionLogin(request);
        assertNull(instance.getSessionLogin(request));
    }
    
   /**
    * Tests complex functionality of SessionService
    */
    @Test
    public void testCreateGetDeleteUseCase(){
        System.out.println("trstCreateGetDeleteUseCase");
        String login = "anicka";
        //no user currently logged in
        assertNull(instance.getSessionLogin(request));
        
        //log anicka in
        instance.createSessionLogin(request, login);
        
        //check if anicka is logged in
        assertEquals(login, instance.getSessionLogin(request));
        
        //log anicka out
        instance.deleteSessionLogin(request);
        
        //check that nobody is logged in now
        assertNull(instance.getSessionLogin(request));
    }
    
    /**
     * Tests invalidation of login after session timeout.
     */
    public void testLoginInvalidation() throws InterruptedException{
        HttpServletRequest req = new MockHttpServletRequest();
        req.getSession().setMaxInactiveInterval(3);
        String login = "anicka";
        instance.createSessionLogin(request, login);
        assertEquals(login, instance.getSessionLogin(req));
        
        wait(5000l);
        assertNull(instance.getSessionLogin(req));
        
    }
}
