package cz.muni.fi.pb138.cv.transformation;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;
import org.w3c.dom.Document;
import cz.muni.fi.pb138.cv.service.Config;

/**
 *
 * @author Peto
 */
public class TransformatorsTest {
    public static void main(String[] args) {
        
        TransformatorsTest.testXml2Json();
        
    }
    
    public static void testXml2Json(){
        String data;
        try {
            List<String> lines = Files.readAllLines(Paths.get(Config.SAMPLE_XML_PATH));
            data = String.join("", lines);
        } catch (IOException ex) {
            Logger.getLogger(Xml2JsonImpl.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        Xml2JsonImpl xl2 = new Xml2JsonImpl();
        JSONObject xmlJSONObj = xl2.transform(data);
            
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
        System.out.println(jsonPrettyPrintString);
    }
    
    public static void testJson2Xml(){
        String data;
        
        /*data = "{"
        + "\"autentication\": {\"name\": \"Meno\", \"password\": \"heslo\", \"lang\": \"en\"},"
        + "\"personal-details\": {\"address\": {\"street\":\"Ulica\", \"number\": 5, \"city\": \"Ranc\"}, \"phones\": [\"454545\", \"884455554\"]}"
        + "}";*/
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(Config.SAMPLE_JSON_PATH));
            data = String.join("", lines);
        } catch (IOException ex) {
            Logger.getLogger(Json2XmlImpl.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }        

        Json2XmlImpl p = new Json2XmlImpl();
        Document doc = p.transform(data);
        
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            
            writer.flush();
            
            System.out.println("XML IN String format is: \n" + writer.toString());
        } catch (TransformerException ex) {
            Logger.getLogger(Json2XmlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
