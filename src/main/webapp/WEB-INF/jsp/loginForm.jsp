<form class="login-tag-form p-l-55 p-r-55 p-t-178" action="%s" method="post">
    <span class="login100-form-title">Sign In</span>
    %s
    <div class="wrap-input100 m-b-16">
        <input class="input100" type="text" name="login" id="login" placeholder="Login">
        <span id="loginError"> Login must have at least 3 characters: uppercase letters, lowercase letters, numbers, '_' , '-'.</span>
        %s
    </div>
    <div class="wrap-input100">
        <input class="input100" type="password" name="password" id="password" placeholder="Password">
        <span id="passwordError">Password must have at least 6 characters</span>
        %s
    </div>
    <div class="p-t-13 p-b-23"></div>
    <div class="container-login100-form-btn">
        <button class="login100-form-btn" id="submitLogin">Login</button>
    </div>
    <div class="flex-col-c p-t-100 p-b-40">
        <span class="txt1 p-b-9">Don't have an account?</span>
        <a href="%s" class="txt3">Sign up now</a>
    </div>
</form>