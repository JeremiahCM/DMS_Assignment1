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

/*
 * Servlet which deals with an request regarding an employee via JPA and Entities
 * @author Jeremiah Martinez: 18027693 | Sanjeel P Nath: 17987458
 * 
 * The files have used code learned from several stackoverflow threads, Telusko youtube channel and reused
 * from lab exercises, few bits of the following is adapted not copy and pasted, except for lab code we have used
 *
 * Grading Method: both equal
*/

//Define initial servlet configuration parameters.
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
    private final char QUOTE = '"';
    private Logger logger;
    private String sqlCommandEmployee;
    private String sqlCommandTask;
    
    @Resource(mappedName = "jdbc/MySQLEmployeeRes")
    private DataSource dataSauce;
    
    // Creates a new instance of EmployeeServlet.
    public EmployeeServlet()
    {
        logger = Logger.getLogger(getClass().getName());
    }
    
    @Override
    public void init()
    {
        // Obtain servlet configuration values from annotation or web.xml.
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
        
        // Create query statements for the database.
        /*
           Employee query will retrieve an employee's first name, last name and
            job title for an employee ID given by user input.
        */
        sqlCommandEmployee = "SELECT " + dbfirst_nameAtt + " AS FIRST_NAME," + dblast_nameAtt
           + " AS LAST_NAME," + dbjob_Att + " AS JOB FROM " + dbTable + " WHERE " + dbe_idAtt
           + " LIKE ?";
        
        /*
           Employee query will retrieve an employee's assigned tasks, showing
            the tasks IDs and task names based on employee ID given by user input.
        */
        sqlCommandTask = "SELECT " + dbt_idAtt + " AS TASK_ID, " + dbtask_nameAtt + " AS TASK_NAME FROM "
                + dbTable3 + " INNER JOIN " + dbTable2 + " USING (" + dbt_idAtt + ") WHERE "
                + dbe_idAtt + " LIKE ?";
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        HttpSession session = request.getSession(true);
        RequestDispatcher dispatcher = null;
        String userId = session.getId();
        String e_id = null;
        String first_name = null;
        String last_name = null;
        String job = null;
        String fname = null;
        String lname = null;
        
        /*
           Retrives 'request' attribute from the session.
           Request attribute tells EmployeeServlet what kind of request is
           being made for the database, and may redirect this request.
        */
        String requestType = (String) session.getAttribute("request");
        
        /*
            Check if request is to remove an employee.
            If this is true, get the value of the employee ID from the request.
            Then forward this request to the EmployeeRemoveEntityServlet.
        */
        if (requestType.equals("remove"))
        {
            e_id = request.getParameter("e_id");
            
            dispatcher
                = getServletContext().getRequestDispatcher("/EmployeeRemoveEntityServlet");
            dispatcher.forward(request, response);
        }
        
        /*
            Then check if request is to add an employee.
            If this is true, get the values of the employee's first name,
             last name and job title from the request.
            Then forward this request to the EmployeeAddEntityServlet.
        */
        else if (requestType.equals("add"))
        {
            first_name = request.getParameter("first_name");
            last_name = request.getParameter("last_name");
            job = request.getParameter("job");
            
            dispatcher
                = getServletContext().getRequestDispatcher("/EmployeeAddEntityServlet");
            dispatcher.forward(request, response);
        }
        
        /*
            Otherwise the request must be to search for an employee.
            The servlet will connect to the database, and run queries to retrieve
             the employee's details and assigned tasks.
        */
        else
        {
            e_id = request.getParameter("e_id");
            
            if (e_id== null || e_id.length() == 0)
                e_id = "%";
            
            Connection conn = null;
            PreparedStatement prepStmt = null;
            ResultSet resultSet = null;
            ResultSet resultSet2 = null;
            if (sqlCommandEmployee != null && dataSauce != null)
            {
                /*
                   Attempt to establish a connection the database, then query
                    the database for the employee's details.
                   Otherwise catch the error and print error message to log
                */
                try
                {
                    conn = dataSauce.getConnection();
                    prepStmt = conn.prepareStatement(sqlCommandEmployee);
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

            if (sqlCommandTask != null && dataSauce != null)
            {
                /*
                   Attempt to establish a connection the database, then query
                    the database for the details of employee's assigned tasks.
                   Otherwise catch the error and print error message to log
                */
                try
                {
                    conn = dataSauce.getConnection();
                    prepStmt = conn.prepareStatement(sqlCommandTask);
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

            // Creates the web page for employee search results
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
                            fname = resultSet.getString("first_name");
                            lname = resultSet.getString("last_name");
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
               
                if(fname == null || lname == null || fname.length() == 0 || lname.length() == 0)
                {
                    try
                    {
                       if (prepStmt != null)
                          prepStmt.close();
                       if (conn != null)
                          conn.close();
                    }
                    catch (SQLException e)
                    { 
                    }

                    response.sendRedirect("redirected.jsp");
                }
               

                if (resultSet2 != null)
                {
                    out.println("<TABLE cellspacing=1 border=5>");
                    out.println("<br><TR><TD><B>Task ID</B></TD>"
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
                    + response.encodeURL("add.jsp") + QUOTE + ">"
                    + "Enter another employee</A></P>");
                out.println("<P><A href=" + QUOTE
                    + response.encodeURL("remove.jsp") + QUOTE + ">"
                    + "Remove an employee</A></P>");
                out.println("<h5>Session ID: " + userId
                    + "</h5>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
    
    public static String filter(String text) {
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