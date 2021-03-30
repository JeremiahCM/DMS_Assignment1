<%-- 
    Document   : index
    Created on : 31/03/2021, 12:46:51 AM
    Author     : churr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
      <title>Server Interaction</title>
      <meta charset="UTF-8">
      <meta name="viewport"
            content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%
            if(session.getAttribute("username") == null)
            {
                response.sendRedirect("login.jsp");
            }
        %>
      h1>Add or Search this database</h1>
      <form method="get" action="/DmsAssignment1/EmployeeServlet">
         <table cellpadding="0" cellspacing="0" border="0">
            <tr>
               <td>Search the database:</td>
               <td><input type="text" name="e_id"/></td>
            </tr>
            <tr>
               <td>
                  <input type="checkbox" name="entities" value="true"/>
                  Use entities
               </td>
               <td><INPUT type="submit" value="Search"/></td>
            </tr>
         </table>
      </form>
    </body>
</html>
