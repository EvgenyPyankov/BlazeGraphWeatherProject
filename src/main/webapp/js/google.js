$(document).ready(function(){
    initMap();
});

function initMap() {
    getStations();
    var uluru = {lat: 79.50, lng: 76.98};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 4,
        //center: uluru
    });
    var marker = new google.maps.Marker({
        position: uluru,
        map: map
    });
    var marker2 = new google.maps.Marker({
        position: {lat:53.93, lng:102.05},
        map:map
    });
    marker.addListener('click', function() {
        map.setZoom(8);
        map.setCenter(marker.getPosition());
        infoWindow.open(map,marker);
    });
    var label = '<div>Спасибо, за Ваш выбор!</div>';
    var infoWindow = new google.maps.InfoWindow({
        content: label
    });
}

function getStations() {
    $.ajax({
        dataType: "json",
        url: "rest/controller/stations",
        success: function (json) {
            //var obj = JSON.parse(json);
            alert(Object.keys(json).length);
        }
    });
}
