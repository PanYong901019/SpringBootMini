<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="./static/scripts/jquery-3.3.1.min.js"></script>
    <title>Title</title>
</head>
<body>
<h2>Hello World! Hello Easyond!</h2>
<form class="loginForm" action="/api/login" method="post">
    <div><p>username:</p><input class="account" type="text" name="account" value="administrator"></div>
    <div><p>password:</p><input class="passowrd" type="password" name="passowrd" value="py123123"></div>
    <div><input type="button" value="提交" class="doLogin"></div>
</form>
</body>
<script language="JavaScript">
    $(".doLogin").on("click", function () {
        var form = $(".loginForm");
        var reqData = {
            "account": $(".loginForm .account").val(),
            "password": $(".loginForm .passowrd").val()
        };
        $.ajax({
            url: form.attr("action"),
            type: form.attr("method"),
            data: reqData,
            dataType: "json",
            success: function (data) {
                if (data.rspCode == 0) {
                    alert(data.rspInfo);
                } else {
                    window.location.reload()
                }
            }
        });
    });
</script>
</html>
