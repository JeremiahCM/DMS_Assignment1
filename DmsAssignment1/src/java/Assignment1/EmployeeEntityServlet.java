/*
 * Servlet which demonstrates accessing a database via JPA and Entities
 * Note must create a connection pool and a JDBC Resource for a
 * DataSource called "jdbc/MySQLAnimalResource" on application server
 * and have persistence unit called "animalPersistenceUnit"
 * in the configuration file persistence.xml
 * @author Andrew Ensor
 */
package Assignment1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "EmployeeEntityServlet", urlPatterns =
{
   "/EmployeeEntityServlet"
})
public class EmployeeEntityServlet extends HttpServlet
{

   private final char QUOTE = '"';
   private Logger logger;
   @PersistenceContext
   private EntityManager entityManager;

   /**
    * Creates a new instance of EmployeeServlet
    */
   public EmployeeEntityServlet()
   {
      logger = Logger.getLogger(getClass().getName());
   }

   protected void processRequest(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException
   {
      String e_id = request.getParameter("e_id");
      if (e_id == null || e_id.length() == 0)
         e_id = "%";
      // query database using JPA and the Employee entity
      List<Employee> employeeList = null;
      if (entityManager != null)
      {
         String jpqlCommand
            = "SELECT e FROM Employee e WHERE e.employeeID LIKE :e_id";
         Query query
            = entityManager.createQuery(jpqlCommand);
         query.setParameter("e_id", e_id);
         employeeList = query.getResultList();
         logger.info("Successfully executed jpql query for employee "
            + e_id);
      }
      // set response headers before returning any message content
      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter())
      {
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Pet Entity Servlet Response</title>");
         out.println("</head>");
         out.println("<body>");
         out.println("<h1>Employee Found for ID " + filter(e_id)
            + "</h1>");
         if (employeeList != null)
         {
            out.println("<table cellspacing=1 border=5>");
            out.println("<tr><td><b>Pet Name</b></td>"
               + "<td><b>Pet Owner</b></td></tr>");
            for (Employee pet : employeeList)
            {
               String first_name = pet.getFirstName();
               String last_name = pet.getLastName();
               out.println("<tr><td>" + filter(first_name) + "</td><td>"
                  + filter(last_name) + "</td></tr>");
            }
            out.println("</table>");
         }
         out.println("<p><a href=" + QUOTE
            + response.encodeURL("index.html") + QUOTE + ">"
            + "Return to Pet Information Page</a></p>");
         out.println("</body>");
         out.println("</html>");
      }
   }

   // filter string so that it doesn't contain special HTML characters
   public static String filter(String text)
   {
      StringBuilder buffer = new StringBuilder();
      for (int i = 0; i < text.length(); i++)
      {
         char c = text.charAt(i);
         if (c == '<')
            buffer.append("&lt;");
         else if (c == '>')
            buffer.append("&gt;");
         else if (c == '\"')
            buffer.append("&quot;");
         else if (c == '\'')
            buffer.append("&#39;");
         else if (c == '&')
            buffer.append("&amp;");
         else
            buffer.append(c);
      }
      return buffer.toString();
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
      throws ServletException, IOException
   {
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
      throws ServletException, IOException
   {
      processRequest(request, response);
   }

   /**
    * Returns a short description of the servlet.
    *
    * @return a String containing servlet description
    */
   @Override
   public String getServletInfo()
   {
      return "Short description";
   }// </editor-fold>

}
