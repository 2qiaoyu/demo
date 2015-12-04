<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品</title>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.js"></script>
<script type="text/javascript">
$(function(){
	loadGoodsInfo($("#goodsId").val());
});
function loadGoodsInfo(goodsId){
	$.get("findGoodsByGoodsId.htm?goodsId="+goodsId,function(data){
		$("#goodsName").val(data.goodsName);
		$("#goodsImg").attr("src",data.goodsImg);
		$("#goodsBrand").val(data.goodsBrand.brandId);
		$("#goodsNo").val(data.goodsNo);
		$("#goodsPrice").val(data.goodsPrice);
	});
}
function update(){
	$("#updateForm").submit();
}
</script>
</head>
<body>
	<h1>修改商品</h1>
	<form id="updateForm" action="updateGoods.htm" method="post">
		<input type="hidden" name=goodsId id="goodsId" value="${goodsId}"/>
		<div>
			<p>商品名称 <input name="goodsName" id="goodsName"/></p>
			<p>商品图片<img name="goodsImg" id="goodsImg" src="goodsImg"/></p>
			<p>商品品牌 
				<select name="brandId" id="goodsBrand">
					<c:forEach var="list" items="${brandList}">
						<option value="${list.brandId}">${list.brandName}</option>
					</c:forEach>
				</select>
			<p>商品编号 <input name="goodsNo" id="goodsNo" /></p>
			<p>商品价格 <input name="goodsPrice" id="goodsPrice" /></p>
			<p><input type="button" onclick="update()" value="保存"/></p>
		</div>
	</form>
<script type="text/javascript">

</script>
</body>
</html>