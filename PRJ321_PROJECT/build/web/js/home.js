const totalBookings = document.querySelector('.totalBookings .contents div.rate');
const totalUsers = document.querySelector('.totalUsers .contents div.rate');
const totalFees = document.querySelector('.totalFees .contents div.rate');
const bookingsByWeek = document.querySelector('.bookingsByWeek .contents div.rate');

const feeCtx = document.getElementById('feeChart').getContext('2d');
const usersCtx = document.getElementById('usersChart').getContext('2d');
const bookingCtx = document.getElementById('bookingsChart').getContext('2d');
const feeRealTimeCtx = document.getElementById('fee-realtime').getContext('2d');
const bookingStatusCtx = document.getElementById('bookingStatus').getContext('2d');

const bookingStatusChart = new Chart(bookingStatusCtx, {
    // The type of chart we want to create
    type: 'doughnut',
    // The data for our dataset
    data: {
        labels: [],
        datasets: [
           
        ]
    },
    // Configuration options go here
    options: {
       
        title: {
            display: true,
            text: 'Booking Status (By percent %)',
            fontSize: 25
        },
        legend: {

            labels: {
                fontColor: '#666',
                padding: 10,
            }
        },
        
    }
});


const feeRealTimeChart = new Chart(feeRealTimeCtx, {
    // The type of chart we want to create
    type: 'line',
    // The data for our dataset
    data: {
        labels: [],
        datasets: [
            {
                label: 'Fee',
                backgroundColor: '#d6d6d6',
                borderColor: 'purple',
                hoverBorderWidth: 5,
                lineTension: 0,
                fill: false,
                order: 1
            },
        ]
    },
    // Configuration options go here
    options: {
        animation: {
            duration: 0, // general animation time
        },
        elements: {
            line: {
                tension: 0, // disables bezier curves
            }
        },
        layout: {
            padding: {
                left: 50,
                right: 50,
                top: 0,
                bottom: 0
            }
        },
        title: {
            display: true,
            text: 'RealTime Fees In Last 30 Days',
            fontSize: 25
        },
        legend: {

            labels: {
                fontColor: '#666',
                padding: 10,
            }
        },
        scales: {
            yAxes: [{
                    ticks: {
                        // Include a dollar sign in the ticks
                        callback: function (value, index, values) {
                            return '$' + value;
                        },
                        padding: 10,
                    },
                }],
            xAxes: [{
                    ticks: {
                        padding: 10,
                        fontStyle: 'bold'
                    },
                }]
        },
        plugins: {
            datalabels: {
                offset: 20,

            }
        }
    }
});

const feeChart = new Chart(feeCtx, {
    // The type of chart we want to create
    type: 'line',
    // The data for our dataset
    data: {
        labels: [],
        datasets: [
            {
                label: 'Fee',
                backgroundColor: '#d6d6d6',
                borderColor: 'purple',
                hoverBorderWidth: 5,
                lineTension: 0,
                fill: false,
                order: 1
            },
        ]
    },
    // Configuration options go here
    options: {
        elements: {
            line: {
                tension: 0.4, // disables bezier curves
            }
        },
        layout: {
            padding: {
                left: 50,
                right: 50,
                top: 0,
                bottom: 0
            }
        },
        title: {
            display: true,
            text: 'Fees Per Month In Last 6 Months',
            fontSize: 25
        },
        legend: {

            labels: {
                fontColor: '#666',
                padding: 10,
            }
        },
        scales: {
            yAxes: [{
                    ticks: {
                        // Include a dollar sign in the ticks
                        callback: function (value, index, values) {
                            return '$' + value;
                        },
                        padding: 10,
                    },
                }],
            xAxes: [{
                    ticks: {
                        padding: 10,
                        fontStyle: 'bold'
                    },
                }]
        },
        plugins: {
            datalabels: {
                offset: 20,

            }
        }
    }
});
const usersChart = new Chart(usersCtx, {
    // The type of chart we want to create
    type: 'line',
    // The data for our dataset
    data: {
        labels: ['January 2018', 'February', 'March', 'April', 'May', 'June'],
        datasets: [
            {
                label: 'Users',
                backgroundColor: 'rgb(255, 99, 132)',
                borderColor: 'lightblue',
                hoverBorderWidth: 5,
                fill: false,
                data: [3, 4, 20, 30, 50, 0, 0]
            },
        ]


    },
    // Configuration options go here
    options: {
        layout: {
            padding: {
                left: 50,
                right: 50,
                top: 0,
                bottom: 0
            }
        },
        title: {
            display: true,
            text: 'Total Users In Last 6 Months',
            fontSize: 25
        },
        legend: {
            labels: {
                fontColor: '#666'
            }
        },
        scales: {
            yAxes: [{
                    ticks: {
                        padding: 10,
                    },
                }],
            xAxes: [{
                    ticks: {
                        padding: 10,
                        fontStyle: 'bold'
                    },
                }]
        },
    }
});

const bookingChart = new Chart(bookingCtx, {
    // The type of chart we want to create
    type: 'bar',
    // The data for our dataset
    data: {

    },
    // Configuration options go here
    options: {
        layout: {
            padding: {
                left: 50,
                right: 50,
                top: 0,
                bottom: 0
            }
        },
        title: {
            display: true,
            text: 'Bookings In Last 6 Months',
            fontSize: 25
        },
        legend: {
            labels: {
                fontColor: '#666'
            }
        },
        scales: {
            yAxes: [{
                    ticks: {
                        padding: 10,
                    },
                }],
            xAxes: [{
                    ticks: {
                        padding: 10,
                        fontStyle: 'bold'
                    },
                }]
        },
    }
})

const rateToPreviousMonth = (prev, now, property, ele, compared) => {
    const rate = Math.floor(Math.abs(now[property] - prev[property]) / prev[property] * 100);
    if (prev[property] < now[property]) {
        ele.innerHTML = `
            <p>
            <span>
        <?xml version="1.0" encoding="iso-8859-1"?>
<!-- Generator: Adobe Illustrator 19.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 512.008 512.008" style="enable-background:new 0 0 512.008 512.008;height:16;width:16;fill: #2dce89" xml:space="preserve">
<path d="M263.556,3.123c-4.165-4.164-10.917-4.164-15.083,0L45.807,205.79
	c-21.838,21.838-21.838,57.245,0,79.083s57.245,21.838,79.083,0l77.781-77.781v251.584c0,29.455,23.878,53.333,53.333,53.333
	c29.455,0,53.333-23.878,53.333-53.333V207.091l77.781,77.781c21.838,21.838,57.245,21.838,79.083,0s21.838-57.245,0-79.083
	L263.556,3.123z"/>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
</svg>
            </span>
            <span style="color: #2dce89;">${rate}%</span>
            <span style="color: #8898aa;margin-left: 5px;">Since Last ${compared}</span>
            </p>
        `;
    } else {
        ele.innerHTML = `
            <p>
            <span>
            <?xml version="1.0" encoding="iso-8859-1"?>
<!-- Generator: Adobe Illustrator 19.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 511.987 511.987" style="enable-background:new 0 0 511.987 511.987;height:16;width:16;fill: #f5365c;" xml:space="preserve">
<path d="M387.098,227.115l-77.781,77.803V53.333C309.316,23.878,285.438,0,255.983,0
	S202.65,23.878,202.65,53.333v251.584l-77.781-77.803c-21.838-21.838-57.245-21.838-79.083,0s-21.838,57.245,0,79.083
	l202.667,202.667c4.165,4.164,10.917,4.164,15.083,0l202.667-202.667c21.838-21.838,21.838-57.245,0-79.083s-57.245-21.838-79.083,0
	L387.098,227.115z"/>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
</svg>
            </span>
            <span style="color: #f5365c;">${rate}%</span>
            <span style="color: #8898aa;margin-left: 5px;">Since Last ${compared}</span>
            <p>
        `;
    }
}

const fetchFeeRealtime = async () => {
    const response = await fetch('http://localhost:3000/PRJ321_REST/bookings/report/days', {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        headers: {
            "Content-Type": 'application/json'
        }
    });


    const reports = await response.json();

    let last30Days = [];

    const today = new Date();
    let from = new Date();

    from.setDate(today.getDate() - 30);

    let labelDataSet = [];
    let feeData = [];
    let fees = 0;
    let index = 0;

    for (let d = from; d <= today; d.setDate(d.getDate() + 1)) {
        labelDataSet.push(d.getDate() + "-" + (d.getMonth() + 1));

        feeData.push(fees);

        for (let report of reports) {
            if (report.day == d.getDate() && report.month == (d.getMonth() + 1)) {
                fees += report.fee;
                feeData[index] = fees;
                break;
            }
        }

        index++;
    }

    const feeDataSets = {
        label: 'Fee',
        backgroundColor: 'lightblue',
        borderColor: 'rgb(0, 0, 0, .6)',
        hoverBorderWidth: 5,
        hoverBorderColor: 'black',
        fill: true,
        data: feeData
    }



    feeRealTimeChart.config.data.labels = labelDataSet;
    feeRealTimeChart.config.data.datasets[0] = feeDataSets;


    feeRealTimeChart.update();
}

const fetchBookingPercentByStatus = async () => {
    const response = await fetch('http://localhost:3000/PRJ321_REST/bookings/status', {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        headers: {
            "Content-Type": 'application/json'
        }
    });


    const reports = await response.json();
    
    console.log(reports);
    
    let labelDataSet = ["progress", "booked", "cancelled", "completed"];
    let bookingData = [];
    let bookings = 0;

    for (let i = 0; i < reports.length; i++) {
        bookings += reports[i].bookingsInMonth;    
    }
    
    for (let i = 0; i < labelDataSet.length; i++) {
        if (reports[i] && labelDataSet.indexOf(reports[i].status.toLowerCase()) > -1) {
            const rate = Math.round((reports[i].bookingsInMonth / bookings) * 100);
            
            bookingData.push({
                status: reports[i],
                rate
            })
            
        } else {
            bookingData.push({
                status: labelDataSet[i],
                rate: 0
            })
        }
    }
 
    const feeDataSets = {
        label: 'Fee',
        backgroundColor: [
            'rgb(0, 227, 150)',
            '#6794dc',
            '#ff6384',
            '#dc67ce'
        ],
        borderColor: '#fff',
        fill: true,
        data: bookingData.map(data => data.rate)
    }

    console.log(bookingData);

    bookingStatusChart.config.data.labels = bookingData.map(data => data.status.status);
    bookingStatusChart.config.data.datasets[0] = feeDataSets;


    bookingStatusChart.update();
}


const fetchBookingReport = async () => {
    const response = await fetch('http://localhost:3000/PRJ321_REST/bookings/report', {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        headers: {
            "Content-Type": 'application/json'
        }
    });

    const otherResponse = await fetch('http://localhost:3000/PRJ321_REST/bookings/report/weeks', {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        headers: {
            "Content-Type": 'application/json'
        }
    });

    const reports = await response.json();
    
    const reportsByWeeks = await otherResponse.json();
    
    let labelDataSet = [];
    let feeData = [];
    let bookingData = [];
    let bookings = 0;
    let fees = 0;

    let preivousMonth = {
        month: new Date().getMonth()
    }

    let currentMonth = {
        month: new Date().getMonth() + 1
    }
    
    let totalFeesPrev = {
        month: new Date().getMonth()
    }

    let totalFeesCurrent = {
        month: new Date().getMonth() + 1
    }

    let preivousWeek = {};

    let currentWeek = {};

    if (reportsByWeeks.length > 0) {
        currentWeek.week = reportsByWeeks[reportsByWeeks.length - 1].week;
        currentWeek.fee = reportsByWeeks[reportsByWeeks.length - 1].fee;

    }

    for (let report of reportsByWeeks) {
        if (report.week == currentWeek.week - 1) {
            preivousWeek.week = report.week;
            preivousWeek.fee = report.fee;
            break;
        }
    }

    for (let report of reports) {
        const date = report.month + "-" + report.year;
        labelDataSet.push(date);
        feeData.push(report.fee);
        bookingData.push(report.bookingsInMonth);

        bookings += report.bookingsInMonth;
        fees += report.fee;

        if (report.month == preivousMonth.month) {
            preivousMonth.fee = report.fee;
            totalFeesPrev. fee = fees;
            preivousMonth.booking = bookings;
        }

        if (report.month == currentMonth.month) {
            currentMonth.fee = report.fee;
            totalFeesCurrent.fee = fees;
            currentMonth.booking = bookings;
        }
    }

    // Set total bookings statistics
    rateToPreviousMonth(preivousMonth, currentMonth, "booking", totalBookings, "Month");
    rateToPreviousMonth(totalFeesPrev, totalFeesCurrent, "fee", totalFees, "Month");
    rateToPreviousMonth(preivousWeek, currentWeek, "fee", bookingsByWeek, "Week");

    document.querySelector('.bookingsByWeek .contents div.header p').innerHTML = `
    Total Fees By Week
    <strong style="display: block;margin-top: 5px;">$${currentWeek.fee}</strong>
    `;

    document.querySelector('.totalBookings .contents div.header p').innerHTML = `
    Total Bookings
    <strong style="display: block;margin-top: 5px;">${bookings}</strong>
    `;

    document.querySelector('.totalFees .contents div.header p').innerHTML = `
    Total Fees
    <strong style="display: block;margin-top: 5px;">$${fees}</strong>
    `;


    const feeDataSets = {
        label: 'Fee',
        backgroundColor: 'lightblue',
        borderColor: 'rgb(0, 0, 0, .6)',
        hoverBorderWidth: 5,
        hoverBorderColor: 'black',
        fill: true,
        data: feeData
    }

    const bookingDataSets = {
        label: 'Bookings',
        backgroundColor: 'rgb(125, 58, 192)',
        borderColor: 'rgb(0, 0, 0, .6)',
        hoverBorderWidth: 2,
        hoverBorderColor: 'black',
        fill: true,
        data: bookingData
    }

    feeChart.config.data.labels = labelDataSet;
    feeChart.config.data.datasets[0] = feeDataSets;

    bookingChart.config.data.labels = labelDataSet;
    bookingChart.config.data.datasets[0] = bookingDataSets;

    bookingChart.update();
    feeChart.update();
}

const fetchUsersReport = async () => {
    const response = await fetch('http://localhost:3000/PRJ321_REST/users/report', {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        headers: {
            "Content-Type": 'application/json'
        }
    });

    const reports = await response.json();

    let labelDataSet = [];
    let usersData = [];
    let users = 0;

    let preivousMonth = {
        month: new Date().getMonth()
    }

    let currentMonth = {
        month: new Date().getMonth() + 1
    }

    for (let report of reports) {
        const date = report.month + "-" + report.year;
        labelDataSet.push(date);
        if (usersData.length > 0) {
            usersData.push(report.numOfUsers + usersData[usersData.length - 1]);
        } else {
            usersData.push(report.numOfUsers);
        }

        users += report.numOfUsers;

        if (report.month == preivousMonth.month) {
            preivousMonth.numOfUsers = users;
        }

        if (report.month == currentMonth.month) {
            currentMonth.numOfUsers = users;
        }
    }


    rateToPreviousMonth(preivousMonth, currentMonth, "numOfUsers", totalUsers, "Month");

    document.querySelector('.totalUsers .contents div.header p').innerHTML = `
    New Users
    <strong style="display: block;margin-top: 5px;">${users}</strong>
    `;


    const userDataSets = {
        label: 'Users',
        backgroundColor: 'pink',
        tension: .4,
        borderColor: '#ff6384',
        hoverBorderWidth: 5,
        borderWidth: 2,
        hoverBorderColor: 'black',
        fill: true,
        data: usersData
    }

    usersChart.config.data.labels = labelDataSet;
    usersChart.config.data.datasets[0] = userDataSets;

    usersChart.update();
}

const fetchPageVisit = async () => {
    const response = await fetch('http://localhost:3000/PRJ321_REST/pageVisit', {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        headers: {
            "Content-Type": 'application/json'
        }
    });

    const pageVisits = await response.json();

    const currentDate = new Date();

    let previousVisit = {
        month: new Date().getMonth(),
        year: new Date().getYear() + 1900,
        visitor: []
    }

    let currentVisit = {
        month: new Date().getMonth() + 1,
        year: new Date().getYear() + 1900,
        visitor: []
    }

    const PAGES = ['/bookings', '/bookings/new', '/bookings/view', '/bookings/edit', '/pets', '/pets/new', '/pets/edit', '/pets/view', '/index', '/feedbacks'];

    let boundRate = [];
    
    console.log(pageVisits);

    for (let pageVisit of pageVisits) {
        if (pageVisit.month == previousVisit.month && pageVisit.year == previousVisit.year) {
            previousVisit.visitor.push({
                page: pageVisit.page,
                visit: pageVisit.visit
            });
        }

        if (pageVisit.month == currentVisit.month && pageVisit.year == currentVisit.year) {
            currentVisit.visitor.push({
                page: pageVisit.page,
                visit: pageVisit.visit
            })
        }

        if (PAGES.indexOf(pageVisit.page) < 0) {
            boundRate.push({
                page: pageVisit.page,
                rate: 0,
                status: 'static'
            })
        }
    }
    
    console.log(previousVisit, currentVisit);

    for (let i = 0; i < currentVisit.visitor.length; i++) {
        if (currentVisit.visitor[i].page) {     
            let current = currentVisit.visitor[i] ? currentVisit.visitor[i].visit : 0;
            let prev = 0;
            
            for (let j = 0; j < previousVisit.visitor.length; j++) {
                if (previousVisit.visitor[j] && previousVisit.visitor[j].page == currentVisit.visitor[i].page) {
                    prev = previousVisit.visitor[j] ? previousVisit.visitor[j].visit : 0;
                    break;
                }
            }
            
            let pageRate = Math.floor(Math.abs(current - prev) / prev * 100);

            if (prev == 0) {
                pageRate = 0;
                boundRate.push({
                    page: currentVisit.visitor[i].page || previousVisit.visitor[i].page,
                    rate: pageRate,
                    status: 'Static',
                    visit: current
                })
            } else {
                if (prev < current) {
                    boundRate.push({
                        page: currentVisit.visitor[i].page || previousVisit.visitor[i].page,
                        rate: pageRate,
                        status: 'Up',
                        visit: current
                    })
                } else if (prev > current) {
                    boundRate.push({
                        page: currentVisit.visitor[i].page || previousVisit.visitor[i].page,
                        rate: pageRate,
                        status: 'Down',
                        visit: current
                    })
                } else {
                    boundRate.push({
                        page: currentVisit.visitor[i].page || previousVisit.visitor[i].page,
                        rate: pageRate,
                        status: 'Static',
                        visit: current
                    })
                }
            }
        }
    }
    
    console.log(boundRate);
    
    let temp = '';

    for (let i = 0; i < boundRate.length; i++) {
        temp += `<tr>
                    <td>${boundRate[i].page}</td>
                    <td style="text-align: center">${boundRate[i].visit}</td>
                    <td style="text-align: center">`;
        
        if (boundRate[i].status == 'Down') {
            temp += `

                            <?xml version="1.0" encoding="iso-8859-1"?>
<!-- Generator: Adobe Illustrator 19.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 511.987 511.987" style="enable-background:new 0 0 511.987 511.987;height:16;width:16;fill: #f5365c !important;" xml:space="preserve">
<path d="M387.098,227.115l-77.781,77.803V53.333C309.316,23.878,285.438,0,255.983,0
	S202.65,23.878,202.65,53.333v251.584l-77.781-77.803c-21.838-21.838-57.245-21.838-79.083,0s-21.838,57.245,0,79.083
	l202.667,202.667c4.165,4.164,10.917,4.164,15.083,0l202.667-202.667c21.838-21.838,21.838-57.245,0-79.083s-57.245-21.838-79.083,0
	L387.098,227.115z"/>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
</svg>`
        } else if (boundRate[i].status == 'Up' || boundRate[i].status == 'Static') {
            temp += `
                <?xml version="1.0" encoding="iso-8859-1"?>
<!-- Generator: Adobe Illustrator 19.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 512.008 512.008" style="enable-background:new 0 0 512.008 512.008;height:16;width:16;fill: #2dce89 !important;" xml:space="preserve">
<path d="M263.556,3.123c-4.165-4.164-10.917-4.164-15.083,0L45.807,205.79
	c-21.838,21.838-21.838,57.245,0,79.083s57.245,21.838,79.083,0l77.781-77.781v251.584c0,29.455,23.878,53.333,53.333,53.333
	c29.455,0,53.333-23.878,53.333-53.333V207.091l77.781,77.781c21.838,21.838,57.245,21.838,79.083,0s21.838-57.245,0-79.083
	L263.556,3.123z"/>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
</svg>`
        }
        
        temp += `<span style="color: #8898aa;margin-left: 5px;">${boundRate[i].rate}%</span></td>`;
   
    }
    document.querySelector('.page-visit table tbody').innerHTML = temp;
}

const fetchBookingRecently = async () => {
    const response = await fetch('http://localhost:3000/PRJ321_REST/bookings/recently', {
        method: 'GET', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: "include",
        headers: {
            "Content-Type": 'application/json'
        }
    });

    const bookings = await response.json();

    let temp = '';

    const tableBody = document.querySelector('.booking-table tbody');

    for (let booking of bookings) {
        temp += `       <tr>
                        <td data-label="ID" data-tooltip="${booking.id}" data-position="top center">
                            ${booking.id.substring(0, 3)}...
                       </td>
                        <td data-label="PetOwner" style="color: var(--theme)">
                           <a href="/view?id=${booking.userID}" data-tooltip="${booking.userEmail}" data-position="top center">${booking.userName}</a>
                       </td>
                        <td data-label="Pet" style="color: var(--theme)">${booking.petName}</td>
                        <td data-label="Arrival">${new Date().toLocaleString(booking.arrival)}</td>
                        <td data-label="Departure">${new Date().toLocaleString(booking.departure)}</td>
                        <td data-label="Status">`;
        if (booking.status !== 'progress') {
            temp += `<span class="${booking.status}">${booking.status}</span></td>`;
        } else {
            temp += `<span class="${booking.status}">In Progress</span></td>`;
        }

        if (booking.extraServices) {
            temp += `<td data-label="extraServices">
                <?xml version="1.0" encoding="iso-8859-1"?>
<!-- Generator: Adobe Illustrator 19.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 426.667 426.667" style="enable-background:new 0 0 426.667 426.667;height:16;width:16;fill: green" xml:space="preserve">
<g>
	<g>
		<path d="M421.876,56.307c-6.548-6.78-17.352-6.968-24.132-0.42c-0.142,0.137-0.282,0.277-0.42,0.42L119.257,334.375
			l-90.334-90.334c-6.78-6.548-17.584-6.36-24.132,0.42c-6.388,6.614-6.388,17.099,0,23.713l102.4,102.4
			c6.665,6.663,17.468,6.663,24.132,0L421.456,80.44C428.236,73.891,428.424,63.087,421.876,56.307z"/>
	</g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
</svg>

            </td>`
        } else {
            temp += `<td data-label="extraServices">
                <?xml version="1.0" encoding="iso-8859-1"?>
<!-- Generator: Adobe Illustrator 19.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 512.001 512.001" style="enable-background:new 0 0 512.001 512.001;height:16;width:16;fill: red" xml:space="preserve">
<g>
	<g>
		<path d="M284.286,256.002L506.143,34.144c7.811-7.811,7.811-20.475,0-28.285c-7.811-7.81-20.475-7.811-28.285,0L256,227.717
			L34.143,5.859c-7.811-7.811-20.475-7.811-28.285,0c-7.81,7.811-7.811,20.475,0,28.285l221.857,221.857L5.858,477.859
			c-7.811,7.811-7.811,20.475,0,28.285c3.905,3.905,9.024,5.857,14.143,5.857c5.119,0,10.237-1.952,14.143-5.857L256,284.287
			l221.857,221.857c3.905,3.905,9.024,5.857,14.143,5.857s10.237-1.952,14.143-5.857c7.811-7.811,7.811-20.475,0-28.285
			L284.286,256.002z"/>
	</g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
</svg>

            </td>`
        }



        temp += `<td data-label="Fee">
                            $${booking.fee}
                        </td>
                        <td data-label="CreatedAt">
                            ${new Date().toLocaleString(booking.createdAt)}
                        </td>
                        <td data-label="Actions">
                            <a href="/bookings/view?id=${booking.id}" data-tooltip="View Booking" data-position="top center">
                               <?xml version="1.0" encoding="iso-8859-1"?>
<!-- Generator: Adobe Illustrator 19.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 511.999 511.999" style="enable-background:new 0 0 511.999 511.999;height: 16;width: 16" xml:space="preserve">
<g>
	<g>
		<path d="M508.745,246.041c-4.574-6.257-113.557-153.206-252.748-153.206S7.818,239.784,3.249,246.035
			c-4.332,5.936-4.332,13.987,0,19.923c4.569,6.257,113.557,153.206,252.748,153.206s248.174-146.95,252.748-153.201
			C513.083,260.028,513.083,251.971,508.745,246.041z M255.997,385.406c-102.529,0-191.33-97.533-217.617-129.418
			c26.253-31.913,114.868-129.395,217.617-129.395c102.524,0,191.319,97.516,217.617,129.418
			C447.361,287.923,358.746,385.406,255.997,385.406z"/>
	</g>
</g>
<g>
	<g>
		<path d="M255.997,154.725c-55.842,0-101.275,45.433-101.275,101.275s45.433,101.275,101.275,101.275
			s101.275-45.433,101.275-101.275S311.839,154.725,255.997,154.725z M255.997,323.516c-37.23,0-67.516-30.287-67.516-67.516
			s30.287-67.516,67.516-67.516s67.516,30.287,67.516,67.516S293.227,323.516,255.997,323.516z"/>
	</g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
</svg>

                           </a>
                            <a href="/bookings/edit?id=${booking.id}&owner=${booking.userID}" class="edit" data-tooltip="Edit Booking" data-position="top center">
                              <?xml version="1.0" encoding="iso-8859-1"?>
<!-- Generator: Adobe Illustrator 19.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->
<svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
	 viewBox="0 0 477.873 477.873" style="enable-background:new 0 0 477.873 477.873;height:16;width:16" xml:space="preserve">
<g>
	<g>
		<path d="M392.533,238.937c-9.426,0-17.067,7.641-17.067,17.067V426.67c0,9.426-7.641,17.067-17.067,17.067H51.2
			c-9.426,0-17.067-7.641-17.067-17.067V85.337c0-9.426,7.641-17.067,17.067-17.067H256c9.426,0,17.067-7.641,17.067-17.067
			S265.426,34.137,256,34.137H51.2C22.923,34.137,0,57.06,0,85.337V426.67c0,28.277,22.923,51.2,51.2,51.2h307.2
			c28.277,0,51.2-22.923,51.2-51.2V256.003C409.6,246.578,401.959,238.937,392.533,238.937z"/>
	</g>
</g>
<g>
	<g>
		<path d="M458.742,19.142c-12.254-12.256-28.875-19.14-46.206-19.138c-17.341-0.05-33.979,6.846-46.199,19.149L141.534,243.937
			c-1.865,1.879-3.272,4.163-4.113,6.673l-34.133,102.4c-2.979,8.943,1.856,18.607,10.799,21.585
			c1.735,0.578,3.552,0.873,5.38,0.875c1.832-0.003,3.653-0.297,5.393-0.87l102.4-34.133c2.515-0.84,4.8-2.254,6.673-4.13
			l224.802-224.802C484.25,86.023,484.253,44.657,458.742,19.142z M434.603,87.419L212.736,309.286l-66.287,22.135l22.067-66.202
			L390.468,43.353c12.202-12.178,31.967-12.158,44.145,0.044c5.817,5.829,9.095,13.72,9.12,21.955
			C443.754,73.631,440.467,81.575,434.603,87.419z"/>
	</g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
<g>
</g>
</svg>

                           </a>
                       </td></tr>`;

    }

    tableBody.innerHTML = temp;

}

fetchBookingPercentByStatus();
fetchBookingRecently();
fetchBookingReport();
fetchUsersReport();
fetchPageVisit();
fetchFeeRealtime();
setInterval(() => {
    fetchFeeRealtime();
}, 5000);