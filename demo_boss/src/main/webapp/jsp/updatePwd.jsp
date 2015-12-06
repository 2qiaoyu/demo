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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	function checkAll(){
		if(checkOldPwd()&&checkNewPwd()&&checkRePwd()){
			return true;
		}
		return false;
	}
	function checkOldPwd(){
		if($("#oldPassword").val()==""){
			alert("旧密码不能为空！");
			return false;
		}else if($("#oldPassword").val()!="${sessionScope.admin.password}"){
			alert("旧密码不正确！");
			return false;
		}
		return true;
	}
	
	function checkNewPwd(){
		if($("#newPassword").val()==""){
			alert("新密码不能为空！");
			return false;
		}
		return true;
	}
	
	function checkRePwd(){
		if($("#rePassword").val()==""){
			alert("确认新密码不能为空！");
			return false;
		}else if($("#rePassword").val()!=$("#newPassword").val()){
			alert("两次输入密码不一致！");
			return false;
		}
		return true;
	}
	function updatePwd(){
		if(checkAll()){
			$("#registerForm").submit();
		}
	}
</script>
</head>
<body>
	<div>
		<h1>修改密码</h1>
		<form action="updatePwd.htm" id="registerForm" method="post">
			<input type="hidden" name="userId" value="${sessionScope.admin.userId}"/>
			<table>
				<tr><td>用户名：</td><td><input type="text" name="username" id="username" 
					value="${sessionScope.admin.userName}" readonly="readonly"/>
				<tr><td>旧密码：</td><td><input type="password" name="oldPassword" id="oldPassword"/></td></tr>
				<tr><td>新密码：</td><td><input type="password" name="newPassword" id="newPassword"/></td></tr>
				<tr><td>确认新密码：</td><td><input type="password" name="rePassword" id="rePassword"/></td></tr>
				<tr><td><input type="button" value="确认" onclick="updatePwd()"/></td>
					<td><input type="reset" value="取消" /></td></tr>
				<tr><td><a href="javascript:history.go(-1)">返回</a></td></tr>
			</table>
		</form>
	</div>
</body>
</html>