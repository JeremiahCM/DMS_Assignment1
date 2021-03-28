/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment1;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
/**
 *
 * @author churr
 */

@WebServlet(name = "EmployeeServlet", urlPatterns = 
{
    "/EmployeeServlet"
}, initParams = 
{
    @WebInitParam(name = "dbTable", value = "e_id")
   ,@WebInitParam(name = "dblast_nameAtt", value = "last_name")
   ,@WebInitParam(name = "dbfirst_nameAtt", value = "first_name")
   ,@WebInitParam(name = "db_job_Att", value = "job")     
})

public class EmployeeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final char QUOTE = '"';
    private Logger logger;
    private String sqlCommand;
    
    @Resource(mappedName = "jdbc/MySQLEmployeeRes")
    private DataSource dataSauce;
    
    public EmployeeServlet()
    {
        logger = Logger.getLogger(getClass().getName());
    }
    
   @Override
   public void init()
   {
      // obtain servlet configuration values from annotation or web.xml
      ServletConfig config = getServletConfig();
      String dbTable = config.getInitParameter("dbTable");
      String dblast_nameAtt = config.getInitParameter("dblast_nameAtt");
      String dbfirst_nameAtt = config.getInitParameter("dbfirst_nameAtt");
      String db_job_Att = config.getInitParameter("db_job_Att");
      sqlCommand = "SELECT " + dbfirst_nameAtt + " AS NAME," + dblast_nameAtt
         + " AS OWNER FROM " + dbTable + " WHERE " + db_job_Att
         + " LIKE ?";
   }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*insert entity code here for redirection
        
        
        
        */
      String e_id = request.getParameter("e_id");
      if (e_id== null || e_id.length() == 0)
         e_id = "%";
      // query database
      Connection conn = null;
      PreparedStatement prepStmt = null;
      ResultSet resultSet = null;
      if (sqlCommand != null && dataSauce != null)
      {
         try
         {
            conn = dataSauce.getConnection();
            prepStmt = conn.prepareStatement(sqlCommand);
            prepStmt.setString(1, e_id);
            resultSet = prepStmt.executeQuery();
            logger.info("Successfully executed query for e_id "
               + e_id);
         }
         catch (SQLException e)
         {
            logger.severe("Unable to execute query for e_id "
               + e_id + ": " + e);
         }
      }
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
         out.println("<html>");
         out.println("<head>");
         out.println("<title>PetServlet Response</title>");
         out.println("</head>");
         out.println("<body>");
         out.println("<h1>ID found here it is " + filter(e_id)
            + "</h1>");
         if (resultSet != null)
         {
            out.println("<TABLE cellspacing=1 border=5>");
            out.println("<TR><TD><B>First Name</B></TD>"
               + "<TD><B>Last Name</B></TD></TR>");
            try
            {
               while (resultSet.next())
               {
                  String fname = resultSet.getString("FIRST_NAME");
                  String lname = resultSet.getString("LAST_NAME");
                  out.println("<TR><TD>" + filter(fname) + "</TD><TD>"
                     + filter(lname) + "</TD></TR>");
               }
            }
            catch (SQLException e)
            {
               logger.severe("Exception in results "
                  + e_id + ": " + e);
            }
            out.println("</TABLE>");
         }
         try
         {
            if (prepStmt != null)
               prepStmt.close();
            if (conn != null)
               conn.close(); // release conn back to pool
         }
         catch (SQLException e)
         {  // ignore
         }
         out.println("<P><A href=" + QUOTE
            + response.encodeURL("index.html") + QUOTE + ">"
            + "Return to the search page</A></P>");
         out.println("</body>");
         out.println("</html>");
        out.println("<P><A href=" + QUOTE
            + response.encodeURL("index.html") + QUOTE + ">"
            + "View the jobs</A></P>");
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
