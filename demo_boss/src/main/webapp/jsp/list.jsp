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
<title>商品列表</title>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/jquery-ui-css/jquery-ui-1.10.3.custom.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-ui/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript">
	function clickShowPage(pageSize,pageNo){
		$("#searchPageSize").val(pageSize);
		$("#searchPageNo").val(pageNo);
		if (pageNo == "" || pageNo == "0" || isNaN(pageSize) || isNaN(pageNo) || pageNo <= 0)
	    {
			alert("错误！");
	    }else{
	    	$("#listForm").attr("action","list.htm");
	    	$("#listForm").submit();
	    }
	}
	function changePageSize(){
		var size=$("#list_size").val();
		$("#searchPageSize").val(size);
		$("#searchPageNo").remove();
		if (size == "" || size == "0" || isNaN(size) || size <= 0)
	    {
	        alert("错误！");
	    }
	    else {
	    	$("#listForm").attr("action","list.htm");
	        $("#listForm").submit();
	    };
	}
	function changeShowPage(totalPages){
		var size=$("#list_size").val();
		var list_pageno=$("#list_pageno").val();
		if(list_pageno>totalPages){
	    	list_pageno=totalPages;
	    }
		list_pageno=(list_pageno==0?1:list_pageno);
		$("#searchPageSize").val(size);
		$("#searchPageNo").val(list_pageno);
		if (list_pageno == "" || list_pageno == "0" || isNaN(list_pageno) || list_pageno <= 0 ||
	    		size == "" || size == "0" || isNaN(size) || size <= 0)
	    {
			alert("错误！");
	    }else{
	    	$("#listForm").attr("action","list.htm");
	    	$("#listForm").submit();
	    }
	}
	
	//反选
	function unSelectAll(obj) {
		var checkboxs = document.getElementsByName(obj);
		for ( var i = 0; i < checkboxs.length; i++) {
			var e = checkboxs[i];
			e.checked = !e.checked;
		}
	}
	//全选
	function selectAll(obj) {
		var checkboxs = document.getElementsByName(obj);
		for ( var i = 0; i < checkboxs.length; i++) {
			var e = checkboxs[i];
			e.checked = true;
		}
	}
	$(function(){
		$("#dialog-confirm").dialog({
			resizable : false,
			height : 200,
			width : 350,
			modal : true,
			autoOpen : false,
			buttons : {
				"确定" : function() {
					//删除数据
					$("#listForm").attr("action","batchDelGoods.htm");
					$("#listForm").submit();
					$(this).dialog("close");
				},
				'取消' : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#dialog-tip").dialog(
			        {
			            resizable : false, height : 200, width : 270, modal : true, autoOpen : false, buttons : {
			                "确定" : function () 
			                {
			                    $(this).dialog("close");
			                }
			            }
		});
		$("#dialog-form").dialog({
			title:"添加商品",
			resizable : false,
			height : 400,
			width : 350,
			modal : true,
			autoOpen : false,
			buttons : {
				"保存" : function() {
					$("#addForm").submit();
					$(this).dialog("close");
				},
				'取消' : function() {
					$(this).dialog("close");
				}
			}
		});
		$("#addGoods").button().click(function(){
			$("#dialog-form").dialog("open");
		});
		//填充品牌信息
		$.get("getgoodsBrandList.htm",function(data){
			var options = '';
			for( var i=0; i<data.length; i++){
				var goodsBrandName=data[i];
				options +=  "<option value='"+goodsBrandName.brandId+"'>"+goodsBrandName.brandName+"</option>";
				}
				$("#goodsBrandName").html(options);
		});
	});
	//批量删除
	function batchDel(obj)
	{
	    var bool = false;
	    var goodsIds = document.getElementsByName(obj);
	    for (var i = 0; i < goodsIds.length; i++) {
	        var e = goodsIds[i];
	        if (e.checked == true) {
	            bool = true;
	        }
	    }
	    if (bool == false)
	    {
	        //选择数据
	       	$("#dialog-tip").dialog("open");
	    }
	    else
	    {
	        $("#dialog-confirm").dialog("open");
	    }
	}
	//条件查询
	function searchSubmit(){
		var list_pageSize=$("#list_size").val();
		$("input[name='pageNo']").val("1");
		$("input[name='pageSize']").val(list_pageSize);
		$("#searchForm").submit();
	}
	/*点击导出按钮的时候*/
	function exportList(){
		$("#listForm").attr("action","exportGoodsList.htm");
		$("#listForm").submit();
	}
	/*点击导出当前页的时候 */
	function exportPage(){
		selectAll('goodsIds');
		$("#listForm").attr("action","exportGoodsCheck.htm");
        $("#listForm").submit();
	}
	/*点击导出选中的时候*/
	function exportCheck(){
		var bool = false;
	    var goodsIds = document.getElementsByName("goodsIds");
	    for (var i = 0; i < goodsIds.length; i++) {
	        var e = goodsIds[i];
	        if (e.checked == true) {
	            bool = true;
	        }
	    }
	    if (bool == false)
	    {
	        //选择数据
	       	$("#dialog-tip").dialog("open");
	    }
	    else
	    {
	    	$("#listForm").attr("action","exportGoodsCheck.htm");
	        $("#listForm").submit();
	    }
	}
	function updatePwd(){
		window.location.href="updatePwd.html";
	}
</script>
	<style type="text/css">
		body{
			margin: 0px auto;
			text-align: center;
		}
		table,th,td{
		border:1px solid black;
		border-collapse: collapse;
		padding: 5px;
		margin: 0px auto;
		text-align: center;
		}
		td{text-align: center;}
		thead {background-color: gray;}
		.current{
			background-color: gray;
		}
		.input1{
			width: 20px;
		}
		.img1{
			width: 40x;
			height: 40px;
		}
 		button{
			width: 80px;
			height: 30px;
		}
		.fr{
			float: right;
			display: inline;
		}	
	</style>
</head>
<body>
	<div>
		<form action="#" method="post" id="updateForm">
			<p><span>欢迎您：${sessionScope.admin.userName}</span></p>
			<p><span><input type="button" value="修改密码" onclick="updatePwd()" /></span></p>
			<p><span><a href="logout.htm">[登出]</a></span></p>
		</form>
	</div>
	<h4>共有${pb.rows}个商品</h4>
	<button id="addGoods">添加</button>
	<button id="deleteGoods" onclick="batchDel('goodsIds');" >批量删除</button>
		<!-- 条件查询 -->
	<div id="search" class="fr">
				<form id="searchForm" action="list.htm" method="post">
		  			<input type="hidden" name="pageNo" />
		  			<input type="hidden" name="pageSize" />
					<div class="search_box">
						<div class="search_sel">
							<span></span> 
							<select name="condition" id="condition">
								
								<option value="1" <c:if test="${selectBean.condition eq '1' }">selected="selected"</c:if>>商品名称</option>
								<option value="2" <c:if test="${selectBean.condition eq '2' }">selected="selected"</c:if>>商品编号</option>
							</select>
	
						</div>
						<!--arch_sel-->
						<input class="search_text" name="searchText" value="${selectBean.searchText }" id="searchText" type="text" />
					</div>
					<!--arch_box-->
					<button id="search-btn" onclick="searchSubmit();">搜索</button>
				</form>
	</div>
	<!-- 导出 -->
	<div>
	<a href="javascript:;" onclick="exportList();">导出全部</a>
	<a href="javascript:;" onclick="exportPage();">导出列表</a>
	<a href="javascript:;" onclick="exportCheck();">导出选中</a>
	</div>
	<div id="goodsList">
	<form id="listForm" action="#" method="post">
	<input type="hidden" name="pageSize" id="searchPageSize" value="${pb.pageSize}"/>
	<input type="hidden" name="pageNo" id="searchPageNo" value="${pb.pageNo}"/>
	<input type="hidden" name="goodsId" value="${goods.goodsId}" />
	<table>
		<thead>
			<tr>
				<th>序号</th>
				<th><a href="javascript:void(0)" onclick="selectAll('goodsIds')">全选</a>
					<a href="javascript:void(0)" onclick="unSelectAll('goodsIds')">反选</a>
				</th>
				<th>商品名称</th>
				<th>商品图片</th>
				<th>品牌</th>
				<th>商品编号</th>
				<th>价格</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="goods" items="${pb.list}" varStatus="sta">
				<tr>
					<td>${sta.count}</td>
					<td><input type="checkbox" name="goodsIds" value="${goods.goodsId}"></td>
					<td>${goods.goodsName}</td>
					<td><img class="img1" src="${goods.goodsImg}"/></td>
					<td>${goods.goodsBrand.brandName}</td>
					<td>${goods.goodsNo}</td>
					<td>${goods.goodsPrice}</td>
					<td><a href="javascript:void(0)" onclick="location.href='toModifyGoods.htm?goodsId=${goods.goodsId}'">修改</a>|
					<%-- <a href="javascript:void(0)" onclick="location.href='toModifyGoods.htm?goodsId=${goods.goodsId}'">修改</a>| --%>
					<a href="javascript:void(0)" onclick="location.href='delGoods.htm?goodsId=${goods.goodsId}'">删除</a>
					<%--<a href="javascript:void(0)" onclick="location.href='findGoodsByGoodsId.htm?goodsId=${goods.goodsId}'">查看</a> --%>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr><td colspan="8" >每页显示<input id="list_size" class="input1" type="text" value="${pb.pageSize}" onchange="changePageSize()">
				条,共${pb.totalPages}页/当前${pb.pageNo}页,
				跳转到<input type="text" class="input1" id="list_pageno" value="${pb.pageNo}" onchange="changeShowPage(${pb.totalPages})">
				<a href="javascript:void(0)" onclick="clickShowPage(${pb.pageSize},${pb.prePageNo})">«</a>
					<c:forEach begin="${pb.startNo }" end="${pb.endNo}" varStatus="sta">
					<!-- 如果当前页码等于开始页码+循环标记-1 那么就是当前页，否则就不是 -->
					<c:if test="${sta.count+pb.startNo-1==pb.pageNo}">
					<a class="current" href="javascript:void(0)" onclick="clickShowPage(${pb.pageSize},${pb.startNo+sta.count-1})">${pb.startNo+sta.count-1}</a>
					</c:if>
					<c:if test="${sta.count+pb.startNo-1!=pb.pageNo}">
					<a href="javascript:void(0)" onclick="clickShowPage(${pb.pageSize},${pb.startNo+sta.count-1})">${pb.startNo+sta.count-1}</a>
					</c:if>
					</c:forEach>
				<a href="javascript:void(0)" onclick="clickShowPage(${pb.pageSize},${pb.nextPageNo})">»</a>
			</td></tr>
		</tfoot>
	</table>
	</form>
	</div>
	<div id="dialog-confirm" title="操作提示">
		<span>你确定要删除吗?</span>
	</div>
	<div id="dialog-tip" title="操作提示">
		<span class="tip">请先选择一行!</span>
	</div>
	<!--添加  -->
	<div id="dialog-form">
		<form id="addForm" action="addGoods.htm" method="post">
			<p>商品名称: <input type="text" name="goodsName" id="goodsName" ></p>
			<p>商品品牌:
				<select name="brandId" id="goodsBrandName">
				</select>
			<p>商品编号 <input name="goodsNo" id="goodsNo" /></p>
			<p>商品价格 <input name="goodsPrice" id="goodsPrice" /></p>
		</form>
	</div>
</body>
</html>