<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册成功</title>
<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script>
	setTimeout(countDown, 1000);
	function countDown(){
		var time = $("#time").text();
		$("#time").text(time - 1);
		if (time == 1) {
			location.href='login.html';
		} else {
			setTimeout(countDown, 1000);
		}
	}
</script>
</head>
<body>
	<h1>注册成功</h1>
	<span><strong><span id="time">5</span>秒自动跳转<a href="login.html"/>
</body>
</html>