function validate() {
}

function submit() {
    validate();
    var fromFrom = $("#fromFrom").val();
    var fromTo = $("#fromTo").val();
    var toFrom = $("#toFrom").val();
    var toTo = $("#toTo").val();
    var region = $("#regionPicker").val();
    // alert(fromFrom);
    // alert(fromTo);
    // alert(toFrom);
    // alert(toTo);
    // alert(region);
    var data = [fromFrom, fromTo, toFrom, toTo, region];
    alert(data[3]);
    calculate(data);
    alert(regionsMap[region]);
    alert(region);
}
function printTable(arg) {
    var output = '';

    output += '<table id="resultsTable"><tr><td>Mean from</td><td>Mean to</td><td>Delta</td></tr></tr><tr><td>'  + arg.from + '</td><td>' + arg.to+ '</td><td>' + arg.avg+ '</td></tr></table>';

    return output;
}

function calculate(data) {

    $.ajax({
        type: "POST",
        data: JSON.stringify({
            "fromFrom": data[0],
            "fromTo": data[1],
            "toFrom": data[2],
            "toTo": data[3],
            "id": regionsMap[data[4]]
        }),
        dataType: "json",
        url: "rest/controller/tempDelta",
        success: function (json) {
            alert(json.from);
            alert(json.to);
            alert(json.avg);
            $('#tableContainer').append(printTable(json));
            // var arr = [['Month', 'Temperature']];
            //
            //
            // arr.push([1, 3]);
            // arr.push([2, 6]);
            // var data = google.visualization.arrayToDataTable(arr);
            // var formatNumer = new google.visualization.NumberFormat({pattern: 'decimal'});
            // formatNumer.format(data, 1);
            //
            // var options = {
            //     chart: {
            //         title: 'Mean temperature in Sochi',
            //         subtitle: '2012',
            //     }
            // };
            //
            // var chart = new google.charts.Bar(document.getElementById('graphicContainer'));
            //
            // chart.draw(data, options);
            // // var date = json.date;
            // // var mean = json.mean;
            // // alert("helllo");
            // // $("#graphicContainer").append("<p>dfdfd"+mean+"</p>")
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

