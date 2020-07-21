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

if (searchTable) {
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

}

document.querySelector('.reset-action ').addEventListener('click', function (e) {
    e.preventDefault();

    document.querySelector('.container.left #owner').value = '';
    document.querySelector('#petName').value = '';
    document.querySelector('#breed').value = '';
    document.querySelector('#periodTo').value = '';
})

const updateLogs = async (bookings, logURI) => {
    await fetch(`http://localhost:3000/PRJ321_REST/logs/bookings/${logURI}/${userID.value}`, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify(bookings)
    })
            .then(response => {
                return response.json();
            })
            .catch(err => {
                setTimeout(() => {
                    iziToast.error({
                        title: 'Error',
                        message: 'Log(s) Can\'t Be Updated !!',
                    });
                }, 50);
            });
}

const removeBookings = async () => {
    const checkedRows = searchTable.querySelectorAll('tr.checked');
    const deletedBookings = Array.from(checkedRows).map(row => {
        return row.querySelector('[data-label=ID]').getAttribute('data-tooltip');
    });

    loader.classList.add('active');

    await updateLogs(deletedBookings, "remove");

    fetch('http://localhost:3000/PRJ321_REST/bookings/delete', {
        method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify(deletedBookings),
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
                        message: 'Booking(s) Remove Successfully ! The Page will reload in 3 seconds for changes to take effect',
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
                        message: 'Booking(s) Can\'t Be Removed !!',
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
                        removeBookings();
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
        Title: 'bookings_Report',
        Subject: 'Booking Detail File',
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
        
        let BASE_URL = 'http://localhost:3000/PRJ321_REST/bookings';
        
        if (userRole.value === 'pet owner') {
            BASE_URL += `/${userID.value}`;
        }
        
        

        const response = await fetch(BASE_URL, {
            method: 'GET', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "include",
            headers: {
                "Content-Type": 'application/json'
            }
        });

        const bookings = await response.json();

        console.log(bookings);

        const header = ["Id", "Owner", "Pet", "Arrival", "Departure", "Notes", "Employee Notes", "Status", "Cancellation Notes", "Total Fee", "Receipt", "Extra Services", "Created At", "Updated At"];

        const worksheet_data = [header];

        for (let booking of bookings) {
            const row_data = [];
            row_data.push(booking.id);
            row_data.push(booking.userID);
            row_data.push(booking.petId);
            row_data.push(new Date(booking.arrival).toLocaleString());
            row_data.push(new Date(booking.departure).toLocaleString());
            row_data.push(booking.ownerNotes.trim());
            row_data.push(booking.managerNotes.trim());
            row_data.push(booking.status);
            row_data.push(booking.cancelNotes.trim());           
            
            row_data.push(`$${booking.fee}`);
            
            if (booking.receipt) {
                row_data.push(`${booking.receipt}`);
            } else {
                row_data.push("");
            }
            
            if (booking.extraServices) {
                row_data.push("Yes");
            } else {
                row_data.push("No");
            }
            
            row_data.push(new Date(booking.createdAt).toLocaleString());

            if (booking.updatedAt) {
                row_data.push(new Date(booking.updatedAt).toLocaleString());
            } else {
                row_data.push("");
            }

            worksheet_data.push(row_data);
        }

        console.log(worksheet_data);

        await saveAs(new Blob([s2ab(createExcelSheet("Booking_Report Sheet", worksheet_data))], {type: "application/octet-stream"}), 'booking.xlsx');

    } catch (err) {
        iziToast.error({
            title: 'Error',
            message: 'Export Bookings To Excel Failed!',
        });
        
        return ;
    }
    
    iziToast.success({
        title: 'OK',
        message: 'Export Bookings To Excel Successfully!',
    })

    $("#export-to-excel").removeClass("loading");
    
});

$('#createdAt').datetimepicker();
$('#createdTo').datetimepicker();
$('#periodTo').datetimepicker();
$('#periodAt').datetimepicker();
