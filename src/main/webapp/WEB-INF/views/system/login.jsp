<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <script type="text/javascript" src="${contextPath }/js/jquery/jquery-3.2.1.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${contextPath }/js/jquery-easyui-1.5.3/jquery.easyui.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${contextPath }/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>

    <link rel="stylesheet" href="${contextPath }/js/jquery-easyui-1.5.3/themes/icon.css" charset="UTF-8">
    <link rel="stylesheet" href="${contextPath }/js/jquery-easyui-1.5.3/themes/default/easyui.css" charset="UTF-8">


    <title>登录</title>
    <script type="text/javascript">
        function changeImg() {
            var time = new Date().getTime();
            $("#verifycodeImg").attr("src", "${contextPath }/manage/verifycode?t=" + time);
        }


        $(function () {

            $('#username').validatebox({
                required: true,
                validType: 'length[3,10]'
            });
            $('#loginForm').form({
                url: "login",
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (data) {
                    var data = eval('(' + data + ')');//将JSON字符串更改为JavaScript对象
                    if (data.success == true) {
                        $.messager.alert('登录成功', data.message, 'info');
                    } else {
                        $.messager.alert('登录失败', data.message, 'info');
                    }
                }
            });


        })

        function doSubmit() {
            $('#loginForm').submit();
        }


    </script>
</head>
<body>
<div>
    <form method="post" id="loginForm">
        <div>
            <label for="username">用　户：</label>
            <input type="text" name="username" title="请输入用户" id="username">
        </div>
        <div>
            <label for="password">密　码：</label>
            <input type="password" name="password" title="请输入密码" id="password" class="easyui-validatebox" required="true">
        </div>
        <div>
            <label for="verifycode">验证码：</label>
            <input type="text" name="verifycode" title="请输入验证码" id="verifycode" class="easyui-validatebox" required="true">
            <img alt="点击更换验证码" title="点击更换验证码" id="verifycodeImg" src="${contextPath }/manage/verifycode" onclick="changeImg()">
        </div>
        <div>
            <input type="button" value="登录" title="登录" onclick="doSubmit()">
            <input type="reset" value="重置" title="重置">
        </div>
    </form>
</div>
</body>
</html>