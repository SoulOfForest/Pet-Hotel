const ownerInput = document.querySelector('#owner');

MicroModal.init({
    awaitCloseAnimation: false, // set to false, to remove close animation
});

document.querySelector('.container-left form').addEventListener('submit', function (e) {
    e.preventDefault();

    document.querySelector('.modal__content ul').innerHTML = `
                        <li>Owner: <strong style="margin-left: 10px;">${document.querySelector('#owner').value}</strong></li>
                                <li>                 
                                    Pet Name: <strong style="margin-left: 10px;">${document.querySelector('#name').value}</strong>
                                </li>
                                <li>                 
                                    Breed: <strong style="margin-left: 10px;">${document.querySelector('#breed').value}</strong>
                               </li>
                              <li>Type: <strong style="margin-left: 10px;">${document.querySelector('#type')[document.querySelector('#type').selectedIndex].innerText}</strong>.</li>
                              <li>
                                Size: <strong style="margin-left: 10px;">${document.querySelector('#size')[document.querySelector('#size').selectedIndex].innerText}</strong>.
                                </li>
                              
                                <li>                 
                                 Avatar: <img style="width: 100%;margin-top: 10px;" src="${document.querySelector('.image--preview').getAttribute('src')}"/>
                               </li>
                   `;
    MicroModal.show('modal-1');

    document.querySelector('.modal__footer button.ui.primary').addEventListener('click', () => {
        this.submit();
    })
})

ownerInput.addEventListener('focus', function () {

    fetch(`http://localhost:3000/PRJ321_REST/users/search`, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify({
            "owner": "",
            "role": ""
        }),
        headers: {
            "Content-Type": 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded',
        }
    })
            .then(response => {
                return response.json();
            })
            .then(data => {
                if (data.length > 0) {
                    let temp = `<select id="users" name="users">`;

                    data.map(user => temp += `<option value=${user.email}>${user.email}</option>`);

                    temp += "</select>";

                    iziToast.question({
                        timeout: 20000,
                        close: false,
                        drag: false,
                        overlay: true,
                        displayMode: 'once',
                        id: 'question',
                        zindex: 999,
                        title: 'Hey',
                        message: 'Please Choose One User: ',
                        position: 'center',
                        inputs: [
                            [
                                temp, 'change', function (instance, toast, select, e) {

                                }
                            ]
                        ],
                        buttons: [
                            ['<button><b>Confirm</b></button>', function (instance, toast, button, e, inputs) {

                                    document.querySelector('.container-left #owner').value = inputs[0].options[inputs[0].selectedIndex].value;
                                    instance.hide({transitionOut: 'fadeOut'}, toast, 'button');
                                }, false], // true to focus
                            ['<button>NO</button>', function (instance, toast, button, e) {
                                    instance.hide({transitionOut: 'fadeOut'}, toast, 'button');
                                }]
                        ],
                        onClosed: function (instance, toast, closedBy) {
                            document.querySelector('.container-left #owner').blur();
                        },
                    });
                } else {
                    setTimeout(() => {
                        iziToast.error({
                            title: 'error',
                            message: 'Can\'t Find Any User(s) !',
                        });
                    }, 50);
                }
            })
            .catch(err => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'error',
                        message: 'There is some error when searching user !',
                    });
                }, 50);
            });
});