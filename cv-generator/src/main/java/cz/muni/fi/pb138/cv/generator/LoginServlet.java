/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zuzana
 */
@WebServlet(LoginServlet.URL_MAPPING + "/*")
public class LoginServlet extends HttpServlet {

    public static final String URL_MAPPING = "/login";
    public static final String URL_EDIT = "/edit";
    public static final String LOGIN_JSP = "/login.jsp";

    private final static Logger log = LoggerFactory.getLogger(LoginServlet.class);

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
        request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
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
            case "/start":
                String name = request.getParameter("name");
                if (!name.equals("x")) {
                    Object data = "No such user exists.";
                    request.setAttribute("data", data);
                    request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + URL_EDIT);
                }
                return;

            case "/submit":
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                Object data = "";
                //TODO find out if user exists
                if (!login.equals("x")) {
                    data = "Username: " + login + " does not exists";
                    request.setAttribute("error", data);
                    request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
                    //TODOcheck password
                } else if (!password.equals("x")) {
                    data = "Wrong password.";
                    request.setAttribute("error", data);
                    request.getRequestDispatcher(LOGIN_JSP).forward(request, response);

                } else {
                    HttpSession session = request.getSession(true);
                    if (session.isNew() == false) {
                        session.invalidate();
                        session = request.getSession(true);
                    }
                    session.setAttribute("login", login);
                    response.sendRedirect(request.getContextPath() + URL_EDIT);
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
}
