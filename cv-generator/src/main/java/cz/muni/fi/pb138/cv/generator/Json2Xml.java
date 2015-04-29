/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.generator;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Peto
 */
public class Json2Xml {
    private String data;
    
    private DocumentBuilderFactory docFactory;
    private DocumentBuilder docBuilder;
    private Document doc;
    private JSONObject jsonRoot;
    
    public static void main(String[] args) {
        String data;
        try {
            List<String> lines = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "\\..\\sample_data\\json_example.json"));
            data = String.join("", lines);
        } catch (IOException ex) {
            Logger.getLogger(Json2Xml.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        Json2Xml p = new Json2Xml(data);
    }
    
    public Json2Xml(String json_input){
        data = "{"
        + "\"autentication\": {\"name\": \"Meno\", \"password\": \"heslo\", \"lang\": \"en\"},"
        + "\"personal-details\": {\"address\": {\"street\":\"Ulica\", \"number\": 5, \"city\": \"Ranc\"}, \"phones\": [\"454545\", \"884455554\"]}"
        + "}";
        
        data = json_input;
        
        
        
        docFactory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Json2Xml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        doc = docBuilder.newDocument();
                
        jsonRoot =(JSONObject) JSONValue.parse(data);
        
        parse();
    } 
    
    public void parse(){
        Element rootElement = doc.createElement("curriculum-vitae");
        
        
        rootElement.appendChild(parseAutentication());
        rootElement.appendChild(parsePersonalDetails());
        
        
        doc.appendChild(rootElement);
        
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(Json2Xml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Element parsePersonalDetails(){
        Element elem = doc.createElement("personal-details");
        JSONObject json_pd = (JSONObject) jsonRoot.get("personal-details");
        
        //Names
        elem.appendChild(createElemWithText(json_pd, "given-names"));
        elem.appendChild(createElemWithText(json_pd, "surname"));
        
        //Adresses
        Element elem_address = doc.createElement("address");
        JSONObject json_address = (JSONObject) json_pd.get("address");
        
        String address_items[] = {"street", "number", "city", "post-code", "state", "country"};
        for (String address_item : address_items) {
            Element e = createElemWithText(json_address, address_item);
            if (e != null) elem_address.appendChild(e);
        }
        elem.appendChild(elem_address);
        
        //Phones
        elem.appendChild(createElemWithTextArray((JSONArray) json_pd.get("phones"), "phones", "phone"));
        
        //Emails
        elem.appendChild(createElemWithTextArray((JSONArray) json_pd.get("emails"), "emails", "email"));
        
        //Social
        elem.appendChild(createElemWithTextArray((JSONArray) json_pd.get("social"), "social", "site"));
        
        return elem;
    }
    
    private Element parseAutentication(){
        Element elem = doc.createElement("autentication");
        JSONObject json = (JSONObject) jsonRoot.get("autentication");
        
        elem.appendChild(createElemWithText(json, "log-in"));
        
        elem.appendChild(createElemWithText(json, "password"));
        
        elem.appendChild(createElemWithText(json, "lang"));
        
        return elem;
    }
    
    private Element createElemWithTextArray(JSONArray json_array, String category_name, String element_name){
        Element elem = doc.createElement(category_name);
        for (int i=0; i<json_array.size(); i++){
            Element e = doc.createElement(element_name);
            e.setTextContent(json_array.get(i).toString());
            elem.appendChild(e);
        }
        return elem;
    }
    
    private Element createElemWithText(JSONObject json, String name){
        return createElemWithText(json, name, name);
    }
    
    private Element createElemWithText(JSONObject json, String xmlName, String jsonName){
        Element elem = doc.createElement(xmlName);
        if (json.get(jsonName) == null) return null;
        elem.setTextContent(json.get(jsonName).toString());
        return elem;
    }
}

