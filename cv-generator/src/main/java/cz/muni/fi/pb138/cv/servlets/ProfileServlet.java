/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

import cz.muni.fi.pb138.cv.service.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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

    public static UserService userService = new UserServiceImpl();
    public static CvService cvService = new CvServiceImpl();
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
        String action = request.getPathInfo();
        if (action == null) {
            //load json with user data associated with 'login'
            String login = SessionService.getSessionLogin(request);
            if (login != null) {
                log.debug("PROFILE Logged user: " + login);
                JSONObject userData = cvService.loadCvJSON(login);
                request.setAttribute("userData", userData);

                System.out.println("User: " + login);
                System.out.println("Data: " + userData);

                request.getRequestDispatcher(Common.PROFILE_JSP).forward(request, response);
            } else {
                //user is not logged in
                request.setAttribute("error", "You are not logged in.");
                log.warn("Attempt for an anauthorized access.");
                response.sendRedirect(request.getContextPath() + Common.URL_LOGIN);
            }
        }
        switch (action) {
            case "/download":
                String login = SessionService.getSessionLogin(request);
                try {
                    String lang = request.getParameter("lang");
                    if(lang == null || lang.length() == 0){
                        lang = "en";
                    }
                    attachFile(response, login, lang);
                } catch (IOException e) {
                    request.setAttribute("error", "Sorry, some error occured while generating your CV.");
                    log.error("Failed to generate cv for " + login, e);
                    request.getRequestDispatcher(Common.PROFILE_JSP).forward(request, response);
                    return;
                }
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
        return "Profile servlet of CV Generator app.";
    }

    private void attachFile(HttpServletResponse response, String login, String lang) throws IOException {
        File file = cvService.generatePdf(login, lang);
        if(file == null){
            return;
        }

        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

        OutputStream out = response.getOutputStream();
        try (FileInputStream in = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
        out.flush();
    }
}
