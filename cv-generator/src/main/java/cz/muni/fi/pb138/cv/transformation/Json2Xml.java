/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.transformation;

import org.w3c.dom.Document;
import org.json.simple.JSONObject;
/**
 * Class for transformation of JSON object to XML Dom.
 * @author Zuzana
 */
public interface Json2Xml {
    
    /**
     * Transforms given JSON object to DOM.
     * @param json JSON object to be transformed
     * @return document containing transformed data of given JSON
     */
    Document transform(JSONObject json);
    
    
    /**
     * Transforms given JSON string to DOM.
     * @param json JSON object to be transformed
     * @return document containing transformed data of given JSON string
     */
    Document transform(String json);
}
