<%-- 
    Document   : addandremove
    Created on : 30/03/2021, 12:32:17 AM
    Author     : churr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enter a new employee details</title>
    </head>
    <body>
      <h1>Please Enter Customer Details</h1>
      <form action="EmployeeServlet" method="GET">
         <p>
            First Name:
            <input type="text" name="firstName"/>
         </p>
         <p>
            Last Name:
            <input type="text" name="lastName"/>
         </p>
         <p>
            Job:
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