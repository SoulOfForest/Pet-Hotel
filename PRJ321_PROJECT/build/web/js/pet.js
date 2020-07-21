const searchTable = document.querySelector('.search-display table');
const checkCells = document.querySelectorAll('.search-display table > tbody tr input[type=checkbox]');
const loader = document.querySelector('.ui.segment');
const removeBtn = document.querySelector('#remove');
const userID = document.querySelector('#userID');
const userRole = document.querySelector('#userRole');
const userEmail = document.querySelector('#userEmail');

let checkedCells = 0;

document.querySelector('.reset-action ').addEventListener('click', function (e) {
    e.preventDefault();

    document.querySelector('.container.left #owner').value = '';
    document.querySelector('#petName').value = '';
    document.querySelector('#breed').value = '';
    document.querySelector('#periodTo').value = '';
})


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

const updateLogs = async (users, logURI) => {
    await fetch(`http://localhost:3000/PRJ321_REST/logs/pets/${logURI}/${userID.value}`, {
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

const removePets = async () => {
    const checkedRows = searchTable.querySelectorAll('tr.checked');
    const deletedPets = Array.from(checkedRows).map(row => {
        return row.querySelector('[data-label=petID] input').value;
    });

    loader.classList.add('active');

    await updateLogs(deletedPets, "remove");

    fetch('http://localhost:3000/PRJ321_REST/pets/delete', {
        method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        body: JSON.stringify(deletedPets),
        headers: {
            "Content-Type": 'application/json'
        }
    })
            .then(response => {
                return response.text();
            })
            .then(data => {
                console.log(data);
                if (data === 'booked') {
                    setTimeout(() => {
                        iziToast.error({
                            title: 'error',
                            message: 'There is a booking for this pet, So It can\'t be deleted !',
                        });
                    }, 50);
                } else if (data == 'success') {
                    setTimeout(() => {
                        iziToast.success({
                            title: 'OK',
                            timeout: 3000,
                            message: 'Pet(s) Remove Successfully ! The Page will reload in 3 seconds for changes to take effect',
                            onClosing: function () {
                                window.location.replace(window.location.href.split('?')[0]);
                            }
                        });
                    }, 50);
                } else {
                    setTimeout(() => {
                        iziToast.error({
                            title: 'error',
                            message: 'Pet(s) Can\'t Be Removed !!',
                        });
                    }, 50);
                }
            })
            .catch(err => {
                setTimeout(() => {
                    iziToast.success({
                        title: 'error',
                        message: 'Pet(s) Can\'t Be Removed !!',
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
        message: 'Are you sure ?',
        position: 'center', // bottomRight, bottomLeft, topRight, topLeft, topCenter, bottomCenter
        progressBarColor: 'rgb(0, 255, 184)',
        buttons: [
            ['<button>Ok</button>', function (instance, toast) {
                    instance.hide({
                        transitionOut: 'fadeOutUp',
                    }, toast);
                    removePets();
                }], // true to focus
            ['<button>Close</button>', function (instance, toast) {
                    instance.hide({
                        transitionOut: 'fadeOutUp',
                    }, toast);
                }]
        ],
    });
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
        Title: 'pests_Report',
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

        let BASE_URL = 'http://localhost:3000/PRJ321_REST/pets';

        if (userRole.value === 'pet owner') {
            BASE_URL += `/search/${userEmail.value}`;
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

        const pets = await response.json();

        console.log(pets);

        const header = ["Id", "Avatar", "Name", "Owner", "Breed", "Size", "Type", "Created At", "Updated At"];

        const worksheet_data = [header];

        for (let pet of pets) {
            const row_data = [];
            row_data.push(pet.id);
            if (pet.avatar) {
                row_data.push(pet.avatar);
            } else {
                row_data.push("");
            }
            row_data.push(pet.name);
            row_data.push(pet.owner);
            row_data.push(pet.breed);
            row_data.push(pet.size);
            row_data.push(pet.type);
            row_data.push(new Date(pet.createdAt).toLocaleString());

            if (pet.updatedAt) {
                row_data.push(new Date(pet.updatedAt).toLocaleString());
            } else {
                row_data.push("");
            }

            worksheet_data.push(row_data);
        }

        console.log(worksheet_data);

        await saveAs(new Blob([s2ab(createExcelSheet("Pet_Report Sheet", worksheet_data))], {type: "application/octet-stream"}), 'pet_report.xlsx');

    } catch (err) {
        iziToast.error({
            title: 'Error',
            message: 'Export Pets To Excel Failed!',
        });

        return;
    }

    iziToast.success({
        title: 'OK',
        message: 'Export Pets To Excel Successfully!',
    })

    $("#export-to-excel").removeClass("loading");

});