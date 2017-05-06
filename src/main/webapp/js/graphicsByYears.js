google.charts.load('current', {'packages': ['corechart', 'line', 'bar']});
google.charts.setOnLoadCallback(drawChart);

$("#datePicker").submit(function (event) {
    alert("Handler for .submit() called.");
    event.preventDefault();
});

function submitYear(val) {

    var year = $("#yearInput").val()
    alert(year);
    event.preventDefault();
    drawYear(year);
}

function submitMonth(val) {

    var month = $("#monthInput").val()
    alert(month);
    event.preventDefault();
}

function submitDate(val) {

    var date = $("#dateInput").val()
    alert(date);
    event.preventDefault();


}
function addDatePicker(option) {
     $('#datePickerContainer').empty();
    switch (option) {
        case 'year':
            $('#datePickerContainer').append("<form id='yearPicker'>" +
                "Choose year:" +
                "</br>" +
                "<input type='number' id='yearInput' min='1880' max='2017'>" +
                "</br>" +
                // "<input type='submit'>" +
                "<button onclick='submitYear()'>Submit</button>" +
                "</form>"
            );
            break;
        case 'month':
            $("#datePickerContainer").append(" <form id='monthPicker'>" +
                "Choose month:" +
                "</br>" +
                "<input type='month' id='monthInput'>" +
                "</br>" +
                "<button onclick='submitMonth()'>Submit</button>" +
                "</form>");
            break;
        case 'day':
            $("#datePickerContainer").append("<form id='datePicker'>" +
                "Choose day:" +
                " </br>" +
                "<input type='date' id='dateInput' data-date='' data-date-format='YYYY MMMM DD' >" +
                "</br>" +
                "<button onclick='submitDate()'>Submit</button>" +
                " </form>")
            break;
        default:
            alert("No such option for addDatePicker!")
    }

}

function processSelectEvent(val) {
    $('#graphicContainer').empty();
    switch (val) {
        case 'All time':
            $("#datePickerContainer").empty();
            drawAllTime();
            break;
        case 'Year':
            addDatePicker("year");
            break;
        case 'Month':
            addDatePicker("month");
            break;
        case 'Day':
            addDatePicker("day");
            break;
        default:
            alert('No such option for processSelectEvent!');
    }

}

function drawMonth(){

}

function drawDay(){

}

function drawDay(date){
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "station": getUrlParameter('id'),
            "date": date
        }),
        dataType: "json",
        url: "rest/controller/dayMeasure",
        success: function (json) {
            alert(json.mean)
        }
    });
}

function drawYear(year) {
    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "station": getUrlParameter('id'),
            "year": year
        }),
        dataType: "json",
        url: "rest/controller/meanYearTempByMonths",
        success: function (json) {


            var arr = [['Month', 'Temperature']];


            for (var i in json) {
                var month = json[i].month;
                var val = parseFloat(json[i].val);
                arr.push([month, val]);
            }

            var data = google.visualization.arrayToDataTable(arr);
            var formatNumer = new google.visualization.NumberFormat({pattern: 'decimal'});
            formatNumer.format(data, 1);

            var options = {
                chart: {
                    title: 'Mean temperature in Sochi',
                    subtitle: '2012',
                }
            };

            var chart = new google.charts.Bar(document.getElementById('graphicContainer'));

            chart.draw(data, options);
        }
    });
}

function drawChart() {
    drawAllTime();
}
function drawAllTime() {
    $.ajax({
        type: "POST",
        data: JSON.stringify({"station": getUrlParameter('id')}),
        dataType: "json",
        url: "rest/controller/meanTempByYears",
        success: function (json) {

            if (json.toString() == null)
                alert("No data found!")

            var data = new google.visualization.DataTable();
            data.addColumn('number', 'Year');
            data.addColumn('number', 'Temperature');

            for (var i in json) {
                var year = json[i].year;
                var val = parseFloat(json[i].val);
                data.addRow([year, val]);
            }

            var options = {
                hAxis: {
                    title: 'Temp'
                },
                vAxis: {
                    title: 'Time'
                },
                chart: {
                    title: 'Mean temperature in ' + getUrlParameter('id'),
                    subtitle: '2012',
                }
            };

            var chart = new google.visualization.LineChart(document.getElementById('graphicContainer'));

            chart.draw(data, options);


        }
    });


}
