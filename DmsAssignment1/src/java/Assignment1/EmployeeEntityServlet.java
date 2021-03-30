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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

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
   @Resource
   private UserTransaction userTrans;

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
       
       String firstName = request.getParameter("firstName");
       String lastName = request.getParameter("lastName");
       String job = request.getParameter("job");
       
       if(firstName == null || lastName == null || job == null || lastName.length() == 0 || firstName.length() == 0 || job.length() == 0 )
       {
           response.sendRedirect(request.getContextPath() + "");
       }
       else if(entityManager != null)
       {
           try {
               Employee dude = new Employee();
               dude.setFname(firstName);
               dude.setFname(lastName);
               dude.setEmpJob(job);
               try {
                   userTrans.begin();
               } catch (NotSupportedException ex) {
                   Logger.getLogger(EmployeeEntityServlet.class.getName()).log(Level.SEVERE, null, ex);
               }
               entityManager.persist(dude);
               userTrans.commit();
           }
           catch (RollbackException ex) {
               Logger.getLogger(EmployeeEntityServlet.class.getName()).log(Level.SEVERE, null, ex);
           } catch (HeuristicMixedException ex) {
               Logger.getLogger(EmployeeEntityServlet.class.getName()).log(Level.SEVERE, null, ex);
           } catch (HeuristicRollbackException ex) {
               Logger.getLogger(EmployeeEntityServlet.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SecurityException ex) {
               Logger.getLogger(EmployeeEntityServlet.class.getName()).log(Level.SEVERE, null, ex);
           } catch (IllegalStateException ex) {
               Logger.getLogger(EmployeeEntityServlet.class.getName()).log(Level.SEVERE, null, ex);
           } catch (SystemException ex) {
               Logger.getLogger(EmployeeEntityServlet.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
      response.setContentType("text/html;charset=UTF-8");
      try (PrintWriter out = response.getWriter())
      {
         out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Input response</title>");
         out.println("</head>");
         out.println("<body>");
         out.println("<h1>Pets Found for Species " + filter(firstName)
            + " "+filter(lastName)+ "</h1>");
         out.println("<p><a href=" + QUOTE
            + response.encodeURL("index.html") + QUOTE + ">"
            + "Return to Home page</a></p>");
         out.println("</body>");
         out.println("</html>");
      }
   }
   
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
