/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

import cz.muni.fi.pb138.cv.servlets.utils.*;
import cz.muni.fi.pb138.cv.service.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

/**
 *
 * @author Zuzana
 */
@WebServlet(Common.URL_EDIT + "/*")
public class EditServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(EditServlet.class);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        String action = request.getPathInfo();
        if (action == null) {
            //load json object with user data associated with 'login'
            String login = getSessionService().getSessionLogin(request);
            if (login != null) {
                log.debug("EDIT Logged user: " + login);
                JSONObject userData = getCvService().loadCvJSON(login);
                request.setAttribute("userData", userData);
                request.getRequestDispatcher(Common.EDIT_JSP).forward(request, response);
            } else { //user is not logged in
                log.warn("Attempt for an anauthorized access.");
                response.sendRedirect(request.getContextPath() + Common.URL_LOGIN);
            }
            return;
        }
        switch (action) {
            case "/logout":
                getSessionService().deleteSessionLogin(request);
                response.sendRedirect(request.getContextPath() + Common.URL_LOGIN);
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
                return;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        response.setContentType("application/json");
        ResourceBundle bundle = ResourceBundle.getBundle("texts", request.getLocale());
        String action = request.getPathInfo();
        switch (action) {
            case "/save":
                response.setStatus(HttpServletResponse.SC_OK);
                String login = getSessionService().getSessionLogin(request);
                log.debug("EDIT - save: This is recieved JSON for " + login + ": ");

                //this is JSON object with user data
                JSONObject userData;
                try {
                    userData = getCvUtil().extractUserData(request.getReader());
                } catch (Exception ex) {
                    response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
                    setMessage(response, "Some error occured while parsing your CV.");
                    return;
                }
                log.debug(userData.toString());

                //run XML schema
                log.debug("Running validity check of CV for " + login);
                String message = getCvService().checkValidity(userData);
                if (message != null) {
                    log.debug(message);
                    //TODO if not ok, display error, forward to edit.jsp
                    setMessage(response, "Could not generate valid XML. " + message);
                    return;
                }

                log.debug("Trying to store CV for " + login);
                //if ok, store xml to DB
                if (getCvService().saveCv(login, userData)) {
                    log.debug("CV for " + login + " saved");

                    setMessage(response, "CV saved. For downloading the PDF go to your profile page.");
                } else {
                    log.error("Error while storing CV for " + login + ".");
                    setMessage(response, "Unexpected error occured while storing your CV to database. Try again later.");
                }
                return;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
    }

    private void setMessage(HttpServletResponse response, String message) throws IOException {
        JSONObject json = new JSONObject();
        json.put("msg", message);
        response.getWriter().write(json.toString());
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Edit servlet of CV Generator app.";
    }

    private CvService getCvService() {
        return (CvService) getServletContext().getAttribute("cvService");
    }

    private CvUtil getCvUtil() {
        return (CvUtil) getServletContext().getAttribute("cvUtil");
    }

    private SessionService getSessionService() {
        return (SessionService) getServletContext().getAttribute("sessionService");
    }
}
