const editOwner = document.querySelector('.edit-owner');
const createdAt = document.querySelector('#createdAt');
const createdTo = document.querySelector('#createdTo');
const petSelector = document.querySelector('#owner-pets');
const statusSelector = document.querySelector('#status-selector');
const totalFee = document.querySelector('#totalFee');
const extraServices = document.querySelector('.form-field input[type=checkbox]');
const extraFee = document.querySelector('#extra');
const mediumFee = document.querySelector('#medium');
const smallFee = document.querySelector('#small');
const largeFee = document.querySelector('#large');

$('#createdAt').datetimepicker({
    onChangeDateTime: function (dp, $input) {
        handleTimeChange();
    }
});
$('#createdTo').datetimepicker({
    onChangeDateTime: function (dp, $input) {
        handleTimeChange();
    }
});

MicroModal.init({
    awaitCloseAnimation: false, // set to false, to remove close animation
});

document.querySelector('.container-left form').addEventListener('submit', function (e) {
    e.preventDefault();

    document.querySelector('.modal__content ul').innerHTML = `
                        <li>Owner: <strong style="margin-left: 10px;">${document.querySelector('#owner').value}</strong></li>
                                <li>                 
                                    Pet Name: <strong style="margin-left: 10px;">${document.querySelector('a#owner-pets') ? document.querySelector('#owner-pets').innerText : document.querySelector('#owner-pets')[document.querySelector('#owner-pets').selectedIndex].innerText}</strong>
                                </li>
                                <li>                 
                                    Departure: <strong style="margin-left: 10px;">${document.querySelector('#createdAt').value}</strong>
                               </li>
                               <li>                 
                                    Arrival: <strong style="margin-left: 10px;">${document.querySelector('#createdTo').value}</strong>
                               </li>
                              <li>Status: <strong style="margin-left: 10px;">${document.querySelector('#status-selector')[document.querySelector('#status-selector').selectedIndex].innerText}</strong>.</li>      
                                <li>                 
                                    Extra Services: <strong style="margin-left: 10px;">${document.querySelector('input[name=extra]').checked}</strong>
                               </li>
                                <li>                 
                                 Total Fee: <strong style="margin-left: 10px;">${document.querySelector('#totalFee').value}</strong>
                               </li>
                   `;
    MicroModal.show('modal-1');

    document.querySelector('.modal__footer button.ui.primary').addEventListener('click', () => {
        this.submit();
    })
})



const handleTotalFeeBooking = (petSize, departure, arrival) => {
    const MILLS_IN_DAY = 1000 * 24 * 3600;

    const differentInTime = arrival.getTime() - departure.getTime();
    const differentInDays = differentInTime / MILLS_IN_DAY;

    const differentInWeeks = Math.floor(differentInDays / 7);

    const oddDays = differentInDays % 7;

    let bookingFee = 0;

    if (departure.getTime() && arrival.getTime()) {
        switch (petSize) {
            case 'small )' :
            {
                bookingFee += Math.round(smallFee.value * differentInDays);
                break;
            }
            case 'medium )':
            {
                bookingFee += Math.round(mediumFee.value * differentInDays);
                break;
            }
            case 'large )' :
            {
                bookingFee += Math.round(largeFee.value * differentInDays);
                break;
            }
        }
    }

    if (extraServices.checked) {
        bookingFee += parseFloat(extraFee.value);
    }

    totalFee.value = `$${bookingFee}`;
}

if (totalFee) {
    extraServices.addEventListener('change', function () {
        const currentFee = parseInt(totalFee.value.split("$")[1]);
        if (this.checked) {
            totalFee.value = `$${currentFee + 5}`;
        } else {
            totalFee.value = `$${currentFee - 5}`;
        }
    })

}

if (petSelector) {
    petSelector.addEventListener('change', function () {
        const petSize = this[this.selectedIndex].innerText;
        handleTotalFeeBooking(petSize.split('(')[1].trim(), new Date(createdAt.value), new Date(createdTo.value));
    })
}

statusSelector.addEventListener('change', function (e) {
    const status = statusSelector[statusSelector.selectedIndex].value;
    const userRole = document.querySelector('#userRole');

    if (status === 'completed' && userRole !== 'pet owner') {
        iziToast.show({
            theme: 'dark',
            overlay: true,
            displayMode: 'once',
            title: 'Hey',
            message: 'Do you want to receive <strong>feedback</strong> from user ?',
            position: 'center', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter
            progressBarColor: 'rgb(0, 255, 184)',
            buttons: [
                ['<button>Ok</button>', function (instance, toast) {
                        const sendFeedbackEle = document.createElement("div");

                        sendFeedbackEle.classList.add('form-field');
                        sendFeedbackEle.classList.add('feedback-field');

                        sendFeedbackEle.innerHTML = `
                        <label for="feedback"><strong>Send Feedback:</strong></label>
                        <div style="justify-content: flex-start">
                            <input type="checkbox" name="feedback" value="feedback" style="display: inline-block; width: auto" checked="checked"/>
                        </div>`;

                        document.querySelector('.container-left form').insertBefore(sendFeedbackEle, document.querySelector('.form-action'));

                        instance.hide({
                            transitionOut: 'fadeOutUp',
                        }, toast);

                    }], // true to focus
                ['<button>Close</button>', function (instance, toast) {
                        instance.hide({
                            transitionOut: 'fadeOutUp',
                        }, toast);
                    }]
            ],
        });
    } else if (document.querySelector('.feedback-field')) {
        document.querySelector('.feedback-field').remove();
    }

    if (status === 'cancelled' && userRole.value === 'pet owner') {

        const newEle = document.createElement('div');
        newEle.className = 'form-field';

        const label = document.createElement('label');
        label.setAttribute('for', 'cancellationNotes');
        label.innerHTML = 'Cancellation Notes:';

        const textarea = document.createElement('textarea');
        textarea.setAttribute('name', 'cancellationNotes');
        textarea.setAttribute('rows', '4');
        textarea.setAttribute('cols', '50');
        textarea.setAttribute('id', 'cancellationNotes');

        newEle.appendChild(label);
        newEle.appendChild(textarea);

        document.querySelector('form').insertBefore(newEle, document.querySelector('.fee-field'));

    } else if (status !== 'cancelled' && userRole.value === 'pet owner') {
        document.querySelector('.fee-field').previousElementSibling.remove();
    }
})

if (editOwner) {
    editOwner.addEventListener('click', function () {

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

                                        fetch(`http://localhost:3000/PRJ321_REST/pets/search/${document.querySelector('.container-left #owner').value}`, {
                                            method: 'GET', // *GET, POST, PUT, DELETE, etc.
                                            mode: 'cors', // no-cors, *cors, same-origin
                                            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                                            credentials: "include",
                                            headers: {
                                                "Content-Type": 'application/json'
                                            }
                                        })
                                                .then(response => {
                                                    return response.json();
                                                })
                                                .then(data => {

                                                    if (data.length > 0) {
                                                        let temp = ``;

                                                        data.map(pet => temp += `<option value=${pet.id}>${pet.name} (${pet.id})</option>`);

                                                        petSelector.innerHTML = temp;

                                                    } else {
                                                        setTimeout(() => {
                                                            iziToast.error({
                                                                title: 'error',
                                                                message: 'Can\'t Find Any Pet(s) Belong To User !',
                                                            });
                                                        }, 50);

                                                        petSelector.innerHTML = '';
                                                        petSelector.nextElementSibling.nextElementSibling.innerHTML = '';
                                                    }
                                                })
                                                .catch(err => {
                                                    console.log(err);
                                                    setTimeout(() => {
                                                        iziToast.error({
                                                            title: 'error',
                                                            message: 'There is some error when searching pets !',
                                                        });
                                                    }, 50);
                                                });
                                        instance.hide({transitionOut: 'fadeOut'}, toast, 'button');
                                    }, false], // true to focus
                                ['<button>NO</button>', function (instance, toast, button, e) {
                                        instance.hide({transitionOut: 'fadeOut'}, toast, 'button');
                                    }]
                            ],
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
}