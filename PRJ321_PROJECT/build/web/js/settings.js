const themeSelector = document.querySelector('.theme-setting select');

themeSelector.addEventListener('change', function(e) {
    document.querySelector('.theme-preview').style.backgroundColor = this[this.selectedIndex].id;
})