<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript">
		document.onkeydown=function(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
		  	if(e && e.keyCode==13){ // enter 键
		     	login();
		    }
		};
		var emp = null;
		function getPatcha(){
			$.ajax({
				url: "patchcaSession.htm", 
				context: document.body, 
				success: function(data){
					emp = data;
				}});
		}
		function login(){
			if(checkAll()){
				doLogin();
			}
		}
		function doLogin(){
			var type="";
			if($(".re").prop("checked")){
				type="0";
			}
			if($(".au").prop("checked")){
				if($(".re").prop("checked")){
					type="1";
				}
			}
			var enterValue = $("#captcha").val();
			if(enterValue==emp){
				$.ajax({
					type:"post",
					url:"login.htm?username="+$("#username").val()+"&password="+$("#password").val()+"&type="+type,
					success:function(data){
						if(data==1){
							window.location.href="list.html";
						}else{
							alert("用户名或密码错误！");
							getPatcha();
						}
					}
				});
			}else{
				alert("验证码错误！");
				getPatcha();
			}
		}
		function checkAll(){
			if(checkUser()&&checkPwd()&&checkCaptcha()){
				return true;
			}
			return false;
		}
		function checkUser(){
			if($("#username").val()==""){
				alert("用户名不能为空！");
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
		
		function checkCaptcha(){
			if($("#captcha").val()==""){
				alert("验证码不能为空！");
				return false;
			}
			return true;
		}
</script>
</head>
<body>
	<div>
		<h4>用户登录</h4>
			<form action="#" method="post" id="loginForm">
				<table>
					<tr><td>用户名:</td><td><input type="text" name="username" id="username" value="${username}" /></tr>
					<tr><td>密码:</td><td><input type="password" name="password" id="password"/></td></tr>
					<tr><td></td><td><img id="captchaImg" src="patchca.htm"alt="验证码" title="点击换一张图片" style="cursor:pointer;" onclick="this.src=this.src+'?'+Math.random(); "/></td></tr>
					<tr><td>验证码:</td><td><input type="text" name="captcha" id="captcha" onfocus="getPatcha()" /></td></tr>
					<tr><td><input type="checkbox" name="remember" class="re" /><label>记住账号</label>
                    <input type="checkbox" name="autologin" class="au" /><label>自动登录</label></td></tr>
					<tr><td><input type="button" value="登陆" onclick="login()"/></td>
						<td><input type="button" value="注册" onclick="javascript:location.href='register.html'"/></td></tr>
				</table>
			</form>
	</div>
</body>
</html>