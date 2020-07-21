const searchTable = document.querySelector('.search-display table');
const checkCells = document.querySelectorAll('.search-display table > tbody tr input[type=checkbox]');
const loader = document.querySelector('.ui.segment');
const removeBtn = document.querySelector('#remove');
const userID = document.querySelector('#userID');
const userRole = document.querySelector('#userRole');

let checkedCells = 0;

const handleServiceBtnsActive = (numOfEntites) => {
    if (numOfEntites > 0) {
        removeBtn.classList.remove('disabled');
    } else {
        removeBtn.classList.add('disabled');
    }
}

const resetTableHandler = () => {
    checkCells.forEach(cell => {
        cell.checked = false;
        checkedCells = 0;
    });

    document.querySelectorAll('.search-display table > tbody tr').forEach(ele => {
        ele.classList.remove('checked');
    })
    document.querySelector('#checkAll').checked = false;

    handleServiceBtnsActive(checkedCells);
}

searchTable.addEventListener('change', function (e) {
    if (e.target.checked && e.target.parentElement.nodeName !== 'TD') {
        checkCells.forEach(cell => {
            cell.checked = true
            checkedCells = checkCells.length;
        });

        document.querySelectorAll('.search-display table > tbody tr').forEach(ele => {
            ele.classList.add('checked');
        })
    } else if (e.target.checked && e.target.parentElement.nodeName === 'TD') {
        e.target.checked = true;
        e.target.parentElement.parentElement.classList.add('checked');
        checkedCells++;
    } else if (!e.target.checked && e.target.parentElement.nodeName === 'TD') {
        e.target.checked = false;
        e.target.parentElement.parentElement.classList.remove('checked');
        checkedCells--;
    } else {
        checkCells.forEach(cell => {
            cell.checked = false;
            checkedCells = 0;
        });

        document.querySelectorAll('.search-display table > tbody tr').forEach(ele => {
            ele.classList.remove('checked');
        })
    }
    ;

    handleServiceBtnsActive(checkedCells);
})

const removeFeedbacks = async () => {
    const checkedRows = searchTable.querySelectorAll('tr.checked');
    const deletedFeedbacks = Array.from(checkedRows).map(row => {
        return row.querySelector('[data-label=ID]').getAttribute('data-tooltip');
    });

    loader.classList.add('active');

    fetch('http://localhost:3000/PRJ321_REST/feedbacks/delete', {
        method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify({
            "user": userID.value,
            "feedbacks": deletedFeedbacks
        }),
        headers: {
            "Content-Type": 'application/json'
        }
    })
            .then(response => response.json())
            .then(data => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        timeout: 3000,
                        message: 'Feedback(s) Remove Successfully ! The Page will reload in 3 seconds for changes to take effect',
                        onClosing: function () {
                            window.location.replace(window.location.href.split('?')[0]);
                        }
                    });
                }, 50);
            })
            .catch(err => {
                setTimeout(() => {
                    iziToast.error({
                        title: 'Error',
                        message: 'Feedback(s) Can\'t Be Removed !!',
                    });
                }, 50);
            });

    loader.classList.remove('active');
    resetTableHandler();

}

if (removeBtn) {
    removeBtn.addEventListener('click', function () {
        iziToast.show({
            theme: 'dark',
            overlay: true,
            displayMode: 'once',
            title: 'Hey',
            message: 'Are you sure ?',
            position: 'center', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter
            progressBarColor: 'rgb(0, 255, 184)',
            buttons: [
                ['<button>Ok</button>', function (instance, toast) {
                        instance.hide({
                            transitionOut: 'fadeOutUp',
                        }, toast);
                        removeFeedbacks();
                    }], // true to focus
                ['<button>Close</button>', function (instance, toast) {
                        instance.hide({
                            transitionOut: 'fadeOutUp',
                        }, toast);
                    }]
            ],
        });
    });
}

window.addEventListener('DOMContentLoaded', function (e) {
    MicroModal.init({
        awaitCloseAnimation: true
    });

    if (document.querySelector('#seeFb')) {

        const takeFbEles = document.querySelectorAll('#seeFb');

        Array.from(takeFbEles).forEach((ele) => {
            ele.addEventListener('click', async function () {

                let bookingID = this.parentElement.parentElement.querySelector("td:nth-child(3)").getAttribute('data-tooltip');
                let feedbackID = this.parentElement.parentElement.firstElementChild.getAttribute('data-tooltip');

                if (userRole.value !== 'pet owner') {
                    bookingID = this.parentElement.parentElement.querySelector("td:nth-child(4)").getAttribute('data-tooltip');
                    feedbackID = this.parentElement.parentElement.querySelector("td:nth-child(2)").getAttribute('data-tooltip');
                }

                try {
                    const response = await fetch('http://localhost:3000/PRJ321_REST/feedbacks/' + feedbackID, {
                        method: 'GET', // *GET, POST, PUT, DELETE, etc.
                        mode: 'cors', // no-cors, *cors, same-origin
                        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                        credentials: "include",
                        headers: {
                            "Content-Type": 'application/json'
                        }
                    });

                    const data = await response.json();

                    console.log(data);
                    $.sweetModal(`< ${data.user} > Feedback !`, data.content);
                } catch (err) {
                    console.log(err);
                }
            })

        });
    }

    if (document.querySelector('#takeFb')) {

        const takeFbEles = document.querySelectorAll('#takeFb');

        Array.from(takeFbEles).forEach((ele) => {
            ele.addEventListener('click', async function () {
                let bookingID = this.parentElement.parentElement.querySelector("td:nth-child(3)").getAttribute('data-tooltip');
                let userID = this.parentElement.parentElement.querySelector("td:nth-child(2)").getAttribute('data-tooltip');
                let feedbackID = this.parentElement.parentElement.firstElementChild.getAttribute('data-tooltip');

                if (userRole === 'manager') {
                    bookingID = this.parentElement.parentElement.querySelector("td:nth-child(4)").getAttribute('data-tooltip');
                    feedbackID = this.parentElement.parentElement.querySelector("td:nth-child(2)").getAttribute('data-tooltip');
                }

                document.querySelector('.ui.segment').classList.add('active');

                try {
                    const response = await fetch('http://localhost:3000/PRJ321_REST/bookings/search/' + bookingID, {
                        method: 'GET', // *GET, POST, PUT, DELETE, etc.
                        mode: 'cors', // no-cors, *cors, same-origin
                        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                        credentials: "include",
                        headers: {
                            "Content-Type": 'application/json'
                        }
                    });

                    const data = await response.json();

                    const departure = new Date(data.departure);
                    const arrival = new Date(data.arrival);

                    document.querySelector('.modal__content ul').innerHTML = `
                        <li>Full Name: <strong style="margin-left: 10px;">${data.userName}</strong></li>
                                <li>                 
                                    User Email: <strong style="margin-left: 10px;">${data.userEmail}</strong>
                                </li>
                                <li>                 
                                    Pet: <strong style="margin-left: 10px;">${data.petName}</strong>
                               </li>
                              <li>Departure: <strong style="margin-left: 10px;">${departure.toLocaleString()}</strong>.</li>
                              <li>
                                Arrival: <strong style="margin-left: 10px;">${arrival.toLocaleString()}</strong>.
                                </li>
                              
                                <li>                 
                                 Total Fee: <strong style="margin-left: 10px;">${data.fee}$</strong>
                               </li>
                   `;


                    MicroModal.show('modal-1');

                    document.querySelector('.modal__footer .ui.primary.button').addEventListener('click', async function () {
                        let hasError = false;

                        try {
                            this.classList.add('loading');

                            const response = await fetch('http://localhost:3000/PRJ321_REST/feedbacks/' + feedbackID, {
                                method: 'POST', // *GET, POST, PUT, DELETE, etc.
                                mode: 'cors', // no-cors, *cors, same-origin
                                cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                                credentials: "include",
                                body: JSON.stringify({
                                    "content": document.querySelector('.user-feedback textarea').value,
                                    "user": userID
                                }),
                                headers: {
                                    "Content-Type": 'application/json'
                                }
                            });


                        } catch (err) {
                            hasError = true;
                        }

                        this.classList.remove('loading');
                        MicroModal.close('modal-1');


                        if (!hasError) {
                            setTimeout(() => {
                                iziToast.success({
                                    title: 'OK',
                                    timeout: 3000,
                                    message: 'Feedback Successfully ! The Page will reload in 3 seconds for changes to take effect',
                                    onClosing: function () {
                                        window.location.reload();
                                    }
                                });
                            }, 50);
                        } else {
                            setTimeout(() => {
                                iziToast.error({
                                    title: 'Error',
                                    timeout: 3000,
                                    message: 'There\'re some errors when send feedback ! Re-try',
                                });
                            }, 50);
                        }
                    });
                } catch (err) {
                    console.log(err);
                }

                document.querySelector('.ui.segment').classList.remove('active');
            })
        })

    }
});

function s2ab(s) {
    var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
    var view = new Uint8Array(buf);  //create uint8array as viewer
    for (var i = 0; i < s.length; i++)
        view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
    return buf;
}

const createExcelSheet = (sheetName, worksheet_data) => {
    const workBook = XLSX.utils.book_new();

    workBook.Props = {
        Title: 'feedbacks_report',
        Subject: 'Feedback Detail File',
        Author: 'ViruSs',
        CreatedDate: new Date()
    }

    workBook.SheetNames.push(sheetName);

    const worksheet = XLSX.utils.aoa_to_sheet(worksheet_data);

    workBook.Sheets[sheetName] = worksheet;

    return XLSX.write(workBook, {bookType: 'xlsx', type: 'binary'});
}


$("#export-to-excel").click(async () => {
    try {
        $("#export-to-excel").addClass("loading");

        let BASE_URL = 'http://localhost:3000/PRJ321_REST/feedbacks';

        if (userRole.value === 'pet owner') {
            BASE_URL += `/user/${userID.value}`;
        }
        
        console.log(BASE_URL);

        const response = await fetch(BASE_URL, {
            method: 'GET', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "include",
            headers: {
                "Content-Type": 'application/json'
            }
        });

        const feedbacks = await response.json();

        console.log(feedbacks);

        const header = ["ID", "User", "UserID", "BookingID", "Content", "Status", "Created At", "Published At"];

        const worksheet_data = [header];

        for (let feedback of feedbacks) {
            const row_data = [];
            row_data.push(feedback.id);
            row_data.push(feedback.user);
            row_data.push(feedback.userID);
            row_data.push(feedback.bookingID);
            row_data.push(feedback.content);
            row_data.push(feedback.status);       
            row_data.push(new Date(feedback.createdAt).toLocaleString());

            if (feedback.publishedAt) {
                row_data.push(new Date(feedback.publishedAt).toLocaleString());
            } else {
                row_data.push("");
            }

            worksheet_data.push(row_data);
        }

        console.log(worksheet_data);

        await saveAs(new Blob([s2ab(createExcelSheet("Feedback_Report Sheet", worksheet_data))], {type: "application/octet-stream"}), 'feedback.xlsx');

    } catch (err) {
        console.log(err);
        iziToast.error({
            title: 'Error',
            message: 'Export Feedbacks To Excel Failed!',
        });

        return;
    }

    iziToast.success({
        title: 'OK',
        message: 'Export Feedbacks To Excel Successfully!',
    })

    $("#export-to-excel").removeClass("loading");

});

$('#createdAt').datetimepicker();
$('#createdTo').datetimepicker();
$('#publishedTo').datetimepicker();
$('#publishedAt').datetimepicker();