<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.js"></script>
<script type="text/javascript">
	function checkAll(){
		if(checkUser()&&checkPwd()){
			return true;
		}
		return false;
	}
	function checkUser(){
		if($("#username").val()==""){
			alert("用户名不能为空！");
			return false;
		}else if($("#hidden").val()=="1"){
			alert("用户名已存在！");
			return false;
		}
		return true;
	}
	
	function checkPwd(){
		if($("#password").val()==""){
			alert("密码不能为空！");
			return false;
		}
		return true;
	}
	function checkUserName(){
		var username=$("#username").val();
		$.ajax({
			type:"post",
			url:"checkUserName.htm?username="+username,
			success:function(result){
				if(result==1){
					$("#checkUserName").html("用户名已存在");
					$("#hidden").val("1");
				}else{
					$("#checkUserName").html("用户名可以注册");
					$("#hidden").val("0");
				}
			}
		});
		}
	function register(){
		if(checkAll()){
			$("#registerForm").submit();
		}
	}
</script>
</head>
<body>
	<div>
		<h1>注册页面</h1>
		<form action="addUser.htm" id="registerForm" method="post">
			<table>
				<tr><td>用户名：</td><td><input type="text" name="username" id="username" onchange="checkUserName()"/>
										</td><td><div id="checkUserName"></div></td></tr>
				<tr><td>密码：</td><td><input type="text" name="password" id="password"/></td></tr>
				<tr><td><input type="button" value="注册" onclick="register()"/></td>
					<td><input type="reset" value="重置" /></td></tr>
				<tr><td><a href="login.html" >返回登录页面</a></td></tr>
				<tr><td><input type="hidden" id="hidden" value="" /></td></tr>
			</table>
		</form>
	</div>
</body>
</html>