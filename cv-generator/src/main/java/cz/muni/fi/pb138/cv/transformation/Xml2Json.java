package cz.muni.fi.pb138.cv.transformation;

import org.w3c.dom.Document;
import org.json.JSONObject;
/**
 * Class for transformation of JSON object to XML Dom.
 * @author Zuzana
 */
public interface Xml2Json {
    
    /**
     * Transforms given DOM object to JSON.
     * @param dom DOM object to be transformed
     * @return JSON object containing transformed data of given DOM
     */
    JSONObject transform(Document dom);
    
    /**
     * Transforms given string containing XML to JSON.
     * @param dom DOM object to be transformed
     * @return JSON object containing transformed data of given DOM
     */
    JSONObject transform(String dom);
}