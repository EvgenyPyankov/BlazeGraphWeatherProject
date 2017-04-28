function sendRequest() {
    var data = new Object();
    data.content = $("#comment").val();
    $.ajax({
        url: 'rest/queries/query',
        type: 'POST',
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        success: function (str) {
            var response = JSON.parse(str);
            //TODO: Get something in response and show something
            $("#panel").empty();
            $("#panel").append(response.content);
            $("#panel").trigger("update");
        }
    });
}
function loadBindings() {
    $.ajax({
        dataType: "json",
        url: "rest/store/bindings",
        success: function (json) {
            $("#testsTable tbody").append(printBindings(json));
            $("#testsTable").trigger("update");
        }
    });
}

function printBindings(arg) {
    var output = '';
    for (var i in arg) {
        output += '<tr><td>' + '<a href="pass_binding.html?id=' + arg[i].id + '">' + arg[i].name + '</a></td><td>' + arg[i].author + '</td><td>' + arg[i].url + '</td></tr>';
    }
    return output;
}

function loadQuestions() {
    $.ajax({
        dataType: "json",
        url: "rest/bindings/binding?id=" + getParamValue('id'),
        success: function (json) {
            jsTest = json;
            $("#questionsPanel").append('<div class="panel panel-default"><div class="panel-heading">URLDFDFLDF</div><div class="panel-body">');
        }
    });
}
