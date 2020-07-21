const showPasswordIcon = document.querySelector('.fa-eye-slash');

const form = document.querySelector(".register__form > form");

const firstName = document.querySelector("#firstName");
const lastName = document.querySelector("#lastName");
const userName = document.querySelector("#username");
const email = document.querySelector("#email");
const password = document.querySelector("#password");
const confirmPassword = document.querySelector("#confirmPassword");
const age = document.querySelector("#age");
const male = document.querySelector("#male");
const female = document.querySelector("#female");

let isHide = true;
let hasError = false;

showPasswordIcon.addEventListener('click', function () {
    if (isHide) {
        password.type = 'text';
        showPasswordIcon.className = 'fas fa-eye';
        isHide = false;
    } else {
        password.type = 'password';
        showPasswordIcon.className = 'fas fa-eye-slash';
        isHide = true;
    }
})

form.addEventListener('submit', function(e) {   
    formValidation();
    checkEqualPassword();
    
    if (hasError) {
         e.preventDefault();
    } 
})


const formValidation = () => {
    const emptyErrors = checkFormEmpty();
    emptyErrors.forEach(error => {
        const ele = error.ele;
        if (error.msg && !hasError) {
            hasError = true;
        }
        ele.parentElement.parentElement.lastElementChild.innerText = error.msg;
        
    })
}

const checkEqualPassword = () => {
    if (password.value.trim() && confirmPassword.value.trim()) {
        if (password.value !== confirmPassword.value) {
            confirmPassword.parentElement.parentElement.lastElementChild.innerText = 'Password Confirm Must Be Same As Password';
            hasError = true;
        } else {
            hasError = false;
        }
    }
}

const checkFormEmpty = () => {
    let errors = [];
    if (!firstName.value) {
        errors.push({
            ele: firstName,
            msg: 'FirstName Field Can\'t Be Empty !'
        });
    } else {
        errors.push({
            ele: firstName,
            msg: ''
        });
    }
    
    if (!lastName.value) {
        errors.push({
            ele: lastName,
            msg: 'LastName Field Can\'t Be Empty !'
        });
    } else {
        errors.push({
            ele: lastName,
            msg: ''
        });
    }
    
    if (!userName.value) {
        errors.push({
            ele: userName,
            msg: 'Username Field Can\'t Be Empty !'
        });
    } else {
        errors.push({
            ele: userName,
            msg: ''
        });
    }
    
    if (!email.value) {
        errors.push({
            ele: email,
            msg: 'Email Field Can\'t Be Empty !'
        });
    } else {
        errors.push({
            ele: email,
            msg: ''
        });
    }
    
    if (!password.value) {
        errors.push({
            ele: password,
            msg: 'Password Field Can\'t Be Empty !'
        });
    } else {
        errors.push({
            ele: password,
            msg: ''
        });
    }
    if (!confirmPassword.value) {
        errors.push({
            ele: confirmPassword,
            msg: 'Confirm Password Field Can\'t Be Empty !'
        });
    } else {
        errors.push({
            ele: confirmPassword,
            msg: ''
        });
    }
    if (!age.value) {
        errors.push({
            ele: age,
            msg: 'Age Field Can\'t Be Empty !'
        });
    } else {
        errors.push({
            ele: age,
            msg: ''
        });
    }
    if (!male.checked && !female.checked) {
        errors.push({
            ele: male,
            msg: 'Gender Field Can\'t Be Empty !'
        });
    }else {
        errors.push({
            ele: male,
            msg: ''
        });
    }
    
    return errors;
}