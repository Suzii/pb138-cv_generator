/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.service;

import java.io.File;
import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author pato
 */
public class FileService {

    private Document document = null;
    private final static Logger log = LoggerFactory.getLogger(FileService.class);

    /**
     * method witch chcek if directory for all CV files exist, if not create
     * directory
     *
     * @return true if diretory exist or is created , otherwise false
     */
    public boolean checkDirectory() {
        File dir = new File(Config.DIRECTORY);
        File utils = new File(Config.DBUTIL);
        if (!dir.exists()) {
            try {
                log.info("creating dir.");
                dir.mkdir();
                utils.mkdir();
            } catch (SecurityException ex) {
                log.error("CANNOT CREATE DIRECTORY.",ex);
                return false;
            }
        }
        return dir.exists();
    }

    /**
     * method which checked if file for user logins already exist
     *
     * @return true if file exist or is created , otherwise false
     */
    public boolean checkUsersFile() {
        File xml = new File(Config.LOGINS);
        try {
            if (xml.createNewFile()) {
                log.debug("Checking user file db.");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                dbFactory.setValidating(false);
                dbFactory.setNamespaceAware(false);
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                document = dBuilder.newDocument();

                Element root = document.createElement("users");
                document.appendChild(root);

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(Config.LOGINS));
                transformer.transform(source, result);
                return true;
           } else {
                return false;
            }
        } catch (IOException|ParserConfigurationException|TransformerFactoryConfigurationError|TransformerException ex) {
            log.error("Error when creating user xml file." , ex);
            return false;
        }
       //return false;
    }

    /**
     * Method to get users document
     *
     * @return document of users
     */
    public Document getDocument() {
        return document;
    }
}
