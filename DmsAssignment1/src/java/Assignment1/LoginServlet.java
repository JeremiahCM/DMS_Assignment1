/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment1;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Servlet which deals with a login request to the system.
 * @author Jeremiah Martinez: 18027693 | Sanjeel P Nath: 17987458
 * 
 * The files have used code learned from several stackoverflow threads, Telusko youtube channel and reused
 * from lab exercises, few bits of the following is adapted not copy and pasted, except for lab code we have used
 *
 * Grading Method: both equal
*/

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Retrieve values for username and password from the login request.
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        
        /*
           If username and password input matches sotred login details, set
            value to 'username' attribute for the session.
           Then take the user to the index server page.
        */
        if(user.equals("DmsStudent") && pass.equals("fpn871"))
        {
            HttpSession session = request.getSession();
            session.setAttribute("username", user);
            response.sendRedirect("index.jsp");
        }
        
        // Otherwise reload the login server page.
        else
        {
            response.sendRedirect("login.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
