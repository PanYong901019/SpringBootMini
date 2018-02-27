<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="./static/scripts/jquery-3.3.1.min.js"></script>
    <title>Title</title>
</head>
<body>
<h2>welcome register!</h2>
<form class="register" action="/api/register" method="post">
    <div><p>登录名:</p><input class="account" type="text" name="account"></div>
    <div><p>密码:</p><input class="passowrd" type="text" name="passowrd"></div>
    <div><p>姓名:</p><input class="name" type="text" name="name"></div>
    <div><input type="button" value="提交" class="doRegister"></div>
</form>
<table>
    <jsp:useBean id="userList" scope="request" type="java.util.List"/>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.account}</td>
            <td>${user.name}</td>
            <td>${user.createTime}</td>
            <td>${user.lastLoginTime}</td>
        </tr>
    </c:forEach>
</table>
</body>
<script language="JavaScript">
    $(".doRegister").on("click", function () {
        var form = $(".register");
        var reqData = {
            "account": $(".register .account").val(),
            "password": $(".register .passowrd").val(),
            "name": $(".register .name").val()
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