/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Zuzana
 */
public interface CvUtil {
    
    /**
     * Attaches provided file to the http response
     * @param response response to attach file to 
     * @param file file to be attached
     * @return true if file was attached, false otherwise
     * @throws IOException 
     */
    boolean attachFile(HttpServletResponse response, File file) throws IOException;
    
    /**
     * Extracts data from request
     * @param request request to extract data from
     * @return JSONObject containing extracted data
     */
    JSONObject extractUserData(HttpServletRequest request);
}
