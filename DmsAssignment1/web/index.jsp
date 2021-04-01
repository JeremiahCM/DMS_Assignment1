<%-- 
    Document   : index
    Created on : 31/03/2021, 12:46:51 AM
    Author     : churr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%// Sets the 'request' attribute for session to indicate user may make a search request%>
<%session.setAttribute("request", "search");%>
<!DOCTYPE html>
<html>
    <head>
        <title>Server Interaction</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <%
            // Redirect user to login page if they attempt to access page without logging in
            if(session.getAttribute("username") == null)
                response.sendRedirect("login.jsp");
        %>
        <h1>Index page for employee database</h1>
        <form method="get" action="/DmsAssignment1/EmployeeServlet">
            <table cellpadding="3" cellspacing="0" border="2">
                <tr>
                    <td>Search the database:</td>
                    <td><input type="text" name="e_id"/></td>
                </tr>
            </table>
            <br><INPUT type="submit" value="Search"/><br>   
        </form>
        <br>
        <table cellpadding="6" cellspacing="0" border="2">
         <tr> 
            <td><a href ="add.jsp">Add entry</a>
            </td>
            </tr> 
            <tr> 
            <td><a href ="remove.jsp">Remove entry</a>
            </td> 
        <tr> 
        </table>
    </body>
</html>
