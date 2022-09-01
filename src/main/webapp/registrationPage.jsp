<%@page import="com.webShop.util.Parameters" %>
<%@page import="com.webShop.util.Attributes" %>
<%@page import="com.webShop.util.Constants" %>
<%@page import="com.webShop.util.LocalizationUtil" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>
<head>
    <title><fmt:message key="registrationPage.title"/></title>
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
<WStags:languageTag/>
<c:set var="bundle" value="${LocalizationUtil.getResourceBundle(requestScope.language)}" scope="request" />
<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <form class="login100-form validate-form p-l-55 p-r-55 p-t-178" action="${Constants.REGISTRATION_SERVLET}"
                  method="post" enctype="multipart/form-data">
					<span class="login100-form-title">
						<fmt:message key="registrationPage.title"/>
					</span>

                ${requestScope.errors.get(Attributes.PAGE_GENERATION_TIME)}

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="text" name="login" id="login" placeholder="${bundle.getString('registrationPage.login')}"
                           value="${requestScope.regBean.login}">
                    <span id="loginError">
                        <fmt:message key="registrationPage.loginError"/>
                    </span>
                    ${requestScope.errors.get(Parameters.LOGIN)}
                </div>

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="text" name="name" id="name" placeholder="${bundle.getString('registrationPage.name')}"
                           value="${requestScope.regBean.name}">
                    <span id="nameError">
                        <fmt:message key="registrationPage.nameError"/>
                    </span>
                    ${requestScope.errors.get(Parameters.NAME)}
                </div>

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="text" name="surname" id="surname" placeholder="${bundle.getString('registrationPage.surname')}"
                           value="${requestScope.regBean.surname}">
                    <span id="surnameError">
                        <fmt:message key="registrationPage.surnameError"/>
                    </span>
                    ${requestScope.errors.get(Parameters.SURNAME)}
                </div>

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="password" name="password" id="password" placeholder="${bundle.getString('registrationPage.password')}">
                    <span id="passwordError">
                        <fmt:message key="registrationPage.passwordError"/>

                    </span>
                    ${requestScope.errors.get(Parameters.PASSWORD)}
                </div>

                <div class="wrap-input100 m-b-16">
                    <input class="input100" type="password" name="repeat-password" id="repeat-password"
                           placeholder="${bundle.getString('registrationPage.repeatPassword')}">
                    <span id="passwordRepeatError">
                        <fmt:message key="registrationPage.passwordRepeatError"/>
                    </span>
                    ${requestScope.errors.get(Parameters.REPEAT_PASSWORD)}
                </div>

                <div class="wrap-input100">
                    <input class="input100" type="email" name="email" id="email" placeholder="${bundle.getString('registrationPage.email')}"
                           value="${requestScope.regBean.email}">
                    <span id="emailError">
                        <fmt:message key="registrationPage.emailError"/>
                    </span>
                    ${requestScope.errors.get(Parameters.EMAIL)}
                </div>

                <div class="wrap-input100">
                    <input type="checkbox" name="sendMail" id="sendMail" value="true">
                    <label for="sendMail"><fmt:message key="registrationPage.label.sendMail"/></label>
                </div>

                <div class="wrap-input100">
                    <WStags:captchaTag/>
                    ${requestScope.errors.get(Parameters.USER_CAPTCHA)}
                </div>

                <div class="wrap-input100 p-t-13 p-b-23">
                    <label for="avatar"><fmt:message key="registrationPage.label.avatar"/></label>
                    <input type="file" name="avatar" id="avatar" accept=".png, .jpeg, .jpg">
                    ${requestScope.errors.get(Parameters.AVATAR)}
                </div>

                <div class="container-login100-form-btn">
                    <button class="login100-form-btn" id="submitRegistration">
                        <fmt:message key="registrationPage.button.create"/>
                    </button>
                </div>

                <div class="flex-col-c p-t-170 p-b-40">
                    <span class="txt1 p-b-9">
                    <fmt:message key="registrationPage.alreadyReg"/>
                    </span>
                    <a href="${Constants.LOGIN_PAGE_PATH}" class="txt3">
                        <fmt:message key="loginPage.title"/>
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
<script src="js/localization.js"></script>
</body>
</html>