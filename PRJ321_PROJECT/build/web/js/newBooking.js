const submitBtn = document.querySelector('.container.left .search-action');
const emailInput = document.querySelector('.container.left #owner');
const roleInput = document.querySelector('.container.left select[name=role]');
const petSelector = document.querySelector('#owner-pets');
const saveBooking = document.querySelector('.search-action');
const resetBooking = document.querySelector('.reset-action');
const periodAt = document.querySelector('#periodAt');
const periodTo = document.querySelector('#periodTo');
const notes = document.querySelector('#notes');
const extraServices = document.querySelector('.form-field input[type=checkbox]');
const extraFee = document.querySelector('#extra');
const mediumFee = document.querySelector('#medium');
const smallFee = document.querySelector('#small');
const largeFee = document.querySelector('#large');

const date = new Date();

$('#periodAt').datetimepicker({

});

$('#periodTo').datetimepicker({

});

const handleTotalFeeBooking = (petSize, departure, arrival) => {
    const MILLS_IN_DAY = 1000 * 24 * 3600;

    const differentInTime = arrival.getTime() - departure.getTime();
    const differentInDays = differentInTime / MILLS_IN_DAY;

    const differentInWeeks = Math.floor(differentInDays / 7);

    const oddDays = differentInDays % 7;

    let totalFee = 0;

    if (departure.getTime() && arrival.getTime()) {
        switch (petSize) {
            case 'small)' :
            {
                totalFee += Math.round(smallFee.value * differentInDays);
                break;
            }
            case 'medium)':
            {
                totalFee += Math.round(mediumFee.value * differentInDays);
                break;
            }
            case 'large)' :
            {
                totalFee += Math.round(largeFee.value * differentInDays);
                break;
            }
        }
    }

    if (extraServices.checked) {
        totalFee += parseFloat(extraFee.value);
    }

    return totalFee;
}

resetBooking.addEventListener('click', (e) => {
    e.preventDefault();
    
    periodAt.value = '';
    periodTo.value = '';
    notes.value = '';
    extraServices.checked = false;
    
    console.log('clicked');
})

document.querySelector('.container.right form').addEventListener('submit', function (e) {
    e.preventDefault();

    const userBooking = {};

    if (petSelector[petSelector.selectedIndex]) {
        const pet = petSelector[petSelector.selectedIndex];

        const petSize = pet.innerText.split("(")[2];

        userBooking.pet = pet.innerText;
        userBooking.departure = periodAt.value;
        userBooking.arrival = periodTo.value;
        userBooking.notes = notes.value.trim();
        userBooking["Total Fee"] = `$${handleTotalFeeBooking(petSize, new Date(periodAt.value), new Date(periodTo.value))}`;
    }

    if (extraServices.checked) {
        userBooking.extraServices = "Yes";
    } else {
        userBooking.extraServices = "No";
    }
    

    const userBookingJSON = JSON.stringify(userBooking, null, 4);

    $.sweetModal.confirm('Booking Confirmation !', `<pre style="text-align: left;">${userBookingJSON}</pre>`, function () {
        
        socket.send(JSON.stringify({
            "owner": document.querySelector('.container.right #owner') ? document.querySelector('.container.right #owner').value : document.querySelector('#USER_EMAIL').value,
            "arrival": userBooking.arrival,
            "departure": userBooking.departure,
            "fee": userBooking["Total Fee"]
        }));
        
        document.querySelector('.container.right form').submit();
    });
    
})

if (submitBtn) {
    submitBtn.addEventListener('click', function () {

        fetch(`http://localhost:3000/PRJ321_REST/users/search`, {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "include",
            body: JSON.stringify({
                "owner": emailInput.value,
                "role": roleInput.options[roleInput.selectedIndex].value
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

                                        document.querySelector('.container.right #owner').value = inputs[0].options[inputs[0].selectedIndex].value;
                                        instance.hide({transitionOut: 'fadeOut'}, toast, 'button');

                                        fetch(`http://localhost:3000/PRJ321_REST/pets/search/${document.querySelector('.container.right #owner').value}`, {
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
                                                    console.log(data);
                                                    if (data.length > 0) {
                                                        let temp = ``;

                                                        data.map(pet => temp += `<option value=${pet.id}>${pet.name} (${pet.id}) (${pet.size})</option>`);

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
                                                    setTimeout(() => {
                                                        iziToast.error({
                                                            title: 'error',
                                                            message: 'There is some error when searching pets !',
                                                        });
                                                    }, 50);
                                                });
                                    }, false], // true to focus
                                ['<button>NO</button>', function (instance, toast, button, e) {
                                        instance.hide({transitionOut: 'fadeOut'}, toast, 'button');
                                    }]
                            ]
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

    })
} else {
    const currentUser = document.querySelector('#currentUser');
    const petSelector = document.querySelector('#owner-pets');

    petSelector.innerHTML = 'Loading ... !'

    fetch(`http://localhost:3000/PRJ321_REST/pets/search/${currentUser.value}`, {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        headers: {
            "Content-Type": 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded',
        }
    })
            .then(response => {
                return response.json();
            })
            .then(data => {
                let temp = ``;
                data.map(pet => temp += `<option value=${pet.id}>${pet.name} (${pet.id}) (${pet.size})</option>`);

                petSelector.innerHTML = temp;
            })
}