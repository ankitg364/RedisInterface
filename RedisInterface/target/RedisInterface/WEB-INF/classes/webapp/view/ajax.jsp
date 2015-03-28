<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>AJAX calls using Jquery in Servlet</title>
    <script src="http://code.jquery.com/jquery-latest.js">
    </script>
    <script>
        $(document).ready(function() {
            $('#submit').click(function(event) {
                var username=$('#user').val();
                $.get('AjaxController.do',{user:username},function(responseJson)
                {
                    var $ul = $('<ul>').appendTo($('#welcometext')); // Create HTML <ul> element and append it to HTML DOM element with ID "somediv".
                    $.each(responseJson, function(index, item) { // Iterate over the JSON array.
                        $('<li>').text(item).appendTo($ul);      // Create HTML <li> element, set its text content with currently iterated item and append it to the <ul>.
                    });
                });
            });
        });
    </script>
</head>
<body>
    <h1>AJAX Demo using Jquery in JSP and Servlet</h1>
    Enter your Name:
    <input type="text" id="user"/>]
    <a  id="submit"> hi</a>
    <br/>
    <div id="welcometext">
    </div>
</body>
</html>
