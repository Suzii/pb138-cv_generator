/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zuzana
 */
public interface SessionService {
    /**
     * Creates a parameter containing login of currently logged in user of this session.
     * @param request request associated with this session
     * @param login value of login parameter to be set
     */
    void createSessionLogin(HttpServletRequest request, String login);

    /**
     * Returns the login of currently logged in user.
     * @param request request associated with the session
     * @return login of currently used user or null if no such exists
     */
    String getSessionLogin(HttpServletRequest request);

    /**
     * Deletes the attribute login in current session
     * @param request request associated with current session
     */
    void deleteSessionLogin(HttpServletRequest request);
    
}
