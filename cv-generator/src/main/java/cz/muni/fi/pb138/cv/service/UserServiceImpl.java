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
//import java.util.logging.Logger;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author pato
 */
public class UserServiceImpl implements UserService {

    private Document logins;
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl() {
        log.debug("Checking database direcotry.");
        FileService fs = new FileService();

        if (fs.checkDirectory()) {
            if (fs.checkUsersFile()) {
                logins = fs.getDocument();
            } else {
                openDocument();
            }
        } else {
            if (fs.checkUsersFile()) {
                logins = fs.getDocument();
            } else {
                openDocument();
            }
        }
    }

    @Override
    public boolean checkIfExists(String login) {
        openDocument();
        try {
            log.debug("login check : " + login);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/users/user[./login/text()=\""+login+"\"]/login/text()");
            String userLogin = expr.evaluate(logins);
            log.debug("Found user :" + userLogin);
            return login.equals(userLogin);
            //return (!userLogin.trim().equals(""));

        } catch (XPathExpressionException ex) {
            log.error("Problem when checking user : " ,ex);

            return false;
        }
        /* NodeList usersLogins= logins.getElementsByTagName("login");
            
         for(int i = 0;i < usersLogins.getLength(); i++){
         Element el = (Element) usersLogins.item(i);
         if (login.equals(el.getTextContent())) return true;
         }*/
        //return false;
    }

    @Override
    public boolean registerNewUser(String login, String password) {
        if (!checkIfExists(login)) {
            log.debug("Registering new user : " + login + " with psw hash : " +password);
            Element root = logins.getDocumentElement();

            Element newUser = logins.createElement("user");
            newUser.appendChild(createElementWithText("login", login));
            try {
                newUser.appendChild(createElementWithText("passwordHash", PasswordHash.createHash(password)));
            } catch (NoSuchAlgorithmException|InvalidKeySpecException ex) {
                log.error("Create user - Problem with hashing password : " + password ,ex );
            } 

            root.appendChild(newUser);

            try {
                log.debug("Adding to xml db.");
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(logins);
                StreamResult result = new StreamResult(new File(Config.LOGINS));
                transformer.transform(source, result);               
            }catch(TransformerFactoryConfigurationError|TransformerException ex){
                log.error("Error ocurred by adding to db. ",ex);
                return false;
            }
            return true;
        }      
        return false;
    }

    @Override
    public boolean verifyCredentials(String login, String password) {
        if (checkIfExists(login)) {
            try {
                log.debug("Credentials verifing ,user: " +login + "  psw hash : "+password);
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                //XPathExpression expr = xpath.compile("/users/user[./login/text()=\""+login+"\" and ./passwordHash/text()=\""+passwordHash+"\"]/passwordHash/text()");
                XPathExpression expr = xpath.compile("/users/user[./login/text()=\""+login+"\"]/passwordHash/text()");
                String passwordHash = expr.evaluate(logins);
                log.debug("Founded psw: "+ password);
                try {
                    //return login.equals(userLogin);"\"
                    //return (password.equals(passwordHash));
                    return PasswordHash.validatePassword(password, passwordHash);
                } catch (NoSuchAlgorithmException|InvalidKeySpecException ex) {
                   log.error("Verify credentials - Problem with hashing password : " + password + " and login : " + login,ex );
                }

            } catch (XPathExpressionException ex) {
                log.error("Error by verifing credentials of user :" + login ,ex);
                return false;
            }
        }
        return false;
    }

    /**
     * Create new element in Users document
     *
     * @param name is name of element
     * @param text is text value of element
     * @return new created element
     */
    private Element createElementWithText(String name, String text) {
        Element el = logins.createElement(name);
        el.setTextContent(text);
        return el;
    }

    private void openDocument() {
        log.debug("Reload of user document. ");
        File doc = new File(Config.LOGINS);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            logins = dBuilder.parse(doc);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            log.error("Error when opening(loading) users document. ");
        }
    }

}
