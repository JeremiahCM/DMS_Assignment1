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
        <h1>Some form bad input has been detected</h1>
        <br/>
        <h3>You can return back to the following pages</h3>
        <br>
      <a href='<%= response.encodeURL(request.getContextPath()) %>'>
         Return to main page
      </a>
    </body>
</html>
