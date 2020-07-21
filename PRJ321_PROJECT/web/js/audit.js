$('#periodTo').datetimepicker();
$('#periodAt').datetimepicker();

document.querySelector('.reset-action ').addEventListener('click', function (e) {
    e.preventDefault();
 
    document.querySelector('#email').value = '';
    document.querySelector('#entityID').value = '';
    document.querySelector('#periodAt').value = '';
    document.querySelector('#periodTo').value = '';
})


window.addEventListener("DOMContentLoaded", function() {
    const viewLogs = document.querySelectorAll('td[data-label=Actions] a');
    
    Array.from(viewLogs).forEach(view => {
        view.addEventListener('click', function(e) {
            e.preventDefault();
            
            const parsedJson = JSON.parse(this.nextElementSibling.innerHTML);
            
            console.log(JSON.parse(this.nextElementSibling.innerHTML));
            console.log(JSON.stringify(parsedJson, null, 4));
            $.sweetModal('Audit Log', `<pre>${JSON.stringify(parsedJson, null, 4)}</pre>`);
        })
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

        const response = await fetch('http://localhost:3000/PRJ321_REST/logs', {
            method: 'GET', // *GET, POST, PUT, DELETE, etc.
            mode: 'cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: "include",
            headers: {
                "Content-Type": 'application/json'
            }
        });

        const logs = await response.json();

        console.log(logs);

        const header = ["Id", "User Email", "Entity", "Action", "Entity ID", "Content", "Date"];
//
        const worksheet_data = [header];

        for (let log of logs) {
            const row_data = [];
            row_data.push(log.id);
            row_data.push(log.userEmail);
            row_data.push(log.entity);
            row_data.push(log.action);
            row_data.push(log.entityID);
            
            row_data.push(log.content);
            
            row_data.push(new Date(log.createdAt).toLocaleString());
            worksheet_data.push(row_data);
        }

        console.log(worksheet_data);

        await saveAs(new Blob([s2ab(createExcelSheet("Log_Report Sheet", worksheet_data))], {type: "application/octet-stream"}), 'log_report.xlsx');

    } catch (err) {
        iziToast.error({
            title: 'Error',
            message: 'Export Logs To Excel Failed!',
        });
        
        return ;
    }
    
    iziToast.success({
        title: 'OK',
        message: 'Export Logs To Excel Successfully!',
    })

    $("#export-to-excel").removeClass("loading");
    
});