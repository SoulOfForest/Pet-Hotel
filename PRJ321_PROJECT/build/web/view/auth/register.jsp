<%-- 
    Document   : logout
    Created on : Jun 13, 2020, 12:47:18 PM
    Author     : ViruSs0209
--%>

<%@page import="javax.faces.bean.RequestScoped"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
    <link href="../css/register.css" type="text/css" rel="stylesheet"/>
    <link href="../css/fontawesome.min.css" type="text/css" rel="stylesheet"/>
    <title>Document</title>
</head>
<body>
    <div class="register-container">
       
        <div class="register__form">
            <h3>Member Register</h3>
            <form action="/register" method="POST">
                <div class="form-group">
                    <div class="group-name">
                        Name
                    </div>
                    <div class="group-input group-input-spe">
                        <div class="group-input-desc">
                            <input type="text" name="firstName" id="firstName" class="login__input" value="${requestScope.user.getFirstName()}" />
                            <label for="firstName">First Name</label>
                        </div>
                        <div class="group-input-desc">
                            <input type="text" name="lastName" id="lastName" class="login__input" value="${requestScope.user.getLastName()}"/>
                            <label for="lastName">Last Name</label>
                        </div>
                        <div class="form-group-error"></div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="group-name">
                        <label for="username">Username</label>
                    </div>
                    <div class="group-input">
                        <input type="text" name="username" id="username" class="login__input" value="${requestScope.user.getUserName()}">
                    </div>
                    <div class="form-group-error">${requestScope.userNameError}</div>
                </div>

                <div class="form-group">
                    <div class="group-name">
                        <label for="email">Email</label>
                    </div>
                    <div class="group-input">
                        <input type="email" name="email" id="email" class="login__input" value="${requestScope.user.getEmail()}">
                    </div>
                    <div class="form-group-error">${requestScope.emailError}</div>
                </div>

                <div class="form-group">
                    <div class="group-name">
                        <label for="password">Password</label>
                    </div>
                    <div class="group-input">
                        <i class="fas fa-eye-slash"></i>
                        <input type="password" name="password" id="password" class="login__input">
                    </div>
                    <div class="form-group-error"></div>
                </div>

                <div class="form-group">
                    <div class="group-name">
                        <label for="confirmPassword">Confirm Password</label>
                    </div>
                    <div class="group-input">
                        <input type="password" name="confirmPassword" id="confirmPassword" class="login__input">
                    </div>
                    <div class="form-group-error"></div>
                </div>

                <div class="form-group">
                    <div class="group-name">
                        <label for="age">Age</label>
                    </div>
                    <div class="group-input">
                        <input type="number" name="age" id="age" value="${requestScope.user.getAge()}" class="login__input">
                    </div>
                    <div class="form-group-error"></div>
                </div>

                <div class="form-group">
                    <div class="group-name">
                        <label for="gender">Gender</label>
                    </div>
                    <div class="group-input group-input-spe group-checkbox">
                        <div class="group-input-desc">
                            <input type="radio" name="gender" id="male" value="male" class="login__checkbox" checked=${requestScope.user.isGender()} />
                            <label for="male">Male</label>
                        </div>
                        <div class="group-input-desc">
                            <input type="radio" name="gender" id="female" valule="female" class="login__checkbox" checked=${!requestScope.user.isGender()} />
                            <label for="female">Female</label>
                        </div>
                        <div class="form-group-error"></div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="group-name">
                        <label for="country">Country</label>
                    </div>
                    <div class="group-input">
                        <select name="country" id="country">
                           
                        </select>
                    </div>
                </div>


                <button type="submit">Register</button>
            </form>
        </div>
        <div class="login-account">
            Have already an account ?
            <a href="/login">Login Here</a>
        </div>
    </div>
    <script src="../api/countriesAPI.js"></script>
    <script src="../js/register.js" type="text/javascript"></script>
    <script src="https://kit.fontawesome.com/a81368914c.js"></script>
</body>
</html>
