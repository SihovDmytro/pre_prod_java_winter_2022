
const submit = document.getElementById("submit");

const usernameError = document.getElementById("usernameError");
const passwordError = document.getElementById("passwordError");
const emailError = document.getElementById("emailError");
usernameError.hidden = true;
passwordError.hidden = true;
emailError.hidden = true;

submit.addEventListener("click", function (e) {
        let usernameField = document.getElementById("username");
        let emailField = document.getElementById("email");
        let passwordField = document.getElementById("password");
        usernameError.hidden = true;
        passwordError.hidden = true;
        emailError.hidden = true;
        let valid = true;

        if (!validateUsername(usernameField.value)) {
            usernameError.hidden = false;
            valid = false;
        }
        if (!validatePassword(passwordField.value)) {
            passwordError.hidden = false;
            valid = false;
        }
        if (!validateEmail(emailField.value)) {
            emailError.hidden = false;
            valid = false;
        }

        if (!valid) {
            e.preventDefault();
        }
        return valid;
    }
)

//====================================================================================================


// const usernameError = $("#usernameError");
// const passwordError = $("#passwordError");
// const emailError = $("#emailError");
// usernameError.hide();
// passwordError.hide();
// emailError.hide();
//
// $("#submit").click(function (e) {
//     usernameError.hide();
//     passwordError.hide();
//     emailError.hide();
//     let valid = true;
//
//     if (!validateUsername($("#username").val())) {
//         usernameError.show();
//         valid = false;
//     }
//     if (!validatePassword($("#password").val())) {
//         passwordError.show();
//         valid = false;
//     }
//     if (!validateEmail($("#email").val())) {
//         emailError.show();
//         valid = false;
//     }
//
//     if (!valid) {
//         e.preventDefault();
//     }
//
//     console.log(valid);
//     return valid;
// })

//====================================================================================================

function validateUsername(username) {
    const usernamePattern = "^[\\w_-]{3,20}$";
    return username.length <= 20 && username.length >= 3 && username.match(usernamePattern) !== null;
}

function validatePassword(password) {
    return password.length <= 64 && password.length >= 6;
}

function validateEmail(email) {
    const emailPattern = "[\\w_\\-.]+@([\\w-]+\\.)+[\\w-]{1,5}$";
    return email.length <= 30 && email.length >= 5 && email.match(emailPattern) !== null;
}