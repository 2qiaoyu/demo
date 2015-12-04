<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>demo1</title>
<link rel="stylesheet" href="<%=basePath%>js/jquery-ui-css/jquery-ui-1.10.3.custom.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-ui/jquery-ui-1.10.3.custom.js"></script>
<script type="text/javascript">
	$(function(){
		$("#add").button().click(function(){
			$("#dialog-confirm").dialog("open");
		});
		$("#dialog-confirm").dialog({
			title:"测试",
			width:320,
			height:220,
			autoOpen:false,
			modal:true,
			resizable:false,
			buttons:{
				"确定":function(){
					$(this).dialog("close");
				}
			}
		});
	});
</script>
</head>
<body>
<h2>Hello World!</h2>
<!-- <button id="choose">选择</button> -->
<form method="post" action="upload.htm" enctype="multipart/form-data">
<input type="file" name="uploadFile"/>
<input type="submit" value="上传"/>
</form>
<div id="dialog-confirm">
	<span>OK</span>
</div>
</body>
</html>
