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
							<div class="title">投诉登记单</div>
						</td>
					</tr>
					<tr>
						<td>
							<table>
								<tbody>
									<tr>
										<td class="left_td">消费者信息：</td>
										<td></td>
									</tr>
									<tr>
										<td class="first_td">姓名:</td>
										<td class="after_td">${result.name}</td>
									</tr>
									<tr>
										<td class="first_td">性别:</td>
										<td class="after_td">${result.gender}</td>
									</tr>
									<tr>
										<td class="first_td">联系电话:</td>
										<td class="after_td">${result.phone}</td>
									</tr>
									<tr>
										<td class="first_td">通讯地址:</td>
										<td class="after_td">${result.adress}</td>
									</tr>
								</tbody>
							</table></td>
					</tr>
					<tr>
						<td>
							<table>
								<tbody>
									<tr>
										<td class="left_td">被投诉方信息:</td>
										<td></td>
									</tr>
									<tr>
										<td class="first_td">名称:</td>
										<td class="after_td">${result.designation}</td>
									</tr>
									<tr>
										<td class="first_td">地址:</td>
										<td class="after_td">北京市&nbsp;&nbsp;${result.city }&nbsp;&nbsp;${result.local}</td>
									</tr>
									<tr>
										<td class="first_td">联系电话:</td>
										<td class="after_td">${result.number }</td>
									</tr>
								</tbody>
							</table></td>
					</tr>
					<tr>
						<td>
							<table>
								<tbody>
									<tr>
										<td class="left_td">投诉内容:</td>
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
										<td class="first_td">品牌:</td>
										<td class="after_td">${result.brand }</td>
									</tr>
									<tr>
										<td class="first_td">型号:</td>
										<td class="after_td">${result.model }</td>
									</tr>
									<tr>
										<td class="first_td">数量:</td>
										<td class="after_td">${result.count }</td>
									</tr>
									<tr>
										<td class="first_td">商品/服务价格:</td>
										<td class="after_td">${result.price }</td>
									</tr>
									<tr>
										<td class="first_td">凭证:</td>
										<td class="after_td">${result.voucher }</td>
									</tr>
									<tr>
										<td class="first_td">购买(接收服务)时间:</td>
										<td class="after_td">${result.buy_time }</td>
									</tr>
									<tr>
										<td class="first_td">事件发生时间:</td>
										<td class="after_td">${result.happen_time }</td>
									</tr>
									<tr>
										<td class="first_td">简要情况:</td>
										<td class="after_td">请不要超过400个汉字（包括标点）</td>
									</tr>
									<tr>
										<td class="first_td"></td>
										<td class="after_td"><textarea readonly="readonly" id="content" name="content" rows="10" cols="80" title="请输入内容">${result.content }</textarea></td>
									</tr>
									<tr>
										<td class="first_td">
											<a href="javascript:window.print();"><input type="button" value="打印" /></a>&nbsp;&nbsp;
											<a href="${contextPath}/admin/deleteComplaintById?id=${result.id }" onclick="javascript:if (confirm('确定删除吗？')) { return true;}else{return false;};"><input type="button" value="删除" /></a>
										</td>
										<td class="after_td"><a href="${contextPath}/admin/queryComplaint"><input type="button" value="返回" /></a></td>
									</tr>
								</tbody>
							</table></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</body>
</html>