<%-- 
    Document   : addandremove
    Created on : 30/03/2021, 12:32:17 AM
    Author     : churr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%session.setAttribute("request", "remove");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Remove an employee</title>
    </head>
    <body>
        
        <%
            if(session.getAttribute("username") == null)
            {
                response.sendRedirect("login.jsp");
            }
        %>
      <h1>Please Enter Employee ID</h1>
      <form action="EmployeeServlet" method="GET">
         <p>
            Employee ID:
            <input type="text" name="e_id"/>
         </p>
         <input type="submit"/>
      </form>
       <br/>
      <a href='<%= response.encodeURL(request.getContextPath()) %>'>
         Return to main page
      </a>
    </body>
</html>