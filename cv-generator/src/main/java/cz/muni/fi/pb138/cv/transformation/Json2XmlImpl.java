package cz.muni.fi.pb138.cv.transformation;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.json.JSONObject;
import org.json.JSONArray;


/**
 *
 * @author Peto
 */
public class Json2XmlImpl implements Json2Xml {

    private Document doc;
    private JSONObject jsonRoot;
    
    private static final String XML_SCHEME = "cv.xsd";
    private static final String XML_NS = "http://www.w3.org/2001/XMLSchema-instance";
    

    @Override
    public Document transform(String data){
        return transform(new JSONObject(data));
    }
    
    @Override
    public Document transform(JSONObject jsonRoot){
        this.jsonRoot = jsonRoot.getJSONObject("curriculum-vitae");
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Json2XmlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        doc = docBuilder.newDocument();
        
        parse();
        
        return doc;
    }
    
    private void parse(){
        Element rootElement = doc.createElement("curriculum-vitae");
        /*rootElement.setAttribute("xmlns:xsi", XML_NS);
        rootElement.setAttribute("xsi:noNamespaceSchemaLocation", XML_SCHEME);*/
        
        rootElement.appendChild(parsePersonalDetails());
        rootElement.appendChild(createCustomElems("education", "edu", new String[] {"from", "to"}, new String[] {"name-of-education", "name-of-school", "note"}));
        rootElement.appendChild(createCustomElems("employment", "emp", new String[] {"from", "to"}, new String[] {"company", "position", "note"}));
        rootElement.appendChild(createCustomElems("language-skills", "lang", new String[] {"name"}, new String[] {"level", "note"}));
        rootElement.appendChild(createCustomElems("computer-skills", "skill", null, new String[] {"name", "note"}));
        rootElement.appendChild(createCustomElems("certificates", "cert", new String[] {"year"}, new String[] {"name", "note"}));
        rootElement.appendChild(createCustomElems("driving-licence", "class", null, new String[] {"name", "note"}));
        parseOthers(rootElement);
        
        doc.appendChild(rootElement);
    }
    
    private Element parsePersonalDetails(){
        Element elem = doc.createElement("personal-details");
        JSONObject json_pd = (JSONObject) jsonRoot.get("personal-details");
        
        //Names
        elem.appendChild(createElemWithText(json_pd, "given-names"));
        elem.appendChild(createElemWithText(json_pd, "surname"));
        
        //date of Birth
        Element elem_dateOfBirth = doc.createElement("date-of-birth");
        JSONObject json_dateOfBirth = (JSONObject) json_pd.get("date-of-birth");
        
        String dob_items[] = {"day", "month", "year"};
        for (String item : dob_items) {
            Element e = createElemWithText(json_dateOfBirth, item);
            if (e != null) elem_dateOfBirth.appendChild(e);
        }
        elem.appendChild(elem_dateOfBirth);
        
        //Adresses
        Element elem_address = doc.createElement("address");
        JSONObject json_address = (JSONObject) json_pd.get("address");
        
        String address_items[] = {"street", "number", "city", "postal-code", "state", "country"};
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
    
    private void parseOthers(Element root){
        root.appendChild(createElemWithText(jsonRoot, "characteristic"));
        root.appendChild(createElemWithText(jsonRoot, "hobbies"));
    }

    //
    
    private Element createCustomElems(String main_elem_name, String child_elem_name, String[] attributes, String[] elem_names){
        JSONArray json_array = (JSONArray)jsonRoot.get(main_elem_name);
        Element elem = doc.createElement(main_elem_name);
        
        for(int i=0; i<json_array.length(); i++){
            JSONObject j = (JSONObject) json_array.get(i);
            
            Element e = doc.createElement(child_elem_name);
            
            if (attributes != null){
                for(String a : attributes){
                    e.setAttribute(a, j.get(a) != null ? j.get(a).toString() : "");
                }
            }
            
            appendElemsFromArray(j, e, elem_names);
            elem.appendChild(e);
        }
        
        return elem;
    }
   
    private void appendElemsFromArray(JSONObject json, Element root, String[] elem_names){
        for (String elem_name : elem_names){
            root.appendChild(createElemWithText(elem_name, json.get(elem_name).toString()));
        }
    }
    
    private Element createElemWithTextArray(JSONArray json_array, String category_name, String element_name){
        Element elem = doc.createElement(category_name);
        for (int i=0; i<json_array.length(); i++){
            Element e = doc.createElement(element_name);
            e.setTextContent(json_array.getJSONObject(i).get("value").toString());
            elem.appendChild(e);
        }
        return elem;
    }
    
    private Element createElemWithText(String xmlName, String value){
        Element elem = doc.createElement(xmlName);
        elem.setTextContent(value);
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

