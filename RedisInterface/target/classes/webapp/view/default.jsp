<%@ page import="Model.RedisInstances" %>
<%@ page import="Model.RedisInstance" %>
<%--
  Created by IntelliJ IDEA.
  User: asad
  Date: 14/8/14
  Time: 3:02 PM
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
    <link href="css/simple-sidebar.css" rel="stylesheet">
    <link href="css/default.css" rel="stylesheet">
    <link href="css/table.css" rel="stylesheet">

</head>

<body>

<div id="wrapper">
    <jsp:include page="fragments/sidebar.jsp" flush="true"/>
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12">
                    <a href="#menu-toggle" class="btn btn-default btn-lg" id="menu-toggle" style="border-width: 0px; visibility: hidden">
                        <u style="font-family: 'Comic Sans MS'; font-weight: bold; font-size: 18">Redis Administrator</u></a>
                    <a style="cursor: default; text-decoration: none; font-family: 'Comic Sans MS'; font-weight: bold"
                       id="instance_name_display"></a>

                    <a class="btn btn-default"style="visibility: hidden; float: right; border: solid; border-width: 2px; margin-left: 10px"
                       onclick="loadMonitor()" id="startMonitorMode">Go Monitor!</a>

                    <button class="btn btn-default" onclick="loadAddKey()"
                            style="visibility: hidden; float: right; border: solid; border-width: 2px"
                            id="addAnotherKey">Add another key</button>

                </div>

                <div class="col-lg-3" id="second-sidebar" style="visibility: hidden">

                    <div class="btn-group btn-group-xs" id="taskDisplayButtons">
                        <button type="button" class="btn btn-default rangila-button" id="paginatedView">Default</button>
                        <button type="button" class="btn btn-default rangila-button" id="searchKey">Search Key</button>
                        <button type="button" class="btn btn-default rangila-button" id="treeView">Tree View</button>
                    </div>
                    <br><br>
                    <!-- division to load the task -->
                    <div id="taskDisplay">
                    </div>
                </div>

                <div id="workspace"  class="col-lg-9" style="height: 86%; overflow-y: auto">
                </div>

            </div>
        </div>
    </div>

</div>

<!-- Modal -->
<div class="modal fade" id="myHSetModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Edit Entity</h4>
            </div>

            <div class="modal-body">
                <input type="text" class="form-control" placeholder="" name="HSet-field" id="HSet-field" style="font-size: 20" readonly>
                <br>
                <input type="text" class="form-control" placeholder="" name="HSet-value" id="HSet-value" style="font-size: 20">


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="editHSet()">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myListModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Edit Entity</h4>
            </div>

            <div class="modal-body">

                <input type="text" class="form-control" placeholder="" name="List-index" id="List-index" style="font-size: 20" readonly>
                <br>
                <input type="text" class="form-control" placeholder="" name="List-value" id="List-value" style="font-size: 20">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="editList()">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="mySetModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Edit Entity</h4>
            </div>

            <div class="modal-body">
                <input type="text" class="form-control" placeholder="" name="Set-value" id="Set-value" style="font-size: 20">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="editSet()">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myStringModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Edit Entity</h4>
            </div>

            <div class="modal-body">
                <input type="text" class="form-control" placeholder="" name="String-key" id="String-key" style="font-size: 20" readonly>
                <br>
                <input type="text" class="form-control" placeholder="" name="String-value" id="String-value" style="font-size: 20">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="editString()">Save changes</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="myZSetModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">Edit Entity</h4>
            </div>

            <div class="modal-body">
                <input type="text" class="form-control" placeholder="" name="ZSet-value" id="ZSet-value" style="font-size: 20" readonly>
                <br>
                <input type="text" class="form-control" placeholder="" name="ZSet-score" id="ZSet-score" style="font-size: 20">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="editZSet()">Save changes</button>
            </div>
        </div>
    </div>
</div>


<script src="js/angular.min.js"></script>
<script src="js/jquery-1.11.0.js"></script>

<script src="js/bootstrap.min.js"></script>
<script src="js/bootbox.js"></script>

<script src="js/pagination.js"></script>
<script src="js/default.js"></script>

<script src="js/editKey/editHSet.js"> </script>
<script src="js/editKey/editList.js"> </script>
<script src="js/editKey/editSet.js"> </script>
<script src="js/editKey/editString.js"> </script>
<script src="js/editKey/editZSet.js"> </script>

<script src="dist/jstree.js"></script>
<script src="dist/jstree.min.js"></script>


</body>
</html>
