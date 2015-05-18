/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

import cz.muni.fi.pb138.cv.servlets.utils.SessionService;
import cz.muni.fi.pb138.cv.servlets.utils.Common;
import cz.muni.fi.pb138.cv.servlets.utils.LoginsUtil;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zuzana
 */
@WebServlet(Common.URL_SIGNUP + "/*")
public class SignUpServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(SignUpServlet.class);

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
            request.getRequestDispatcher(Common.SIGNUP_JSP).forward(request, response);
            return;
        }
        switch (action) {
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

            case "/submit":
                //retrieve post data
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                String password2 = request.getParameter("password2");
                log.debug("SIGNUP: Username: " + login + " Passwd: " + password);
                
                // try to create an account
                String error = getLoginsUtil().tryToCreateAnAccount(login, password, password2);
                if(error == null) { // everything ok
                    SessionService.createSessionLogin(request, login);
                    response.sendRedirect(request.getContextPath() + Common.URL_EDIT);
                    return;
                }
                request.setAttribute("error", error); // display error
                request.getRequestDispatcher(Common.SIGNUP_JSP).forward(request, response);
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
        return "Signup servlet of CV Generator app.";
    }    
    
    private LoginsUtil getLoginsUtil() {
        return (LoginsUtil) getServletContext().getAttribute("loginsUtil");
    }
}
