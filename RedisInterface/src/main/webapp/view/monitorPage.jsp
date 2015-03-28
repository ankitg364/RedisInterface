<%--
  Created by IntelliJ IDEA.
  User: asad
  Date: 9/9/14
  Time: 12:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>


<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Redis Admin Interface</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet">

</head>

<body>

<!-- NavBar -->
<div class="navbar navbar-inverse navbar-fixed-top">
    <a href="#" class="navbar-brand" style="color: white; vertical-align: middle">  Redis Live Monitor</a>
    <p class="navbar-brand" style="color: #ffffff; vertical-align: middle; float: right" id="currentPort">
        <%= request.getSession().getAttribute("CurrentPort")%>
    </p>
    <p class="navbar-brand" style="color: #ffffff; vertical-align: middle; float: right" id="currentIP">
        <%= request.getSession().getAttribute("CurrentIP")%>
    </p>

    <div class="btn-group btn-toggle" id="hardcoreMonitor" style="float: right; margin: 6px; margin-right: 10px; color:"
         data-toggle="tooltip" data-placement="left" title="Warning: Enabling this will cost your redis server
              50% efficiency cut but will allow you to get the top commands and top key">
        <button class="btn btn-default">ON</button>
        <button class="btn btn-danger active">OFF</button>
        <div style="bottom: 0px; color: #ffffff">Heavy Monitor</div>
    </div>

    <div class="btn-group btn-toggle" id="softcoreMonitor" style="float:right; margin: 6px">
        <button class="btn btn-default">ON</button>
        <button class="btn btn-primary active">OFF</button>
        <div style="bottom: 0px; color: #ffffff">Light Monitor </div>
    </div>

</div>

<iframe>
    <html xmlns="http://www.w3.org/1999/xhtml">
    <body>
    <h1>Hi</h1>
    </body>
    </html>
</iframe>

<div class="container-fluid">


    <div class="col-lg-12"  id="GeneralDetail" style="margin-top: 100px">
        <div class="col-lg-2" style="background-color: lightcyan; margin-right: 5px; margin-left: 20px; text-align: center;
        border: solid; border-width: 1px">
            <b>Used Memory</b>
            <p id="used_memory:"></p>
        </div>
        <div class="col-lg-2" style="background-color: lightcyan; margin-right: 5px; margin-left: 5px; text-align: center;
        border: solid; border-width: 1px">
            <b>Total Keys</b>
            <p id="keys="></p>
        </div>
        <div class="col-lg-2" style="background-color: lightcyan; margin-right: 5px; margin-left: 5px; text-align: center;
        border: solid; border-width: 1px">
            <b>Command Processed</b>
            <p id="total_commands_processed:"></p>
        </div>
        <div class="col-lg-2" style="background-color: lightcyan; margin-right: 5px; margin-left: 5px; text-align: center;
        border: solid; border-width: 1px">
            <b>Uptime</b>
            <p id="uptime_in_seconds:"></p>
        </div>
        <div class="col-lg-2" style="background-color: lightcyan; margin-right: 5px; margin-left: 5px; text-align: center;
        border: solid; border-width: 1px">
            <b>Connected Clients</b>
            <p id="connected_clients:"></p>
        </div>
    </div>

    <br>
    <div class="col-lg-11" id="memory-placeholder" style="margin-top: 20px">
        <strong>Memory Processed</strong>

        <div class="btn-group" style="float: right" id="memory-placeholder-group">
            <button type="button" class="btn btn-default" id="memory-last-hour">Last Hour</button>
            <button type="button" class="btn btn-default" id="memory-last-3-hour">Last 3 Hour</button>
            <button type="button" class="btn btn-default" id="memory-last-day">Last Day</button>
            <button type="button" class="btn btn-default" id="memory-last-week">Last Week</button>
            <button type="button" class="btn btn-default" id="memory-last-month">Last Month</button>
        </div>
        <div id="memory-canvas-div">
            <canvas id="memoryChart" height="100" width="600"></canvas>
        </div>
    </div>

    <br>
    <div class="col-lg-11" id="command-placeholder" style="margin-top: 20px">
        <strong>Command Processed</strong>

        <div class="btn-group" style="float: right" id="command-placeholder-group">
            <button type="button" class="btn btn-default" id="command-last-hour">Last Hour</button>
            <button type="button" class="btn btn-default" id="command-last-3-hour">Last 3 Hour</button>
            <button type="button" class="btn btn-default" id="command-last-day">Last Day</button>
            <button type="button" class="btn btn-default" id="command-last-week">Last Week</button>
            <button type="button" class="btn btn-default" id="command-last-month">Last Month</button>
        </div>

        <div id="command-canvas-div">
            <canvas id="commandChart" height="100" width="600"></canvas>
        </div>
    </div>

    <br>
    <div class="col-lg-11" id="clients-placeholder" style="margin-top: 20px">
        <strong>Clients Connected</strong>

        <div class="btn-group" style="float: right" id="clients-placeholder-group">
            <button type="button" class="btn btn-default" id="clients-last-hour">Last Hour</button>
            <button type="button" class="btn btn-default" id="clients-last-3-hour">Last 3 Hour</button>
            <button type="button" class="btn btn-default" id="clients-last-day">Last Day</button>
            <button type="button" class="btn btn-default" id="clients-last-week">Last Week</button>
            <button type="button" class="btn btn-default" id="clients-last-month">Last Month</button>
        </div>

        <div id="clients-canvas-div">
            <canvas id="clientsChart" height="100" width="600"></canvas>
        </div>
    </div>


    <div class="col-lg-5" id="command-count-placeholder" style="margin-top: 20px">
        <strong>Top command</strong>

        <div class="btn-group" style="float: right" id="command-count-placeholder-group">
            <button type="button" class="btn btn-default" id="topcommands-last-hour">Last Hour</button>
            <button type="button" class="btn btn-default" id="topcommands-last-3-hour">Last 3 Hour</button>
            <button type="button" class="btn btn-default" id="topcommands-last-day">Last Day</button>
            <button type="button" class="btn btn-default" id="topcommands-last-week">Last Week</button>
            <button type="button" class="btn btn-default" id="topcommands-last-month">Last Month</button>
        </div>
        <div id="topcommands-canvas-div">
            <canvas id="commandCountChart" height="300" width="600"></canvas>
        </div>
    </div>

    <div class="col-lg-5 col-lg-offset-1" id="keys-count-placeholder" style="margin-top: 20px">
        <strong>Top Keys</strong>

        <div class="btn-group" style="float: right" id="keys-count-placeholder-group">
            <button type="button" class="btn btn-default" id="topkeys-last-hour">Last Hour</button>
            <button type="button" class="btn btn-default" id="topkeys-last-3-hour">Last 3 Hour</button>
            <button type="button" class="btn btn-default" id="topkeys-last-day">Last Day</button>
            <button type="button" class="btn btn-default" id="topkeys-last-week">Last Week</button>
            <button type="button" class="btn btn-default" id="topkeys-last-month">Last Month</button>
        </div>
        <div id="topkeys-canvas-div">
            <canvas id="keysCountChart" height="300" width="600"></canvas>
        </div>
    </div>

    <div class="col-lg-11" id="slowLogs-placeholder" style="margin-top: 20px">
        <strong>List of Slow Command</strong>
        <table class="table table-hover" id="slowlogTable">
            <tr>
                <th>Timestamp</th>
                <th>Time Taken</th>
                <th>Command</th>
            </tr>
        </table>
    </div>

</div>

<script src="js/jquery-1.11.0.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/Chart.js"></script>

<script src="js/monitorPage.js"></script>



</body>
</html>