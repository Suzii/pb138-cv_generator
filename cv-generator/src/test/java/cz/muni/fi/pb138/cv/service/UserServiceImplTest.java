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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;
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
    private static File usersFile;
    private static Document logins;
    private final static org.slf4j.Logger log = LoggerFactory.getLogger(UserServiceImplTest.class);

    public UserServiceImplTest() {
        userService = new UserServiceImpl("Test");
    }

    @BeforeClass
    public static void setUpBeforeClass() {
        File dir = new File("Test");
        dir.mkdir();
        usersFile = new File("Test/" + users);
        log.debug(usersFile.getAbsolutePath());
        try {
            usersFile.createNewFile();
        } catch (IOException ex) {
            log.debug("ERROR", ex);
            fail();
        }
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            logins = dBuilder.newDocument();
        } catch (ParserConfigurationException ex) {
            fail();
        }
        Element root = logins.createElement("users");
        Element u = logins.createElement("user");

        Element el = logins.createElement("login");
        el.setTextContent("admin");
        u.appendChild(el);
        el = logins.createElement("passwordHash");
        try {
            el.setTextContent(PasswordHash.createHash("admin"));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            fail();
        }
        u.appendChild(el);
        root.appendChild(u);
        logins.appendChild(root);
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(logins);
            StreamResult result = new StreamResult(usersFile);
            transformer.transform(source, result);
        } catch (TransformerFactoryConfigurationError | TransformerException ex) {
            fail();
        }
    }

    @AfterClass
    public static void tearDownAfterClass() {
        usersFile.delete();
        File dir = new File("Test");
        dir.delete();
    }

    /**
     * Test of checkIfExists method, of class UserServiceImpl.
     */
    @Test
    public void testCheckIfExistsExistingUser() {
        assertTrue("Check if admin already exist.", userService.checkIfExists("admin"));
    }

    /**
     * Test of checkIfExists method, of class UserServiceImpl.
     */
    @Test
    public void testCheckIfExistsNotExistingUser() {
        assertFalse("User should not exists.", userService.checkIfExists("adminAdmin"));
    }

    /**
     * Test of registerNewUser method, of class UserServiceImpl.
     */
    @Test
    public void testRegisterNewUserNotExsitingUser() {
        assertTrue("Create new user.", userService.registerNewUser("admin2", "blah"));
        assertTrue("Check if new user is registered", userService.checkIfExists("admin2"));

    }

    /**
     * Test of registerNewUser method, of class UserServiceImpl.
     */
    @Test
    public void testRegisterNewUserExsitingUser() {
        assertFalse("Register new user with existing login .", userService.registerNewUser("admin", "blah"));
        assertFalse("Register new user with existing login and password.", userService.registerNewUser("admin", "admin"));
    }

    /**
     * Test of verifyCredentials method, of class UserServiceImpl.
     */
    @Test
    public void testVerifyCredentialsRegisteredUserOK() {
        assertTrue("Verify credentials of registerd user.", userService.verifyCredentials("admin", "admin"));
    }

    /**
     * Test of verifyCredentials method, of class UserServiceImpl.
     */
    @Test
    public void testVerifyCredentialsNotRegisteredUser() {
        assertFalse("Verify credentials of registerd user.", userService.verifyCredentials("admin35", "admin"));
    }

    /**
     * Test of verifyCredentials method, of class UserServiceImpl.
     */
    @Test
    public void testVerifyCredentialsRegisteredUserBadPassword() {
        assertFalse("Verify credentials of registerd user.", userService.verifyCredentials("admin", "Admin"));
    }

}
