/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import java.io.IOException;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
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
   // private final static org.slf4j.Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl() {
        //log.info("USER SERVICE");
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
            System.out.println("login check : " + login);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/users/user[./login/text()=\""+login+"\"]/login/text()");
            String userLogin = expr.evaluate(logins);
            System.out.println("usr :" + userLogin+";");
            return login.equals(userLogin);
            //return (!userLogin.trim().equals(""));

        } catch (XPathExpressionException ex) {
            Logger.getLogger(UserServiceImpl.class
                    .getName()).log(Level.SEVERE, null, ex);

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

            try {
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(logins);
                StreamResult result = new StreamResult(new File(Config.LOGINS));
                transformer.transform(source, result);               
            }catch(TransformerFactoryConfigurationError|TransformerException ex){
                return false;
            }
            return true;
        }      
        return false;
    }

    @Override
    public boolean verifyCredentials(String login, String passwordHash) {
        if (checkIfExists(login)) {
            try {
                System.out.println("cred");
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile("/users/user[./login/text()=\""+login+"\" and ./passwordHash/text()=\""+passwordHash+"\"]/passwordHash/text()");
                String password = expr.evaluate(logins);
                System.out.println("pss: "+ password);
                //return login.equals(userLogin);"\"
                return (password.equals(passwordHash));

            } catch (XPathExpressionException ex) {
                Logger.getLogger(UserServiceImpl.class
                        .getName()).log(Level.SEVERE, null, ex);

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
        File doc = new File(Config.LOGINS);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            logins = dBuilder.parse(doc);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            Logger.getLogger(FileService.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
