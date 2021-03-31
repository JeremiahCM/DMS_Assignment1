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
import java.util.logging.Level;
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
import javax.servlet.http.HttpSession;
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
    @WebInitParam(name = "dbTable", value = "dgn1399_employees")
   ,@WebInitParam(name = "dbe_idAtt", value = "e_id")
   ,@WebInitParam(name = "dblast_nameAtt", value = "last_name")
   ,@WebInitParam(name = "dbfirst_nameAtt", value = "first_name")
   ,@WebInitParam(name = "dbjob_Att", value = "job")
   ,@WebInitParam(name = "dbTable2", value = "dgn1399_tasks")
   ,@WebInitParam(name = "dbTable3", value = "dgn1399_employee_tasks")
   ,@WebInitParam(name = "dbt_idAtt", value = "t_id")
   ,@WebInitParam(name = "dbtask_nameAtt", value = "task_name")
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
    private String sqlCommand2;
    
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
      String dbe_idAtt = config.getInitParameter("dbe_idAtt");
      String dblast_nameAtt = config.getInitParameter("dblast_nameAtt");
      String dbfirst_nameAtt = config.getInitParameter("dbfirst_nameAtt");
      String dbjob_Att = config.getInitParameter("dbjob_Att");
      String dbTable2 = config.getInitParameter("dbTable2");
      String dbTable3 = config.getInitParameter("dbTable3");
      String dbt_idAtt = config.getInitParameter("dbt_idAtt");
      String dbtask_nameAtt = config.getInitParameter("dbtask_nameAtt");
      sqlCommand = "SELECT " + dbfirst_nameAtt + " AS FIRST_NAME," + dblast_nameAtt
         + " AS LAST_NAME," + dbjob_Att + " AS JOB FROM " + dbTable + " WHERE " + dbe_idAtt
         + " LIKE ?";
      sqlCommand2 = "SELECT " + dbt_idAtt + " AS TASK_ID, " + dbtask_nameAtt + " AS TASK_NAME FROM "
              + dbTable3 + " INNER JOIN " + dbTable2 + " USING (" + dbt_idAtt + ") WHERE "
              + dbe_idAtt + " LIKE ?";
   }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession session = request.getSession(true);
        String userId = session.getId();
        String e_id = null;
        String first_name = null;
        String last_name = null;
        String job = null;
        RequestDispatcher dispatcher = null;
        
        //Request type default string
        String requestType = (String) session.getAttribute("request");
        System.out.println("Request Type: " + requestType);
        
        if (requestType.equals("remove"))
        {
            e_id = request.getParameter("e_id");
            
            System.out.println("Elsewhere");
            
            dispatcher
               = getServletContext().getRequestDispatcher("/EmployeeRemoveEntityServlet");
            dispatcher.forward(request, response);
        }
        
        else if (requestType.equals("add"))
        {
            System.out.println("Here");
            
            first_name = request.getParameter("first_name");
            last_name = request.getParameter("last_name");
            job = request.getParameter("job");
            
               dispatcher
                  = getServletContext().getRequestDispatcher("/EmployeeAddEntityServlet");
               dispatcher.forward(request, response);
        }
        
        else
        {
            e_id = request.getParameter("e_id");
            
            if (e_id== null || e_id.length() == 0)
               e_id = "%";
            // query database
            Connection conn = null;
            PreparedStatement prepStmt = null;
            ResultSet resultSet = null;
            ResultSet resultSet2 = null;
            if (sqlCommand != null && dataSauce != null)
            {
               try
               {
                  conn = dataSauce.getConnection();
                  prepStmt = conn.prepareStatement(sqlCommand);
                  prepStmt.setString(1, e_id);
                  resultSet = prepStmt.executeQuery();
                  logger.info("Successfully sasd executed query for e_id "
                     + e_id);
               }
               catch (SQLException e)
               {
                  logger.severe("Unable to execute query for e_id "
                     + e_id + ": " + e);
               }
            }

            if (sqlCommand2 != null && dataSauce != null)
            {
               try
               {
                  conn = dataSauce.getConnection();
                  prepStmt = conn.prepareStatement(sqlCommand2);
                  prepStmt.setString(1, e_id);
                  resultSet2 = prepStmt.executeQuery();
                  logger.info("Successfully executed tasks query for e_id "
                     + e_id);
               }
               catch (SQLException e)
               {
                  logger.severe("Unable to execute tasks query for e_id "
                     + e_id + ": " + e);
               }
            }

              response.setContentType("text/html;charset=UTF-8");
              try (PrintWriter out = response.getWriter()) {
                  /* TODO output your page here. You may use following sample code. */
              out.println("<!DOCTYPE html>");
               out.println("<html>");
               out.println("<head>");
               out.println("<title>EmployeeServlet Response</title>");
               out.println("</head>");
               out.println("<body>");
               out.println("<h1>Found Employee with ID " + filter(e_id)
                  + "</h1>");
               if (resultSet != null)
               {
                  out.println("<TABLE cellspacing=1 border=5>");
                  out.println("<TR><TD><B>First Name</B></TD>"
                     + "<TD><B>Last Name</B></TD>"
                     + "<TD><B>Job</B></TD></TR>");
                  try
                  {
                     while (resultSet.next())
                     {
                        String fname = resultSet.getString("first_name");
                        String lname = resultSet.getString("last_name");
                        String job_title = resultSet.getString("job");
                        out.println("<TR><TD>" + filter(fname) + "</TD><TD>"
                           + filter(lname) + "</TD><TD>"
                           + filter(job_title) +"</TD></TR>");
                     }
                  }
                  catch (SQLException e)
                  {
                     logger.severe("Exception in results "
                        + e_id + ": " + e);
                  }
                  out.println("</TABLE>");
               }

               if (resultSet2 != null)
               {
                  out.println("<TABLE cellspacing=1 border=5>");
                  out.println("<TR><TD><B>Task ID</B></TD>"
                     + "<TD><B>Task Name</B></TD></TR>");
                  try
                  {
                     while (resultSet2.next())
                     {
                        String t_id = resultSet2.getString("task_id");
                        String task_name = resultSet2.getString("task_name");
                        out.println("<TR><TD>" + filter(t_id) + "</TD><TD>"
                           + filter(task_name) +"</TD></TR>");
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
                  + response.encodeURL("index.jsp") + QUOTE + ">"
                  + "Return to the search page</A></P>");
               out.println("</body>");
               out.println("</html>");
              out.println("<P><A href=" + QUOTE
                  + response.encodeURL("index.jsp") + QUOTE + ">"
                  + "View the jobs</A></P>");
                      out.println("<P><A href=" + QUOTE
                  + response.encodeURL("add.jsp") + QUOTE + ">"
                  + "Enter another employee</A></P>");
                      out.println("<P><A href=" + QUOTE
                  + response.encodeURL("remove.jsp") + QUOTE + ">"
                  + "Remove an employee</A></P>");
              out.println("<h4>Your unique session id is " + userId
                  + "</h4>");
               out.println("</body>");
               out.println("</html>");
              }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
    }// </editor-fold>

}