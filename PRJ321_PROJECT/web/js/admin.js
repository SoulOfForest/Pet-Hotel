const roleButtons = document.querySelectorAll('.view-util button');
const searchTable = document.querySelector('.search-display table');
const checkCells = document.querySelectorAll('.search-display table > tbody tr input');
const serviceButtons = document.querySelectorAll('.ui.utilities > .ui.button.disabled');
const rightContainer = document.querySelector('.container.right');
const leftContainer = document.querySelector('.container.left');
const disableBtn = document.querySelector('#disable');
const enableBtn = document.querySelector('#enable');
const removeBtn = document.querySelector('#remove');
const loader = document.querySelector('.ui.segment');
const userID = document.querySelector('#userID');


$('#createdAt').datetimepicker();
$('#createdTo').datetimepicker();

let checkedCells = 0;

const handleServiceBtnsActive = (numOfEntites) => {
    if (numOfEntites > 0) {
        serviceButtons.forEach(button => button.classList.remove('disabled'))
    } else {
        serviceButtons.forEach(button => button.classList.add('disabled'))
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

const updateLogs = async (users, logURI) => {
    await fetch(`http://localhost:3000/PRJ321_REST/logs/${logURI}/${userID.value}`, {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify(users)
    })
            .then(response => {
                return response.json();
            })
            .catch(err => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'error',
                        message: 'Log(s) Can\'t Be Updated !!',
                    });
                }, 50);
            });
}

const removeUsers = async () => {
    const checkedRows = searchTable.querySelectorAll('tr.checked');
    const disabledUsers = Array.from(checkedRows).map(row => {
        return row.querySelector('[data-label=Email]').innerText;
    });

    loader.classList.add('active');

    await updateLogs(disabledUsers, "remove");

    fetch('http://localhost:3000/PRJ321_REST/users/delete', {
        method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify(disabledUsers),
        headers: {
            "Content-Type": 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded',
        }
    })
            .then(response => response.json())
            .then(data => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        timeout: 3000,
                        message: 'User(s) Remove Successfully ! The Page will reload in 3 seconds for changes to take effect',
                        onClosing: function () {
                            window.location.replace(window.location.href.split('?')[0]);
                        }
                    });
                }, 50);

                Array.from(checkedRows).forEach(row => {
                    row.remove();
                });

            })
            .catch(err => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'error',
                        message: 'User(s) Can\'t Be Removed !!',
                    });
                }, 50);
            });

    loader.classList.remove('active');
    resetTableHandler();

}

removeBtn.addEventListener('click', function () {
    iziToast.show({
        theme: 'dark',
        overlay: true,
        displayMode: 'once',
        title: 'Hey',
        message: 'Are you sure ? All Data About User Will Be Deleted (Include Booking, Pet)',
        position: 'center', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter
        progressBarColor: 'rgb(0, 255, 184)',
        buttons: [
            ['<button>Ok</button>', function (instance, toast) {
                    instance.hide({
                        transitionOut: 'fadeOutUp',
                    }, toast);
                    removeUsers();
                }], // true to focus
            ['<button>Close</button>', function (instance, toast) {
                    instance.hide({
                        transitionOut: 'fadeOutUp',
                    }, toast);
                }]
        ],
    });


});

enableBtn.addEventListener('click', function () {
    const checkedRows = searchTable.querySelectorAll('tr.checked');
    const disabledUsers = Array.from(checkedRows).map(row => {
        return row.querySelector('[data-label=Email]').innerText;
    });

    loader.classList.add('active');

    fetch('http://localhost:3000/PRJ321_REST/users/enable', {
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify(disabledUsers),
        headers: {
            "Content-Type": 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded',
        }
    })
            .then(response => response.json())
            .then(data => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        message: 'User(s) Enable Successfully !',
                    });
                }, 50);

                Array.from(checkedRows).forEach(row => {
                    const ele = row.querySelector('[data-label=Status] span');
                    ele.className = 'enabled';
                    ele.innerText = 'enabled';
                });

            })
            .catch(err => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'error',
                        message: 'User(s) Can\'t Be Enabled !!',
                    });
                }, 50);
            });

    updateLogs(disabledUsers, "enable");

    loader.classList.remove('active');
    resetTableHandler();

})

disableBtn.addEventListener('click', function (e) {
    const checkedRows = searchTable.querySelectorAll('tr.checked');
    const disabledUsers = Array.from(checkedRows).map(row => {
        return row.querySelector('[data-label=Email]').innerText;
    });

    loader.classList.add('active');

    fetch('http://localhost:3000/PRJ321_REST/users/disable', {
        method: 'PUT', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify(disabledUsers),
        headers: {
            "Content-Type": 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded',
        }
    })
            .then(response => response.json())
            .then(data => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        message: 'User(s) Disabled Successfully !',
                    });
                }, 50);

                Array.from(checkedRows).forEach(row => {
                    const ele = row.querySelector('[data-label=Status] span');
                    ele.className = 'disabled';
                    ele.innerText = 'disabled';
                });
            })
            .catch(err => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'error',
                        message: 'User(s) Can\'t Be Disabed !!',
                    });
                }, 50);
            });

    updateLogs(disabledUsers, "disable");

    loader.classList.remove('active');
    resetTableHandler();
})

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

        handleServiceBtnsActive(checkedCells);
    })
}

roleButtons.forEach(button => {
    button.addEventListener('click', function (e) {
        for (roleBtn of roleButtons) {
            roleBtn.classList.remove('enabled');
        }
        if (button.innerText === 'Roles') {
            leftContainer.innerHTML = `
                    <div class="form-field">
                        <label for="role">Role: </label>
                        <select class="ui dropdown selection" name="role">
            <option value="manager">Manager</option>
            <option value="employee">Employee</option>
            <option value="pet Owner">Pet Owner</option>
                        </select>
                    </div>       
                `;
            rightContainer.innerHTML = ``;

            $('.ui.dropdown').dropdown({
                clearable: true
            })
        } else {
            rightContainer.innerHTML = `
                
                    <div class="form-field">
                        <label for="createdAt">Create At: </label>
                        <div>
                            <input id="createdAt" type="text" name="createdAt" placeholder="Start date">
                            ~
                            <input id="createdTo" type="text" name="createdTo" placeholder="End date">
                        </div>
                    </div>
                    <div class="form-field">
                        <label for="name">Name: </label>
                        <input type="text" name="name" id="name"/>
                    </div>
                    <div class="form-field">
                        <label for="role">Role: </label>
                        <select class="ui dropdown selection" name="role">
                            <option value="manager">Manager</option>
                            <option value="employee">Employee</option>
                            <option value="petOwner">Pet Owner</option>
                        </select>
                    </div>      `;

            leftContainer.innerHTML = `

                    <div class="form-field">
                        <label for="userID">ID: </label>
                        <input type="text" name="id" id="userID"/>
                    </div>
                    <div class="form-field">
                        <label for="email">Email: </label>
                        <input type="email" name="email" id="email"/>
                    </div>
                    <div class="form-field">
                        <label for="status">Status: </label>
                        <select class="ui dropdown selection" name="status">
                            <option value="enabled">Enabled</option>
                            <option value="disabled">Disabled</option>
                        </select>
                    </div>
                `;

            $('.ui.dropdown').dropdown({
                clearable: true
            })
        }
        button.classList.add('enabled');
    })
});

document.querySelector('.reset-action ').addEventListener('click', function (e) {
    e.preventDefault();
 
    document.querySelector('.container.left #userID').value = '';
    document.querySelector('#email').value = '';
    document.querySelector('#name').value = '';
    document.querySelector('#createdAt').value = '';
    document.querySelector('#createdTo').value = '';
})

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
        Title: sheetName,
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

        const response = await fetch('http://localhost:3000/PRJ321_REST/users', {
            method: 'GET', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "include",
            headers: {
                "Content-Type": 'application/json'
            }
        });

        const users = await response.json();

        console.log(users);

        const header = ["Id", "Email", "First Name", "Last Name", "Gender", "Avatar", "Role", "Status", "Created At", "Updated At"];

        const worksheet_data = [header];

        for (let user of users) {
            const row_data = [];
            row_data.push(user.userID);
            row_data.push(user.email);
            row_data.push(user.firstName);
            row_data.push(user.lastName);
            row_data.push(user.gender);
            if (user.avatar) {
                row_data.push(user.avatar);
            } else {
                row_data.push("");
            }
            row_data.push(user.role);
            row_data.push(user.status);
            row_data.push(new Date(user.createdAt).toLocaleString());

            if (user.updatedAt) {
                row_data.push(new Date(user.updatedAt).toLocaleString());
            } else {
                row_data.push("");
            }

            worksheet_data.push(row_data);
        }

        console.log(worksheet_data);

        await saveAs(new Blob([s2ab(createExcelSheet("User_Report Sheet", worksheet_data))], {type: "application/octet-stream"}), 'user_report.xlsx');

    } catch (err) {
        iziToast.error({
            title: 'Error',
            message: 'Export Users To Excel Failed!',
        });

        return;
    }

    iziToast.success({
        title: 'OK',
        message: 'Export Users To Excel Successfully!',
    })

    $("#export-to-excel").removeClass("loading");

});