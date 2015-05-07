/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

import cz.muni.fi.pb138.cv.service.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.HTTP;
//import org.json.JSONObject;
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
                System.out.println("Logged user: " + login);
                JSONObject userData = cvService.loadCvJSON(login);

                request.setAttribute("userData", userData);
                System.out.println("User: " + login);
                System.out.println("Data: " + userData);

                request.getRequestDispatcher(Common.EDIT_JSP).forward(request, response);
            } else {
                //user is not logged in
                request.setAttribute("error", "You are not logged in.");
                response.sendRedirect(request.getContextPath() + Common.URL_LOGIN);
            }
            return;
        }
        switch (action) {
            case "/logout":
                SessionService.deleteSessionLogin(request);
                response.sendRedirect(request.getContextPath() + Common.URL_LOGIN);
                return;
            case "/profile":
                response.sendRedirect(request.getContextPath() + Common.URL_PROFILE);
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action ");
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
                System.out.println("This is recieved JSON:");

                //this is JSON object with user data
                JSONObject userData = extractUserData(request);
                System.out.println(userData);

                //run XML schema
                /*System.out.println("Running validity check...");
                String message = cvService.checkValidity(userData);
                if (message != null) {
                    System.out.println("Could not generate valid XML... Fail...");
                    //TODO if not ok, display error, forward to edit.jsp
                    request.setAttribute("msg", message);
                    request.getRequestDispatcher(Common.EDIT_JSP).forward(request, response);
                    return;
                }
                */
                System.out.println("Trying to store CV...");
                //if ok, store xml to DB, redirect to /profile
                if (cvService.saveCv(SessionService.getSessionLogin(request), userData)) {
                    System.out.println("CV saved");
                    request.setAttribute("msg", "CV saved.");
                    request.getRequestDispatcher(Common.EDIT_JSP).forward(request, response);

                } else {
                    System.out.println("Error while storing CV. Fail.");
                    request.setAttribute("error", "Unexpected error occured while storing your CV to database.");
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
        return "Short description";
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

        /*JSONParser parser = new JSONParser();
         JSONObject userData = null;
         try {
         userData = (JSONObject) parser.parse(sb.toString());
         return userData;
         } catch (ParseException e) {
         e.printStackTrace();
         }*/
        return new JSONObject(sb.toString());
    }
}
