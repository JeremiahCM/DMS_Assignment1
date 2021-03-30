<%-- 
    Document   : redirected
    Created on : 30/03/2021, 11:35:04 PM
    Author     : churr
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Details</title>
    </head>
    <body>
      <h1>Please Enter Customer Details</h1>
      <form action="lookup" method="GET">
         <p>
            First Name:
            <input type="text" name="firstname"/>
         </p>
         <p>
            Last Name:
            <input type="text" name="lastname"/>
         </p>
         <p>
            Year of Birth:
            <input type="text" name="yearofbirth"/>
         </p>
         <p>
            Gender:
            <input type="radio" name="gender" value="male" checked/>Male
            <input type="radio" name="gender" value="female"/>Female
         </p>
         <input type="submit"/>
      </form>
       <br/>
      <a href='<%= response.encodeURL(request.getContextPath()) %>'>
         Return to main page
      </a>
    </body>
</html>
