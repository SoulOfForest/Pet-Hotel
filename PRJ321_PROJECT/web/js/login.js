const inputFields = document.querySelectorAll('.login__input');

function handleInputBlur() {
    if (this.value === '') {
        this.parentNode.classList.remove('focus');
    }
}

function handleInputFocus() {
    this.parentNode.classList.add('focus');
}

Array.from(inputFields).forEach(input => {
    input.addEventListener('focus', handleInputFocus);
    input.addEventListener('blur', handleInputBlur);
})
