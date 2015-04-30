/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cv.generator;

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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Zuzana
 */
@WebServlet(EditServlet.URL_MAPPING + "/*")
public class EditServlet extends HttpServlet {

    public static final String URL_MAPPING = "/edit";
    public static final String URL_LOGIN = "/login";
    public static final String EDIT_JSP = "/edit.jsp";
    //musi to byt na takejto absolutnej ceste, lebo inak by sa k tomu nedalo pristupit normalne (servlet bezi v inom prieciku)
    public static final String SAMPLE_JSON_PATH = "C:/sample-data.json";

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
        HttpSession session = request.getSession(true);
        String login = (String) session.getAttribute("login");
        if (login != null && login.length() != 0) {
            //load json with user data associated with 'login'
            Object userData = loadUserData(login);
            request.setAttribute("userData", userData);
            System.out.println("User: " + login);
            System.out.println("Data: " + userData);
        } else 
        {
            //no user logged in
            request.setAttribute("userData", null);
        }
        request.getRequestDispatcher(EDIT_JSP).forward(request, response);
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
                //request.setAttribute("data", "Cool you reached the server!");
                System.out.println("This is recieved JSON:");

                //this is JSON object with user data
                JSONObject userData = extractUserData(request);
                System.out.println(userData);

                //TODO create XML from JSON
                //TODO run XML schema
                //TODO if ok, store xml to DB, redirect to /profile
                //TODO else, display error, forward to edit.jsp
                return;
            case "/logout":
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute("login");
                }
                response.sendRedirect(request.getContextPath() + URL_LOGIN);
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

        JSONParser parser = new JSONParser();
        JSONObject userData = null;
        try {
            userData = (JSONObject) parser.parse(sb.toString());
            return userData;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private JSONObject loadUserData(String login) {
        //TODO find appropriate file associated with login
        JSONParser parser = new JSONParser();

        try {
            File file = new File(SAMPLE_JSON_PATH);
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
