/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author pato
 */
public class UserServiceImpl implements UserService {

    private Document logins;

    public UserServiceImpl() {
        FileService fs = new FileService();

        if (fs.checkDirectory()) {
            if (fs.checkUsersFile()) {
                logins = fs.getDocument();
            }
            //else error???
        }

    }

    @Override
    public boolean checkIfExists(String login) {
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/users/user/login[text()=login]");
            String userLogin = expr.evaluate(logins);
            //return login.equals(userLogin);
            return (userLogin != null);

        } catch (XPathExpressionException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean registerNewUser(String login, String passwordHash) {
        if (!checkIfExists(login)) {
            Element root = logins.getDocumentElement();

            Element newUser = logins.createElement("user");
            newUser.appendChild(createElementWithText("login", login));
            newUser.appendChild(createElementWithText("passwordHash", passwordHash));

            root.appendChild(newUser);
        }
        return false;
    }

    @Override
    public boolean verifyCredentials(String login, String passwordHash) {
        if (!checkIfExists(login)) {
            try {
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile("/users/user[./login/text()=login and ./password/text()=passwordHash]/password");
                String password = expr.evaluate(logins);
                //return login.equals(userLogin);
                return (password != null);

            } catch (XPathExpressionException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return false;
    }
    
    /**
     * Create new element in Users document
     * @param name is name of element
     * @param text is text value of element
     * @return new created element
     */
    private Element createElementWithText(String name, String text) {
        Element el = logins.createElement(name);
        el.setTextContent(text);
        return el;
    }

}
