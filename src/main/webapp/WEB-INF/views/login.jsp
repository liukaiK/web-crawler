<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css"/>
    <title>无障碍云后台管理系统</title>
    <script type="text/javascript">
        function login() {
            var username = $.trim($("#username").val());
            var password = $.trim($("#password").val());
            var code = $.trim($("#code").val());
            if (username === "" || username == null || password === "" || password == null || code == null || code === "") {
                $("#str").html("用户信息或验证码不能为空!");
            } else {
                $("#login_form").submit();
            }
        }

        function changImg(obj) {
            obj.attr("src", "${contextPath }/random/code?t=" + new Date().getTime());
        }

        // 回车监听
        document.onkeydown = function (event) {
            var e = event || window.event || arguments.callee.caller.arguments[0];
            if (e && e.keyCode === 13) { // enter 键
                login();
            }
        };
    </script>
</head>
<body>
<div class="page-container">
    <div class="main_box">
        <div class="login_box">
            <div class="login_logo">
                <h3><b>无障碍云后台管理系统</b></h3>
            </div>
            <div class="login_form">
                <form action="${contextPath }/login" id="login_form" method="post">
                    <div class="form-group">
                        <label for="username" class="t">用　户：</label>
                        <input id="username" name="username" type="text" class="form-control x319 in">
                    </div>
                    <div class="form-group">
                        <label for="password" class="t">密　码：</label>
                        <input id="password" name="password" type="password" class="password form-control x319 in">
                    </div>
                    <div class="form-group">
                        <label for="code" class="t">验证码：</label>
                        <input id="code" name="code" type="text" class="form-control x164 in" maxlength="4">
                        <img id="captcha_img" alt="点击更换" title="点击更换" src="${contextPath }/random/code" class="m"
                             onclick="changImg($(this))">
                    </div>
                    <div class="form-group">
                        <label class="t"></label>
                        <label for="j_remember" class="m" id="str" style="color: red;">
                            ${sessionScope.message}
                        </label>
                    </div>
                    <div class="form-group space">
                        <label class="t"></label>　　　
                        <button type="button" id="submit_btn" class="btn btn-primary btn-lg" onclick="login()">&nbsp;登&nbsp;录&nbsp;</button>
                        <input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg">
                    </div>
                </form>
            </div>
        </div>
        <div class="bottom">Copyright &copy; 2014 - 2015 <a href="#">系统登陆</a></div>
    </div>
    <%
        session.removeAttribute("message");
    %>
</div>
</body>
</html>