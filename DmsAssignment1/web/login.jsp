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
        <title>Login Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action = "LoginServlet">
            Enter username: <input type ="text" name = "username"><br>
            Enter password: <input type ="text" name = "password"><br>
            <input type = "submit" value = "login">
        </form>
                                 
    </body>
</html>
