/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Zuzana
 */
public interface CvUtil {
    
    /**
     * Attaches provided file to given output stream
     * @param out output stream to attach file to 
     * @param file file to be attached
     * @return true if file was attached, false otherwise
     * @throws IOException 
     */
    boolean attachFile(OutputStream out, File file) throws IOException;
    
    /**
     * Extracts data from given buffered reader
     * @param reader reader to extract data from
     * @return JSONObject containing extracted data
     * @throws JSONException on parse error
     */
    JSONObject extractUserData(BufferedReader reader) throws JSONException;
}
