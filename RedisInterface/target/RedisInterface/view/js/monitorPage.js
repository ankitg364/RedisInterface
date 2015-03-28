
/**
 * Created by asad on 9/9/14.
 */

var current_address;

window.addEventListener('beforeunload', function() {
    $.get("StopMonitorController.do", function(){

    });
    $.get("StopMonitor.do", function(){

    });
});


$('#softcoreMonitor').click(function() {
    $(this).find('.btn').toggleClass('active');

    if ($(this).find('.btn-primary').size()>0) {
        $(this).find('.btn').toggleClass('btn-primary');
    }

    $(this).find('.btn').toggleClass('btn-default');

    if( $(this).find('.btn-primary')[0].innerHTML == "ON")
        startSoftCoreMonitor();
    else
        stopSoftCoreMonitor();

});

$('#hardcoreMonitor').click(function(){

    $(this).find('.btn').toggleClass('active');

    if ($(this).find('.btn-danger').size()>0) {
        $(this).find('.btn').toggleClass('btn-danger');
    }

    $(this).find('.btn').toggleClass('btn-default');

    if( $(this).find('.btn-danger')[0].innerHTML == "ON")
        startHardCoreMonitor();
    else
        stopHardCoreMonitor();

});

function startHardCoreMonitor()
{
    $.get("StartMonitor.do", function () {

    });
}

function stopHardCoreMonitor()
{
    $.get("StopMonitor.do", function(){

    });
}

function startSoftCoreMonitor()
{
    $.get("StartMonitorController.do", function () {

    });
}

function stopSoftCoreMonitor()
{
    $.get("StopMonitorController.do", function(){

    });
}

$('#memory-placeholder-group > button.btn').click(function(){
    $(this).addClass("active").siblings().removeClass("active");

    var type = this.innerHTML;

    if(type.localeCompare("Last Hour")=="0")
        callMemoryGraphController("3600");

    else if(type.localeCompare("Last Day")=="0")
        callMemoryGraphController("86400");

    else if(type.localeCompare("Last Week")=="0")
        callMemoryGraphController("604800");

    else if(type.localeCompare("Last Month")=="0")
        callMemoryGraphController("2592000");

    else
        callMemoryGraphController("10800");
});

$('#command-placeholder-group > button.btn').click(function(){
    $(this).addClass("active").siblings().removeClass("active");

    var type = this.innerHTML;

    if(type.localeCompare("Last Hour")=="0")
        callCommandGraphController("3600");

    else if(type.localeCompare("Last Day")=="0")
        callCommandGraphController("86400");

    else if(type.localeCompare("Last Week")=="0")
        callCommandGraphController("604800");

    else if(type.localeCompare("Last Month")=="0")
        callCommandGraphController("2592000");

    else
        callCommandGraphController("10800");
});

$('#clients-placeholder-group > button.btn').click(function(){
    $(this).addClass("active").siblings().removeClass("active");

    var type = this.innerHTML;

    if(type.localeCompare("Last Hour")=="0")
        callClientsGraphController("3600");

    else if(type.localeCompare("Last Day")=="0")
        callClientsGraphController("86400");

    else if(type.localeCompare("Last Week")=="0")
        callClientsGraphController("604800");

    else if(type.localeCompare("Last Month")=="0")
        callClientsGraphController("2592000");

    else
        callClientsGraphController("10800");
});

$('#command-count-placeholder-group > button.btn').click(function(){
    $(this).addClass("active").siblings().removeClass("active");
    var type = this.innerHTML;

    if(type.localeCompare("Last Hour")=="0")
        callTopCommandsGraphController("3600");

    else if(type.localeCompare("Last Day")=="0")
        callTopCommandsGraphController("86400");

    else if(type.localeCompare("Last Week")=="0")
        callTopCommandsGraphController("604800");

    else if(type.localeCompare("Last Month")=="0")
        callTopCommandsGraphController("2592000");

    else
        callTopCommandsGraphController("10800");
});

$('#keys-count-placeholder-group > button.btn').click(function(){
    $(this).addClass("active").siblings().removeClass("active");

    var type = this.innerHTML;

    if(type.localeCompare("Last Hour")=="0")
        callTopKeysGraphController("3600");

    else if(type.localeCompare("Last Day")=="0")
        callTopKeysGraphController("86400");

    else if(type.localeCompare("Last Week")=="0")
        callTopKeysGraphController("604800");

    else if(type.localeCompare("Last Month")=="0")
        callTopKeysGraphController("2592000");

    else
        callTopKeysGraphController("10800");
});

function callClientsGraphController(timetype)
{
    $.get("ClientGraphData.do", {type: timetype}, function(responseJson){
        var xData=[];
        var yData=[];
        $.each(responseJson, function(x, y)
        {
            xData.push(x);
            yData.push(parseInt(y));
        });

        buildClientsGraph(xData, yData);
    });
}

function callCommandGraphController(timetype)
{
    $.get("CommandGraphData.do", {type: timetype}, function(responseJson){
        var xData=[];
        var yData=[];
        $.each(responseJson, function(x, y)
        {
            xData.push(x);
            yData.push(parseInt(y));
        });

        buildCommandGraph(xData, yData);
    });
}

function callMemoryGraphController(timetype)
{
    $.get("MemoryGraphData.do", {type: timetype}, function(responseJson){
        var xData=[];
        var yData=[];

        $.each(responseJson, function(x, y)
        {
            xData.push(x);
            yData.push(parseInt(y));
        });
        buildMemoryGraph(xData, yData);
    });
}

function callTopCommandsGraphController(timetype)
{
    $.get("TopCommandsGraphData.do", {type: timetype}, function(responseJson){
        var xData=[];
        var yData=[];

        $.each(responseJson, function(x, y)
        {
            xData.push(x);
            yData.push(parseInt(y));
        });

        buildTopCommandsGraph(xData, yData);
    });
}

function callTopKeysGraphController(timetype)
{
    $.get("TopKeysGraphData.do", {type: timetype}, function(responseJson){
        var xData=[];
        var yData=[];

        $.each(responseJson, function(x, y)
        {
            xData.push(x);
            yData.push(parseInt(y));
        });
        buildTopKeysGraph(xData, yData);
    });
}

$(document).ready(function(){
    current_address = document.getElementById('currentIP').innerHTML+"/"+
        document.getElementById('currentPort').innerHTML;


    // For general information.
    $.get("MonitorController.do",{address:current_address},function(responseJson){
        $.each(responseJson,function(item,value){
            document.getElementById(item).innerHTML=value;
        });
    });
    document.getElementById('used_memory:').innerHTML = document.URL;
    $("#memory-last-day").click();
    $("#command-last-day").click();
    $('#clients-last-day').click();
    $('#topcommands-last-day').click();
    $('#topkeys-last-day').click();

    loadSlowlogs();
});

function loadSlowlogs()
{
    var $table = document.getElementById('slowlogTable');
    $.get("SlowLogController.do", {}, function(responseJson){

        $.each(responseJson, function(index, obj){

            $('<tr>').appendTo($table)
                .append($('<td>').text(obj.timestamp))
                .append($('<td>').text(obj.execution_time))
                .append($('<td>').text(obj.command))
        });
    });
}
function buildMemoryGraph(xData, yData)
{
    var memoryData = {
        labels: xData,
        datasets: [
            {
                label: "My Memory dataset",
                fillColor: "rgba(151,187,205,0.2)",
                strokeColor: "rgba(151,187,205,1)",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: yData
            }
        ]
    };

    document.getElementById("memory-canvas-div").innerHTML="";
    var $canvasDiv = document.getElementById("memory-canvas-div");
    $('<canvas id="memoryChart" height="100" width="600">').appendTo($canvasDiv)

    var canvas = document.getElementById("memoryChart");
    var context = canvas.getContext('2d');

    window.myLine = new Chart(context).Line(memoryData, {
        responsive: true,
        scaleLabel: "<%=value%>"+"kB"
    });

}

function buildCommandGraph(xData, yData)
{
    var commandData = {
        labels: xData,
        datasets: [
            {
                label: "My Command dataset",
                fillColor: "rgba(151,187,205,0.2)",
                strokeColor: "rgba(151,187,205,1)",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: yData
            }
        ]
    };

    document.getElementById("command-canvas-div").innerHTML="";
    var $canvasDiv = document.getElementById("command-canvas-div");
    $('<canvas id="commandChart" height="100" width="600">').appendTo($canvasDiv)

    var canvas = document.getElementById("commandChart");
    var context = canvas.getContext('2d');

    window.myLine = new Chart(context).Line(commandData, {
        responsive: true
    });

}


function buildClientsGraph(xData, yData)
{
    var clientsData = {
        labels: xData,
        datasets: [
            {
                label: "My Clients dataset",
                fillColor: "rgba(151,187,205,0.2)",
                strokeColor: "rgba(151,187,205,1)",
                pointColor: "rgba(151,187,205,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(151,187,205,1)",
                data: yData
            }
        ]
    };

    document.getElementById("clients-canvas-div").innerHTML="";
    var $canvasDiv = document.getElementById("clients-canvas-div");
    $('<canvas id="clientsChart" height="100" width="600">').appendTo($canvasDiv)

    var canvas = document.getElementById("clientsChart");
    var context = canvas.getContext('2d');

    window.myLine = new Chart(context).Line(clientsData, {
        responsive: true
    });

}

function buildTopCommandsGraph(xdata,ydata)
{
    var barChartData = {
        labels: xdata,
        datasets: [
            {
                fillColor : "rgba(151,187,205,0.5)",
                strokeColor : "rgba(151,187,205,0.8)",
                highlightFill : "rgba(151,187,205,0.75)",
                highlightStroke : "rgba(151,187,205,1)",
                data : ydata
            }
        ]
    };


    document.getElementById("topcommands-canvas-div").innerHTML="";
    var $canvasDiv = document.getElementById("topcommands-canvas-div");
    $('<canvas id="commandCountChart" height="300" width="600">').appendTo($canvasDiv)

    var canvas = document.getElementById("commandCountChart");
    var context = canvas.getContext('2d');
    window.myBar = new Chart(context).Bar(barChartData, {
        responsive : true
    });
}

function buildTopKeysGraph(xdata,ydata)
{
    var barChartData = {
        labels: xdata,
        datasets: [
            {
                fillColor : "rgba(151,187,205,0.5)",
                strokeColor : "rgba(151,187,205,0.8)",
                highlightFill : "rgba(151,187,205,0.75)",
                highlightStroke : "rgba(151,187,205,1)",
                data : ydata
            }
        ]
    };

    document.getElementById("topkeys-canvas-div").innerHTML="";
    var $canvasDiv = document.getElementById("topkeys-canvas-div");
    $('<canvas id="keysCountChart" height="300" width="600">').appendTo($canvasDiv)

    var canvas = document.getElementById("keysCountChart");
    var context = canvas.getContext('2d');
    window.myBar = new Chart(context).Bar(barChartData, {
        responsive : true
    });
}


//------------------------------------------------------------------------------------
function rubbish()
{
    var pieData = [
        {
            value: 300,
            color:"#F7464A",
            highlight: "#FF5A5E",
            label: "Red"
        },
        {
            value: 50,
            color: "#46BFBD",
            highlight: "#5AD3D1",
            label: "Green"
        },
        {
            value: 100,
            color: "#FDB45C",
            highlight: "#FFC870",
            label: "Yellow"
        },
        {
            value: 40,
            color: "#949FB1",
            highlight: "#A8B3C5",
            label: "Grey"
        },
        {
            value: 120,
            color: "#4D5360",
            highlight: "#616774",
            label: "Dark Grey"
        }

    ];

    var ctx3 = document.getElementById("keysCountChart").getContext("2d");
    window.myPie = new Chart(ctx3).Pie(pieData);
}