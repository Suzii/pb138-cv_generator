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
        
        //TransformatorsTest.testXml2Json();
        
        TransformatorsTest.testJson2Xml();
        
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
        
        data = "{\"curriculum-vitae\":{\"computer-skills\":[{\"note\":\"I can use it...\",\"name\":\"Windows\"},{\"note\":\"I can open my browser window...\",\"name\":\"HTML\"}],\"education\":[{\"note\":\"What did I learn there.\",\"name-of-education\":\"Primary school\",\"from\":2004,\"to\":2008,\"name-of-school\":\"Primary school NY\"},{\"note\":\"What did I learn there. I really have no idea.\",\"name-of-education\":\"High school\",\"from\":2008,\"to\":2010,\"name-of-school\":\"Primary school New York City\"},{\"note\":\"...\",\"name-of-education\":\"University\",\"from\":2010,\"to\":null,\"name-of-school\":\"University of Queensland\"}],\"language-skills\":[{\"note\":\"\",\"level\":\"A2\",\"name\":\"en\"}],\"certificates\":[{\"note\":\"I made my first cake!\",\"year\":2000,\"name\":\"Baking with mum\"}],\"hobbies\":\"I ride ponnies and bike.. I hate football...\",\"personal-details\":{\"given-names\":\"Anca Maria\",\"emails\":[{\"value\":\"anca@zranca.com\"},{\"value\":\"anicka.z.ranca@gmail.com\"}],\"address\":{\"number\":42,\"country\":\"USA\",\"postal-code\":123456,\"city\":\"Ranc za Humnom\",\"street\":\"Rancova\",\"state\":\"Michigan\"},\"social\":[{\"value\":\"linkedin.com/anicka\"},{\"value\":\"www.anicka-z-ranca.com\"}],\"surname\":\"z Ranca\",\"date-of-birth\":{\"month\":\"04\",\"year\":1994,\"day\":\"01\"},\"phones\":[{\"value\":\"0905123123\"},{\"value\":\"0907123456\"}]},\"driving-licence\":[{\"note\":\"I drove my uncles truck...\",\"name\":\"B\"}],\"employment\":[{\"note\":\"I was cleaning floors all the time...\",\"from\":2014,\"company\":\"Looser, Inc.\",\"to\":2015,\"position\":\"cleaner\"},{\"note\":\"I am making coffee and I like it!\",\"from\":2015,\"company\":\"Starbucks, Inc.\",\"to\":null,\"position\":\"waitress\"}],\"characteristic\":\"I am a tall person with warm heart...\"}}";
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(Config.SAMPLE_JSON_PATH));
            //data = String.join("", lines);
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
