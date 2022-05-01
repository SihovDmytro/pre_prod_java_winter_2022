const submitLogin = document.getElementById("submitLogin");
console.log("submitLogin: "+submitLogin);

const loginError = document.getElementById("loginError");
const passwordError = document.getElementById("passwordError");

loginError.hidden = true;
passwordError.hidden = true;

submitLogin.addEventListener("click", function (e) {
        let loginField = document.getElementById("login");
        let passwordField = document.getElementById("password");
        console.log("loginField: "+loginField);
        console.log("passwordField: "+passwordField);
        loginError.hidden = true;
        passwordError.hidden = true;
        let valid = true;
        console.log("valid: "+valid);
        if (!validateLogin(loginField.value)) {
            console.log("wrong login");
            loginError.hidden = false;
            valid = false;
        }
        if (!validatePassword(passwordField.value)) {
            console.log("wrong pass");
            passwordError.hidden = false;
            valid = false;
        }

        if (!valid) {
            console.log("invalid");
            e.preventDefault();
        }
        return valid;
    }
)

function validateLogin(login) {
    const loginPattern = "^[\\w_-]{3,20}$";
    return login.length <= 20 && login.length >= 3 && login.match(loginPattern) !== null;
}

function validatePassword(password) {
    return password.length <= 64 && password.length >= 6;
}