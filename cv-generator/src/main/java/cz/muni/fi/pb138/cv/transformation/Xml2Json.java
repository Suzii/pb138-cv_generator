package cz.muni.fi.pb138.cv.transformation;

import org.w3c.dom.Document;
import org.json.simple.JSONObject;
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
    
    //este nevieme ako bude fungovat DB uvidime ci bude nutne
    /**
     * Transforms given string containing XML to JSON.
     * @param dom DOM object to be transformed
     * @return JSON object containing transformed data of given DOM
     */
    //JSONObject transform(String dom);
}