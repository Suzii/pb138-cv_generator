/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.servlets;

import cz.muni.fi.pb138.cv.service.MockedUserServiceImpl;
import cz.muni.fi.pb138.cv.service.UserService;

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

    public static UserService userService = new MockedUserServiceImpl();
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
        request.getRequestDispatcher(Common.SIGNUP_JSP).forward(request, response);
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
                String login = request.getParameter("login");
                String passwordHash = request.getParameter("password");
                Object data = "";
                //find out if user exists
                if (userService.checkIfExists(login)) {
                    data = "Username: " + login + " already taken.";
                    request.setAttribute("error", data);
                    response.sendRedirect(request.getContextPath() + Common.URL_EDIT);
                    //check password
                } else if (!userService.registerNewUser(login, passwordHash)) {
                    data = "Error while creating account.";
                    request.setAttribute("error", data);
                    request.getRequestDispatcher(Common.SIGNUP_JSP).forward(request, response);

                } else {
                    SessionService.createSessionLogin(request, login);
                    response.sendRedirect(request.getContextPath() + Common.URL_EDIT);
                }
                return;

            default:
                log.error("Unknown action " + action);
                request.getRequestDispatcher(Common._404_JSP).forward(request, response);
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
}
