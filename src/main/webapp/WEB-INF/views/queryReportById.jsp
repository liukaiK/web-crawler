<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无障碍云后台管理系统</title>
<style type="text/css">
* {
	font-size: 14px;
	margin: 0;
	padding: 0;
	zoom: 1;
}

body {
	text-align: center;
	margin: 0 auto;
	font-family: "Microsoft Yahei";
}

.main {
	margin: 0 auto;
	padding: 0px;
	font-size: 14px;
	width: 1000px;
	background-color: #fafafa;
}

.erjibg {
	width: 800px;
	margin: auto;
}

.erjibg tbody tr {
	line-height: 30px;
}

.title {
	font-size: 20px;
	margin-top: 10px;
	border-bottom: 3pt solid;
}

.left_td {
	text-align: left;
	color: #0000ff;
	font-size: 17px;
	width: 125px;
}

.first_td {
	text-align: right;
	padding-right: 7px;
	width: 143px;
	display: block;
}

.after_td {
	text-align: left;
}
</style>
</head>
<body>
	<div class="main">
		<form action="">
			<table class="erjibg">
				<tbody>
					<tr>
						<td>
							<div class="title">举报登记单</div></td>
					</tr>
					<tr>
						<td>
							<table>
								<tbody>
									<tr>
										<td class="left_td">举报者信息：</td>
										<td></td>
									</tr>
									<tr>
										<td class="first_td">姓名:</td>
										<td class="after_td">${result.name }</td>
									</tr>
									<tr>
										<td class="first_td">性别:</td>
										<td class="after_td">${result.gender }</td>
									</tr>
									<tr>
										<td class="first_td">联系电话:</td>
										<td class="after_td">${result.phone }</td>
									</tr>
									<tr>
										<td class="first_td">通讯地址:</td>
										<td class="after_td">${result.adress }</td>
									</tr>
									<tr>
										<td class="first_td">密码设置:</td>
										<td class="after_td">${result.password }</td>
									</tr>
									<tr>
										<td class="first_td">确认密码:</td>
										<td class="after_td">${result.affirmpassword }</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tbody>
									<tr>
										<td class="left_td">被举报方信息:</td>
										<td></td>
									</tr>
									<tr>
										<td class="first_td">名称:</td>
										<td class="after_td">${result.designation }</td>
									</tr>
									<tr>
										<td class="first_td">地址:</td>
										<td class="after_td">北京市&nbsp;&nbsp; ${result.city } &nbsp;&nbsp; ${result.local }&nbsp;&nbsp;</td>
									</tr>
									<tr>
										<td class="first_td">联系电话:</td>
										<td class="after_td">${result.number }</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tbody>
									<tr>
										<td class="left_td">举报内容:</td>
										<td></td>
									</tr>
									<tr>
										<td class="first_td">商品/服务:</td>
										<td class="after_td">${result.goods }</td>
									</tr>
									<tr>
										<td class="first_td">商品/服务名称:</td>
										<td class="after_td">${result.goods_name }</td>
									</tr>
									<tr>
										<td class="first_td">举报人要求:</td>
										<td class="after_td"></td>
									</tr>
									<tr>
										<td class="first_td">查处:</td>
										<td class="after_td">${result.look_into }</td>
									</tr>
									<tr>
										<td class="first_td">奖励:</td>
										<td class="after_td">${result.award }</td>
									</tr>
									<tr>
										<td class="first_td">保密:</td>
										<td class="after_td">${result.secrecy }</td>
									</tr>
									<tr>
										<td class="first_td">查处结果沟通:</td>
										<td class="after_td">${result.communication }</td>
									</tr>
									<tr>
										<td class="first_td">举报人协助查处:</td>
										<td class="after_td">${result.investigate }</td>
									</tr>
									<tr>
										<td class="first_td">事发时间:</td>
										<td class="after_td">${result.happen_time }</td>
									</tr>
									<tr>
										<td class="first_td">简要情况:</td>
										<td class="after_td">请不要超过400个汉字（包括标点）</td>
									</tr>
									<tr>
										<td class="first_td"></td>
										<td class="after_td"><textarea id="content" name="content" readonly="readonly" rows="10" cols="80" title="请输入内容">${result.content }</textarea>
										</td>
									</tr>
									<tr>
										<td class="first_td">
											<a href="javascript:window.print();"><input type="button" value="打印" /></a>&nbsp;&nbsp;
											<a href="${contextPath}/admin/deleteReportById?id=${result.id }" onclick="javascript:if (confirm('确定删除吗？')) { return true;}else{return false;};"><input type="button" value="删除" /> </a>
										</td>
										<td class="after_td">
											<a href="${contextPath}/admin/queryReport"><input type="button" value="返回" /> </a>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>