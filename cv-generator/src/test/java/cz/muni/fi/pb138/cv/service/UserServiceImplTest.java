/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author pato
 */
public class UserServiceImplTest {
    private final UserService userService;
    private static final String users = "users.xml";
    private File usersFile;
    private Document logins;
    
    public UserServiceImplTest() {
        userService = new UserServiceImpl("./null");
    }
    
    @Before
    public void setUp() {
        usersFile = new File("./null/"+users);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            logins = dBuilder.parse(usersFile);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            fail();
        }
         Element root = logins.createElement("users");
         logins.appendChild(root);
         Element el = logins.createElement("user");
         el.setTextContent("admin");
         root.appendChild(el);
         el = logins.createElement("passwordHash");
        try {
            el.setTextContent(PasswordHash.createHash("admin"));
        } catch (NoSuchAlgorithmException|InvalidKeySpecException ex) {
            fail();
        }
        root.appendChild(el);
    }
    
    @After
    public void tearDown() {
        usersFile.delete();
    }

    /**
     * Test of checkIfExists method, of class UserServiceImpl.
     */
    @Test
    public void testCheckIfExistsExistingUser() {
        assertTrue(userService.checkIfExists("admin"));
    }
    
    /**
     * Test of checkIfExists method, of class UserServiceImpl.
     */
    @Test
    public void testCheckIfExistsNotExistingUser() {
        assertFalse(userService.checkIfExists("adminAdmin"));
    }

    /**
     * Test of registerNewUser method, of class UserServiceImpl.
     */
    @Test
    public void testRegisterNewUser() {
        fail();
    }

    /**
     * Test of verifyCredentials method, of class UserServiceImpl.
     */
    @Test
    public void testVerifyCredentials() {
        assertTrue(userService.verifyCredentials("admin", "admin"));
    }
    
}
