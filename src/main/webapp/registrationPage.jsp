<%@ taglib prefix="WStags" uri="http://webShopTags.com" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.webShop.util.Parameters" %>
<%@page import="com.webShop.util.Attributes" %>
<%@page import="com.webShop.util.Constants" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign Up</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="css/util.css">
    <link rel="stylesheet" type="text/css" href="css/main.css">
    <!--===============================================================================================-->
</head>
<body>

<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <form class="login100-form validate-form p-l-55 p-r-55 p-t-178" action="${Constants.REGISTRATION_SERVLET}"
                  method="post" enctype="multipart/form-data">
					<span class="login100-form-title">
						Sign Up
					</span>

                ${requestScope.errors.get(Attributes.PAGE_GENERATION_TIME)}

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="text" name="login" id="login" placeholder="Login"
                           value="${requestScope.regBean.login}">
                    <span id="loginError">
                        Login must have at least 3 characters: uppercase letters, lowercase letters, numbers, '_' , '-'.
                    </span>
                    ${requestScope.errors.get(Parameters.LOGIN)}
                </div>

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="text" name="name" id="name" placeholder="Name"
                           value="${requestScope.regBean.name}">
                    <span id="nameError">
                        Name must have at least 1 character
                    </span>
                    ${requestScope.errors.get(Parameters.NAME)}
                </div>

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="text" name="surname" id="surname" placeholder="Surname"
                           value="${requestScope.regBean.surname}">
                    <span id="surnameError">
                        Surname must have at least 1 character
                    </span>
                    ${requestScope.errors.get(Parameters.SURNAME)}
                </div>

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="password" name="password" id="password" placeholder="Password">
                    <span id="passwordError">
                        Password must have at least 6 characters
                    </span>
                    ${requestScope.errors.get(Parameters.PASSWORD)}
                </div>

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="password" name="repeat-password" id="repeat-password"
                           placeholder="Repeat password">
                    <span id="passwordRepeatError">
                        Passwords are different
                    </span>
                    ${requestScope.errors.get(Parameters.REPEAT_PASSWORD)}
                </div>

                <div class="wrap-input100">
                    <input class="input100" type="email" name="email" id="email" placeholder="Email"
                           value="${requestScope.regBean.email}">
                    <span id="emailError">
                        Please enter the email in the format: "example@example.com"
                    </span>
                    ${requestScope.errors.get(Parameters.EMAIL)}
                </div>

                <div class="wrap-input100">
                    <input type="checkbox" name="sendMail" id="sendMail" value="true">
                    <label for="sendMail">I want to receive the newsletter</label>
                </div>

                <div class="wrap-input100">
                    <WStags:captchaTag/>
                    ${requestScope.errors.get(Parameters.USER_CAPTCHA)}
                </div>

                <div class="wrap-input100 p-t-13 p-b-23">
                    <label for="avatar">Your avatar</label>
                    <input type="file" name="avatar" id="avatar" accept=".png, .jpeg, .jpg">
                </div>

                <div class="container-login100-form-btn">
                    <button class="login100-form-btn" id="submitRegistration">
                        Create
                    </button>
                </div>

                <div class="flex-col-c p-t-170 p-b-40">
                    <span class="txt1 p-b-9">
                        Already registered?
                    </span>
                    <a href="${Constants.LOGIN_PAGE_PATH}" class="txt3">
                        Sign in
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>


<!--===============================================================================================-->
<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/bootstrap/js/popper.js"></script>
<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="vendor/daterangepicker/moment.min.js"></script>
<script src="vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
<script src="vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
<script src="js/validateRegistration.js"></script>

</body>
</html>