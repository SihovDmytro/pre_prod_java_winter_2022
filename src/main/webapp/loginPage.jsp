<%@page import="com.webShop.util.Constants" %>
<%@page import="com.webShop.util.Parameters" %>
<%@page import="com.webShop.util.Attributes" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Sign In</title>
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
            <form class="login100-form p-l-55 p-r-55 p-t-178" action="${Constants.LOGIN_SERVLET}" method="post">
					<span class="login100-form-title">
						Sign In
                    </span>

                ${requestScope.errors.get(Attributes.CREDENTIALS)}

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="text" name="login" id="login" placeholder="Login">
                    <span id="loginError">
                        Login must have at least 3 characters: uppercase letters, lowercase letters, numbers, '_' , '-'.
                    </span>
                    ${requestScope.errors.get(Parameters.LOGIN)}
                </div>

                <div class="wrap-input100">
                    <input class="input100" type="password" name="password" id="password" placeholder="Password">
                    <span id="passwordError">
                        Password must have at least 6 characters
                    </span>
                    ${requestScope.errors.get(Parameters.PASSWORD)}
                </div>

                <div class="p-t-13 p-b-23"></div>

                <div class="container-login100-form-btn">
                    <button class="login100-form-btn" id="submitLogin">
                        Login
                    </button>
                </div>

                <div class="flex-col-c p-t-170 p-b-40">
						<span class="txt1 p-b-9">
							Don'`t have an account?
						</span>

                    <a href="${Constants.REGISTRATION_PAGE_PATH}" class="txt3">
                        Sign up now
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
<script src="js/validateLogin.js"></script>

</body>
</html>