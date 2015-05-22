package cz.muni.fi.pb138.cv.transformation;

import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import org.w3c.dom.Document;


/**
 *
 * @author Peto
 */
public class Xml2JsonImpl implements Xml2Json{

    @Override
    public JSONObject transform(Document dom){
        try{
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(dom);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            
            writer.flush();
            
            return transform(writer.toString());
        } catch (TransformerException ex) {
            Logger.getLogger(Json2XmlImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public JSONObject transform(String data){
        JSONObject json;
        try {
            json = XML.toJSONObject(data);
        } catch (JSONException je) {
            System.out.println(je);
            return null;
        }
        
        JSONObject jsonCV = json.getJSONObject("curriculum-vitae");
        
        /*jsonCV.remove("xmlns:xsi");
        jsonCV.remove("xsi:noNamespaceSchemaLocation");*/
                
        JSONObject jsonPD = jsonCV.getJSONObject("personal-details");

        
        checkArray(jsonCV, "driving-licence", "class");
        checkArray(jsonCV, "employment", "emp");
        checkArray(jsonCV, "computer-skills", "skill");
        checkArray(jsonCV, "education", "edu");
        checkArray(jsonCV, "language-skills", "lang");
        checkArray(jsonCV, "certificates", "cert");
        
        
        transformArray(jsonPD, "phones", "phone");
        transformArray(jsonPD, "social", "site");
        transformArray(jsonPD, "emails", "email");
        
        return json;
    }

    
    private void checkArray(JSONObject root, String parent, String name){
        Object parentObject = root.get(parent);
        //empty
        if (!(parentObject instanceof JSONObject)) {
            root.put(parent, new JSONArray());
            return;
        }
        
        Object obj = root.getJSONObject(parent).get(name);
        root.remove(parent);
        
        if (obj instanceof JSONArray) //array
            root.put(parent, obj);
        else
            root.append(parent, obj); //one object
    }
    
    private void transformArray(JSONObject jRoot, String parent, String name){
        Object parentObject = jRoot.get(parent);
        if (!(parentObject instanceof JSONObject)) {
            jRoot.put(parent, new JSONArray());
            return;
        }
        
        Object obj = jRoot.getJSONObject(parent).get(name);
        jRoot.remove(parent);

        JSONArray jArray;
        if (obj instanceof JSONArray){
            jArray = (JSONArray) obj;
        }else{
            jArray = new JSONArray();
            jArray.put(obj);
        }
        
        JSONArray phones_new = new JSONArray();
        for (int i=0; i<jArray.length(); i++){
            JSONObject o = new JSONObject();
            o.put("value", jArray.get(i).toString());
            phones_new.put(o);
        }
        jRoot.put(parent, phones_new);
    }
}
