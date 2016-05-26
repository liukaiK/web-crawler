<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>搜索结果页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta content="width=device-width, initial-scale=1" name="viewport">
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<style type="text/css">
body {
	font-family: Microsoft YaHei;
	padding-top: 20px;
	background: #DCDCDC;
	text-align: left;
}

li {
	margin-bottom: 25px;
}

.input_btn {
	width: 666px;
	height: 50px;
	position: relative;
	margin-left: 16%;
}

#serCon {
	width: 468px;
	height: 30px;
	text-align: left;
	margin-top: 0px;
}

#search-btn {
	font-size: 16px;
	color: #d2691e;
	width: 70px;
	height: 38px;
	position: absolute;
	margin-top: -1px;
}

.content {
	background: #DCDCDC none repeat scroll 0 0;
	float: left;
	padding: 5px 0 15px 10px;
	width: 850px;
	font: 12px;
	margin: 0;
}

p {
	background: rgba(0, 0, 0, 0) url("") no-repeat scroll left center;
	line-height: 24px;
	padding-left: 12px;
	margin: 0;
}

h3 {
	padding: 10px;
	font-size: 12px;
	font-weight: normal;
	margin: 0;
}

.Sbmenu {
	background: (0, 0, 0, 0 ) url("") no-repeat scroll 0 10px;
	border-bottom: 1px solid #d8d8d8;
	color: #606060;
	line-height: 25px;
	padding-left: 10px;
	width: 900px;
	font-size: 12px;
}

.Sbmenu span {
	color: #0d1a8a;
}

a {
	text-decoration: none;
}

.page {
	font-size: 12px;
	margin-left: 0;
	padding-bottom: 30px;
	width: 628px;
	border: 0;
}

.page a,.page span {
	display: block;
	float: left;
	height: 1.7em;
	line-height: 1.7em;
	margin: 0 0.2em;
	text-align: center;
	text-decoration: none;
	width: 2em;
}

.page a.prev-page,.page a.next-page {
	width: 4em;
}

.page a .first-page,.page a .last-page {
	width: 3em;
}

tbody {
	display: none;
}
</style>
</head>
<body>

<%
	String domain = request.getScheme() + "://" + request.getServerName();
	
 %>
	<div class="input_btn">
		<form action="${contextPath}/search" method="post" id="search" name="search" role="form" class="form-signin">
			<input type="text" id="serCon" name="serCon" value="${result.serCon}" autofocus required placeholder="填入搜索信息" class="form-control" value="${serCon}">
			<button id="search-btn" type="button" class="btn btn-primary">搜索</button>
			<br>
			<input id="currentPage" type="hidden" name="currentPage" value="${result.currentPage}">
			<input id="hidserCon" type="hidden"  value="${result.serCon}">
		</form>
	</div>
	<div class="content">
		<div class="Sbmenu">
			<span> 查询结果： </span>检索到记录${result.total}条
		</div>
		<div class="SearchList" id="result_list">
			<ul>
				<c:forEach items="${result.history }" var="res">
					<li><p>
							<a class="link" href="<%=domain %>/web/html/${res.md5 }" target="_blank">${res.title }</a>
						<p>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="page" style="margin-left:0;padding-bottom:30px;width:1000px;border:0;">
			<nobr>
				<c:if test="${result.currentPage>1}">
					<a class="first-page" href="javascript:goPage('1');" target="_self">首页</a>
					<a class="prev-page" href="javascript:goPage(${result.currentPage - 1});" target="_self">上一页</a>
				</c:if>
			  	<c:if test="${result.totalPage<=11}">
			  		<c:set var="begin" value="1"></c:set>
			  		<c:set var="end" value="${result.totalPage}"></c:set>
			  	</c:if>
			  	<c:if test="${result.totalPage>11}">
			  		<c:choose>
			  			<c:when test="${result.currentPage<=5}">
			  				<c:set var="begin" value="1"></c:set>
			  				<c:set var="end" value="11"></c:set>
			  			</c:when>
			  			<c:when test="${result.currentPage>=result.totalPage-5}">
			  				<c:set var="begin" value="${result.totalPage-11}"></c:set>
			  				<c:set var="end" value="${result.totalPage}"></c:set>
			  			</c:when>
			  			<c:otherwise>
			  				<c:set var="begin" value="${result.currentPage-5}"></c:set>
			  				<c:set var="end" value="${result.currentPage+5}"></c:set>
			  			</c:otherwise>
			  		</c:choose>
			  	</c:if>
				<c:forEach begin="${begin}" end="${end}" var="i">
					<c:choose>
						<c:when test="${result.currentPage==i}">
							<span class="current-page"> ${i} </span> 
						</c:when>
						<c:otherwise>
							<a target="_self" href="javascript:goPage('${i }');"> ${i } </a> 
						</c:otherwise>
					</c:choose>
				</c:forEach>				  	
				<c:if test="${result.currentPage<result.totalPage}">
					<a class="next-page" href="javascript:goPage('${result.currentPage + 1}');" target="_self"> 下一页 </a>
					<a class="last-page" href="javascript:goPage('${result.totalPage}');" target="_self"> 尾页 </a>
				</c:if>
			</nobr>
			&nbsp;&nbsp;&nbsp;&nbsp; 共有${result.total}条记录 当前页数:${result.currentPage }/总页数:${result.totalPage}<br>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			searchbtn();
		});
		function searchbtn() {
			$("button[id=search-btn]").click(function() {
				var formName = $("#search");
				var serCon = $("#serCon").val();
				if (serCon.length == 0) {
					alert("请填写要查询的信息!");
				} else {
					$("#currentPage").val(1);
					formName.submit();
				}
			});
		}
		
		function goPage(currentPage){
			var hidserCon = $("#hidserCon").val();
			$("#serCon").val(hidserCon);
			$("#currentPage").val(currentPage);
			$("#search").submit();
		}
	</script>
</body>
</html>
