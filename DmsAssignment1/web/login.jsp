<%-- 
    Document   : login
    Created on : 30/03/2021, 11:50:17 PM
    Author     : churr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page for Administrator</title>
    </head>
    <body>
        <h1>Employee modifier database</h1>
        <h3>Please enter your login details</h3>
        <form action = "LoginServlet">
            <h4> Enter username:</h4> <input type ="text" name = "username"><br>
            <h4> Enter password: </h4><input type ="password" name = "password"><br>
            <br> <input type = "submit" value = "login"><br>
        </form>                     
    </body>
</html>
