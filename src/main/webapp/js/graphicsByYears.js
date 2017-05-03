
google.charts.load('current', {'packages':['corechart','line']});
google.charts.setOnLoadCallback(drawChart);



//        $('#loadingDiv')
//                .hide()  // Hide it initially
//                .ajaxStart(function() {
//                    $(this).show();
//                })
//                .ajaxStop(function() {
//                    $(this).hide();
//                })
//        ;

function drawChart() {
    draw();
}
function draw(){
    $.ajax({
        type: "POST",
        data: JSON.stringify({"station": getUrlParameter('id')}),
        dataType: "json",
        url: "rest/controller/meanTempByYears",
        success: function (json) {

            if (json.toString() == null)
                alert("No data found!")
            // var arr = [['Year', 'Temperature']];
            //arr.push(['2024', 200]);
            //arr.push(['1990', 233]);
            //arr.push(['1950', json.val]);



            var data = new google.visualization.DataTable();
            data.addColumn('number', 'Year');
            data.addColumn('number', 'Temperature');

            for (var i in json){
                var year = json[i].year;
                var val = parseFloat(json[i].val);
                data.addRow([year, val]);
                // arr.push([year, val]);
            }

            var options = {
                hAxis: {
                    title: 'Temp'
                },
                vAxis: {
                    title: 'Time'
                },
                chart: {
                    title: 'Mean temperature in '+getUrlParameter('id'),
                    subtitle: '2012',
                }
            };

            var chart = new google.visualization.LineChart(document.getElementById('graphicContainer'));

            chart.draw(data, options);

//                    var data = new google.visualization.DataTable();
//                    data.addColumn('number', 'X');
//                    data.addColumn('number', 'Dogs');
//
//                    data.addRows([
//                        [0, 0],   [1, 10],  [2, 23],  [3, 17],  [4, 18],  [5, 9],
//                        [6, 11],  [7, 27],  [8, 33],  [9, 40],  [10, 32], [11, 35],
//                        [12, 30], [13, 40], [14, 42], [15, 47], [16, 44], [17, 48],
//                        [18, 52], [19, 54], [20, 42], [21, 55], [22, 56], [23, 57],
//                        [24, 60], [25, 50], [26, 52], [27, 51], [28, 49], [29, 53],
//                        [30, 55], [31, 60], [32, 61], [33, 59], [34, 62], [35, 65],
//                        [36, 62], [37, 58], [38, 55], [39, 61], [40, 64], [41, 65],
//                        [42, 63], [43, 66], [44, 67], [45, 69], [46, 69], [47, 70],
//                        [48, 72], [49, 68], [50, 66], [51, 65], [52, 67], [53, 70],
//                        [54, 71], [55, 72], [56, 73], [57, 75], [58, 70], [59, 68],
//                        [60, 64], [61, 60], [62, 65], [63, 67], [64, 68], [65, 69],
//                        [66, 70], [67, 72], [9999, 75], [10000, 80]
//                    ]);
//
//                    var options = {
//                        hAxis: {
//                            title: 'Time'
//                        },
//                        vAxis: {
//                            title: 'Popularity'
//                        }
//                    };
//
//                    var chart = new google.visualization.LineChart(document.getElementById('columnchart_material'));
//
//                    chart.draw(data, options);
        }
        // beforeSend: function () { $("#loadingDiv").show(); },
        // complete: function () { $("#loadingDiv").hide(); }
    });


}