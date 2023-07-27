let form = document.querySelector('.js-form'),
    formInputs = document.querySelectorAll('.form-control'),
    inputEmail = document.querySelector('.js-input-email'),
    inputName = document.querySelector('.js-input-name'),
    inputSurname = document.querySelector('.js-input-surname'),
    inputDate = document.querySelector('.js-input-date'),
    inputPassword = document.querySelector('.js-input-password'),
    inputRepeatPassword = document.querySelector('.js-input-repeatPassword');
import {subYears} from 'date-fns';
function validateEmail(email) {
    let reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3})|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(String(email).toLowerCase());
}

function validateName(name) {
    let reg = /^[a-z ,.'-]+$/i;
    return reg.test(String(name));
}

function validateSurname(surname) {
    let reg = /^[a-z ,.'-]+$/i;
    return reg.test(String(surname));
}

function validatePassword(password) {
    let reg = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$/;
    return reg.test(String(password));
}

function validatePasswordMatching(password, repeatPassword) {
    return (String(password).valueOf() === String(repeatPassword).valueOf());
}

function validateDate(date) {
    return subYears(new Date(), 10) >= date;

}

form.onsubmit = function () {
    let emailVal = inputEmail.value,
        nameVal = inputName.value,
        surnameVal = inputSurname.value,
        passwordVal = inputPassword.value,
        dateVal = inputDate.value,
        repeatPasswordVal = inputRepeatPassword.value,
        emptyInputs = Array.from(formInputs).filter(input => input.value === '');


    formInputs.forEach(function (input) {
        if (input.value === '') {
            input.style.border = 'solid';
            input.style.borderColor = '#FF0000';
        } else {
            input.style.border = 'none';
            input.style.borderColor = 'transparent';
        }
    });

    if (emptyInputs.length !== 0) {
        console.log('inputs not filled');
        return false;
    }

    if (!validateEmail(emailVal)) {
        inputEmail.placeholder = 'Email is not valid';
        inputEmail.style.border = 'solid';
        inputEmail.style.borderColor = '#FF0000';
        return false;
    } else {
        inputEmail.placeholder = 'Enter email';
        inputEmail.style.border = 'none';
        inputEmail.style.borderColor = 'transparent';
    }

    if (!validateName(nameVal)) {
        inputName.placeholder = 'Name is not valid';
        inputName.style.border = 'solid';
        inputName.style.borderColor = '#FF0000';
        return false;
    } else {
        inputName.placeholder = 'Enter name';
        inputName.style.border = 'none';
        inputName.style.borderColor = 'transparent';
    }

    if (!validateSurname(surnameVal)) {
        inputSurname.placeholder = 'Surname is not valid';
        inputSurname.style.border = 'solid';
        inputSurname.style.borderColor = '#FF0000';
        return false;
    } else {
        inputSurname.placeholder = 'Enter surname';
        inputSurname.style.border = 'none';
        inputSurname.style.borderColor = 'transparent';
    }

    if(!validateDate(dateVal)){
        inputDate.placeholder = 'Surname is not valid';
        inputDate.style.border = 'solid';
        inputDate.style.borderColor = '#FF0000';
        return false;
    }else {
        inputDate.placeholder = 'Enter surname';
        inputDate.style.border = 'none';
        inputDate.style.borderColor = 'transparent';
    }

    if (!validatePassword(passwordVal)) {
        inputPassword.placeholder = 'Password is not valid';
        inputPassword.style.border = 'solid';
        inputPassword.style.borderColor = '#FF0000';
        return false;
    } else {
        inputPassword.placeholder = 'Enter password';
        inputPassword.style.border = 'none';
        inputPassword.style.borderColor = 'transparent';
    }

    if (!validatePasswordMatching(passwordVal, repeatPasswordVal)) {
        inputRepeatPassword.placeholder = 'Password is not match';
        inputRepeatPassword.style.border = 'solid';
        console.log(String(passwordVal));
        console.log(String(repeatPasswordVal));
        inputRepeatPassword.style.borderColor = '#FF0000';
        return false;
    } else {
        inputRepeatPassword.placeholder = 'Confirm password';
        inputRepeatPassword.style.border = 'none';
        inputRepeatPassword.style.borderColor = 'transparent';
    }
    return true;
}