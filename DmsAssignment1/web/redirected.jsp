<%-- 
    Document   : redirected
    Created on : 30/03/2021, 11:35:04 PM
    Author     : Jeremiah Martinez: 18027693 | Sanjeel P Nath: 17987458
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Details</title>
    </head>
    <body>
        <%
            // Redirect user to login page if they attempt to access page without logging in
            if(session.getAttribute("username") == null)
                response.sendRedirect("login.jsp");
        %>
        <h1>Some form bad input has been detected</h1>
        <br/>
        <h3>Use the link below to return back to the main page</h3>
        <br>
        <a href='<%= response.encodeURL(request.getContextPath()) %>'>
            Return to main page
        </a>
    </body>
</html>
