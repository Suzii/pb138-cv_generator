/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

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

    public static CvService cvService = new CvServiceImpl();
    public static UserService userService = new UserServiceImpl();
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
            //load json with user data associated with 'login'
            String login = SessionService.getSessionLogin(request);
            if (login != null) {
                log.debug("EDIT Logged user: " + login);
                JSONObject userData = cvService.loadCvJSON(login);
                request.setAttribute("userData", userData);

                System.out.println("User: " + login);
                System.out.println("Data: " + userData);

                request.getRequestDispatcher(Common.EDIT_JSP).forward(request, response);
            } else {
                //user is not logged in
                request.setAttribute("error", "You are not logged in.");
                log.warn("Attempt for an anauthorized access.");
                response.sendRedirect(request.getContextPath() + Common.URL_LOGIN);
            }
            return;
        }
        switch (action) {
            case "/logout":
                SessionService.deleteSessionLogin(request);
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
        ResourceBundle bundle = ResourceBundle.getBundle("texts", request.getLocale());
        String action = request.getPathInfo();
        switch (action) {
            case "/save":
                response.setStatus(HttpServletResponse.SC_OK);
                String login = SessionService.getSessionLogin(request);
                log.debug("EIDT - save: This is recieved JSON for " + login + ": ");

                //this is JSON object with user data
                JSONObject userData = extractUserData(request);
                log.debug(userData.toString());

                //run XML schema
                /*log.debug("Running validity check of CV for " + login);
                String message = cvService.checkValidity(userData);
                if (message != null) {
                    log.debug(message);
                    message += "Could not generate valid XML... Chceck if all date is filled in correctly, mainly if all years are OK...";
                    //TODO if not ok, display error, forward to edit.jsp
                    request.setAttribute("msg", message);
                    request.getRequestDispatcher(Common.EDIT_JSP).forward(request, response);
                    return;
                }
                */
                log.debug("Trying to store CV for " + login);
                //if ok, store xml to DB, redirect to /profile
                if (cvService.saveCv(login, userData)) {
                    System.out.println("CV for " + login + " saved");
                    request.setAttribute("msg", "CV saved. For downloading the PDF go to your profile page.");
                    request.getRequestDispatcher(Common.EDIT_JSP).forward(request, response);
                } else {
                    log.error("Error while storing CV for " + login + ".");
                    request.setAttribute("error", "Unexpected error occured while storing your CV to database. Try again later.");
                    request.getRequestDispatcher(Common.EDIT_JSP).forward(request, response);
                }
                return;
            default:
                log.error("Unknown action " + action);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action " + action);
        }
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

    private JSONObject extractUserData(HttpServletRequest request) {

        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader reader = request.getReader();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject(sb.toString());
    }
}
