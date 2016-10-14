<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无障碍云后台管理系统</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>

<script language="javascript">
	$(function() {
		$('.loginbox0').css({
			'position' : 'absolute',
			'left' : ($(window).width() - 810) / 2
		});
		$(window).resize(function() {
			$('.loginbox0').css({
				'position' : 'absolute',
				'left' : ($(window).width() - 810) / 2
			});
		})
	});
</script>

</head>

<body style="background-color:#1c77ac; background-image:url(/image/light1.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">


<div class="logintop">    
    <span>欢迎进入无障碍云管理系统</span>    
    <ul>
    <li><a href="${contextPath}/logout">退出</a></li>
    </ul>    
    </div>
    
    <div class="loginbody1">
    
    <span class="systemlogo"></span> 
       
    <div class="loginbox0">
    
    <ul class="loginlist">
    <li><a href="${contextPath}/admin/site"><img src="/image/l01.png"  alt="无障碍云后台"/><p>无障碍云后台<br />管理系统</p></a></li>
<%--     <li><a href="${contextPath}/admin/queryComplaint"><img src="/image/l02.png"  alt="查询投诉登记单" style="width: 135px;height: 135;"/><p>查询投诉登记单<br /></p></a></li> --%>
<%--     <li><a href="${contextPath}/admin/queryReport"><img src="/image/l03.png"  alt="查询举报登记单" style="width: 135px;height: 135;"/><p>查询举报登记单<br /></p></a></li> --%>
    </ul>
    
    
    </div>
    
    </div>
    
    <div class="loginbm">Copyright &copy; 2014 - 2015 <a href="#">系统登陆</a></div>
	
    
</body>

</html>
