<%@ page import="win.panyong.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="./static/scripts/jquery-3.3.1.min.js"></script>
    <title>Title</title>
</head>
<body>
<h2>welcome main!</h2>
<% User user = (User) request.getAttribute("loginUser");
    if (user.getId() == 1) {%>
<p><a href="./userManagment">添加用户</a></p>
<%}%>

<form class="changePasswordForm" action="/api/changePassword" method="post">
    <div><p>oldPassword:</p><input class="oldPassword" type="text" name="oldPassword"></div>
    <div><p>newPassword:</p><input class="newPassword" type="text" name="newPassword"></div>
    <div><input type="button" value="提交" class="doChangePassword"></div>
</form>

<div><p><a href="./logout">退出</a></p></div>
</body>
<script language="JavaScript">
    $(".doChangePassword").on("click", function () {
        var form = $(".changePasswordForm");
        var reqData = {
            "oldPassword": $(".changePasswordForm .oldPassword").val(),
            "newPassword": $(".changePasswordForm .newPassword").val()
        };
        $.ajax({
            url: form.attr("action"),
            type: form.attr("method"),
            data: reqData,
            dataType: "json",
            success: function (data) {
                if (data.rspCode === 0) {
                    alert(data.rspInfo);
                } else {
                    window.location.replace("/logout")
                }
            }
        });
    });
</script>
</html>