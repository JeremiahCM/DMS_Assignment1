<%-- 
    Document   : addandremove
    Created on : 30/03/2021, 12:32:17 AM
    Author     : churr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%// Sets the 'request' attribute for session to indicate user may make an add request%>
<%session.setAttribute("request", "add");%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enter a new employee details</title>
    </head>
    <body>
        <%
            // Redirect user to login page if they attempt to access page without logging in
            if(session.getAttribute("username") == null)
                response.sendRedirect("login.jsp");
        %>
        <h1>Please Enter The New Employee Details</h1><br>
        <form action="EmployeeServlet" method="GET">
            <p>
                First Name:
                <input type="text" name="first_name"/>
            </p>
            <p>
                Last Name:
                <input type="text" name="last_name"/>
            </p>
            <p>
                Job:
                <input type="text" name="job"/>
            </p>
            <br><h3>Submit to confirm</h3>
            <input type="submit"/>
        </form>
        <br/>
        <a href='<%= response.encodeURL(request.getContextPath()) %>'>
            Return to main page
        </a>
    </body>
</html>