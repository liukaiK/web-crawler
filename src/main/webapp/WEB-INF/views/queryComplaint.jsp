<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无障碍云后台管理系统</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$(".click").click(function() {
			$(".tip").fadeIn(200);
		});

		$(".tiptop a").click(function() {
			$(".tip").fadeOut(200);
		});

		$(".sure").click(function() {
			$(".tip").fadeOut(100);
		});

		$(".cancel").click(function() {
			$(".tip").fadeOut(100);
		});

	});
</script>


</head>


<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">查询投诉登记单</a>
			</li>
		</ul>
	</div>

	<div class="rightinfo">
		<div class="tools">
			<ul class="toolbar1">
				<li><a href="${contextPath}/admin/login">退出</a>
				</li>
			</ul>
		</div>
		<table class="tablelist">
			<thead>
				<tr>
					<th>编号</th>
					<th>消费者姓名</th>
					<th>消费者联系电话</th>
					<th>被投诉方名称</th>
					<th>被投诉方地址</th>
					<th>商品/服务名称</th>
					<th>凭证</th>
					<th>购买(接收服务)时间</th>
					<th>事件发生时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			
			<c:forEach items="${result }" var="res">
				<tr>
					<td>${res.id }</td>
					<td>${res.name }</td>
					<td>${res.phone }</td>
					<td>${res.designation }</td>
					<td>${res.local }</td>
					<td>${res.goods_name }</td>
					<td>${res.voucher }</td>
					<td>${res.buy_time }</td>
					<td>${res.happen_time }</td>
					<td>
						<a href="${contextPath}/admin/queryComplaintById?id=${res.id }" class="tablelink">查看</a> 
						<a href="${contextPath}/admin/deleteComplaintById?id=${res.id }" onclick="javascript:if (confirm('确定删除吗？')) { return true;}else{return false;};" class="tablelink"> 删除</a>
					</td>
				</tr>
			</c:forEach>

			</tbody>
		</table>
	</div>

	<script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
