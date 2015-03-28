<%--
  Created by IntelliJ IDEA.
  User: asad
  Date: 14/8/14
  Time: 1:34 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html lang="en">

<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Redis Interface Login Form</title>
    <meta name="generator" content="Bootply" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!--
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    -->
</head>

<body>

    </div>
    <div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="position: relative; top: 100px">

                <div class="modal-header">
                    <h1 class="text-center">Login: Redis Interface</h1>
                </div>

                <div class="modal-body">

                    <form class="form col-md-12 center-block" id="form" name="login" method="POST" action="login.do" onsubmit="return validateForm()">

                        <div class="form-group">
                            <input type="text" class="form-control input-lg" placeholder="Username" name="Username" required>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control input-lg" placeholder="Password" name="Password" required>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary btn-lg btn-block" type="submit">Sign In</button>
                         </div>
                        <div id="error"></div>
                    </form>

                </div>

                <div class="modal-footer">
                    <div class="col-md-12"></div>
                </div>
            </div>
        </div>
    </div>

<!-- script references -->
<script src="js/jquery-1.11.0.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="/js/jqBootstrapValidation.js"></script>
</body>

</html>