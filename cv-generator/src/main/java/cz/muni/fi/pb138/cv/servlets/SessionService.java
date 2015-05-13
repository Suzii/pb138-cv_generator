/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zuzana
 */
public class SessionService {
    /**
     * Creates a parameter containing login of currently logged in user of this session.
     * @param request request associated with this session
     * @param login value of login parameter to be set
     */
    public static void createSessionLogin(HttpServletRequest request, String login) {
        HttpSession session = request.getSession(true);
        if (session.isNew() == false) {
            session.invalidate();
            session = request.getSession(true);
        }
        session.setAttribute("login", login);
    }

    /**
     * Returns the login of currently logged in user.
     * @param request request associated with the session
     * @return login of currently used user or null if no such exists
     */
    public static String getSessionLogin(HttpServletRequest request) {
        String login = null;
        if (request == null) {
            return null;
        }
        HttpSession session = request.getSession(false);
        if (session != null) {
            login = (String) session.getAttribute("login");
        }
        return login;
    }

    /**
     * Deletes the attribute login in current session
     * @param request request associated with current session
     */
    public static void deleteSessionLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session != null) {
            session.removeAttribute("login");
        }
    }
    
}