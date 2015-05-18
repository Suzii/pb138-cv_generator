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
public class SessionServiceImpl implements SessionService{
    
    @Override
    public void createSessionLogin(HttpServletRequest request, String login) {
        HttpSession session = request.getSession(true);
        if (session.isNew() == false) {
            session.invalidate();
            session = request.getSession(true);
        }
        session.setAttribute("login", login);
    }

    @Override
    public String getSessionLogin(HttpServletRequest request) {
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

    @Override
    public void deleteSessionLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (session != null) {
            session.removeAttribute("login");
        }
    }   
}
