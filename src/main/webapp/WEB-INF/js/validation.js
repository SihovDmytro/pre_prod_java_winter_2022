const submit = document.getElementById("submit");

const usernameError = document.getElementById("usernameError");
const passwordError = document.getElementById("passwordError");
const emailError = document.getElementById("emailError");
const passwordRepeatError = document.getElementById("passwordRepeatError");

usernameError.hidden = true;
passwordError.hidden = true;
emailError.hidden = true;
passwordRepeatError.hidden = true;

submit.addEventListener("click", function (e) {
        let usernameField = document.getElementById("username");
        let emailField = document.getElementById("email");
        let passwordField = document.getElementById("password");
        let passwordRepeatField = document.getElementById("repeat-password");
        usernameError.hidden = true;
        passwordError.hidden = true;
        passwordRepeatError.hidden = true;
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
        if (!validatePasswordRepeat(passwordField.value, passwordRepeatField.value)) {
            passwordRepeatError.hidden = false;
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
// const passwordRepeatError = $("#passwordRepeatError");
// usernameError.hide();
// passwordError.hide();
// emailError.hide();
// passwordRepeatError.hide();
//
// $("#submit").click(function (e) {
//     usernameError.hide();
//     passwordError.hide();
//     emailError.hide();
//     passwordRepeatError.hide();
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
//     if (!validatePasswordRepeat($("#password").val(), $("#repeat-password").val())) {
//         passwordRepeatError.show();
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

function validatePasswordRepeat(password, repeat) {
    return repeat.length <= 64 && repeat.length >= 6 && password === repeat;
}

function validateEmail(email) {
    const emailPattern = "[\\w_\\-.]+@([\\w-]+\\.)+[\\w-]{1,5}$";
    return email.length <= 30 && email.length >= 5 && email.match(emailPattern) !== null;
}