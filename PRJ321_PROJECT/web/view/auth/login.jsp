<%-- 
    Document   : login
    Created on : Jun 13, 2020, 12:47:12 PM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
    <link href="../css/login.css" type="text/css" rel="stylesheet"/>
    <link href="../css/fontawesome.min.css" type="text/css" rel="stylesheet"/>
    <link rel="stylesheet" href="../css/iziToast.min.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Document</title>
</head>
<body>
     <c:if test="${requestScope.register != null}">
        <div class="register"></div>
    </c:if>
    <c:if test="${requestScope.disable != null}">
        <div class="disable"></div>
    </c:if>
    <c:if test="${requestScope.error != null}">
        <div class="error"></div>
    </c:if>
    <div class="login-container">
        <div class="login__logo">
            <img src="../images/music.png"/>
        </div>
        <div class="login__form">
            <h3>Member Login</h3>
            <form action="/login" method="POST">
                <div class="form-group">
                    <i class="fas fa-user"></i>
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" class="login__input"/>
                </div>
                <div class="form-group">
                    <i class="fas fa-lock"></i>
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" class="login__input">
                </div>

                <a class="login__forgot" href="#" style="display: block">Forgot Password ?</a>
                <button type="submit">Login</button>
            </form>
        </div>
        <div class="create-account">
            <a href="/register">Create Your Account</a>
            <i class="fas fa-long-arrow-alt-right"></i>
        </div>
    </div>
    <script src="../js/login.js" type="text/javascript"></script>
    <script src="https://kit.fontawesome.com/a81368914c.js"></script>    
    <script src="../js/iziToast.min.js"></script>
    <script>
   window.addEventListener('DOMContentLoaded', function () {
            if (document.querySelector('.register') != null) {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        message: 'Register Successfully !',
                    });
                }, 50);
            }
            
            if (document.querySelector('.disable') != null) {
                setTimeout(() => {
                    iziToast.error({
                        title: 'Error',
                        message: 'This account is already disabled',
                    });
                }, 50);
            }
            
            if (document.querySelector('.error') != null) {
                setTimeout(() => {
                    iziToast.error({
                        title: 'Error',
                        message: 'Username or password is not correct!',
                    });
                }, 50);
            }
        });
    </script>
</body>
</html>