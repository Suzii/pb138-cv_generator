/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

import cz.muni.fi.pb138.cv.service.*;
import cz.muni.fi.pb138.cv.servlets.utils.Common;
import cz.muni.fi.pb138.cv.servlets.utils.SessionService;
import cz.muni.fi.pb138.cv.servlets.utils.CvUtil;
import java.io.File;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zuzana
 */
@WebServlet(Common.URL_PROFILE + "/*")
public class ProfileServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(ProfileServlet.class);

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
        //load json with user data associated with 'login'
        String login = getSessionService().getSessionLogin(request);
        if (login != null) {
            log.debug("PROFILE Logged user: " + login);
            JSONObject userData = getCvService().loadCvJSON(login);
            request.setAttribute("userData", userData);
            request.getRequestDispatcher(Common.PROFILE_JSP).forward(request, response);
        } else {//user is not logged in
            log.warn("Attempt for an anauthorized access.");
            response.sendRedirect(request.getContextPath() + Common.URL_LOGIN);
        }
        return;
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
        ResourceBundle bundle = ResourceBundle.getBundle("texts", request.getLocale());
        String action = request.getPathInfo();
        switch (action) {
            case "/download":
                // retrieve session and form info
                String login = getSessionService().getSessionLogin(request);
                String lang = request.getParameter("lang");
                if (lang == null || lang.length() == 0) {
                    lang = "en";
                }
                try { // attach pdf 
                    log.debug("PROFILE: requesting pdf generation for " + login + " in " + lang);
                    File file = getCvService().generatePdf(login, lang);
                    response.setContentType("application/octet-stream");
                    response.setContentLength((int) file.length());
                    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

                    if (!getCvUtil().attachFile(response.getOutputStream(), file)) {
                        request.setAttribute("error", "You have to create and save your CV first!");
                    }
                } catch (IOException e) { // display error
                    request.setAttribute("error", "Sorry, some error occured while generating your CV.");
                    log.error("Failed to generate cv for " + login, e);
                }
                request.getRequestDispatcher(Common.PROFILE_JSP).forward(request, response);
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action ");
                return;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Profile servlet of CV Generator app.";
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
