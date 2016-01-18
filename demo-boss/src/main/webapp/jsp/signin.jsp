<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="login-bg">
<head>
    <base href="<%=basePath%>">
    <title>登录</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- bootstrap -->
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet"/>
    <link href="css/bootstrap/bootstrap-responsive.css" rel="stylesheet"/>
    <link href="css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet"/>

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="css/layout.css"/>
    <link rel="stylesheet" type="text/css" href="css/elements.css"/>
    <link rel="stylesheet" type="text/css" href="css/icons.css"/>

    <!-- libraries -->
    <link rel="stylesheet" type="text/css" href="css/lib/font-awesome.css"/>

    <!-- this page specific styles -->
    <link rel="stylesheet" href="css/compiled/signin.css" type="text/css" media="screen"/>

    <!-- open sans font -->
    <link href='http://fonts.useso.com/css?family=Open+Sans:300italic,400italic,700italic,800italic,400,300,600,700,800'
          rel='stylesheet' type='text/css'/>

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>


<!-- background switcher -->
<div class="bg-switch visible-desktop">
    <div class="bgs">
        <a href="#" data-img="landscape.jpg" class="bg active">
            <img src="img/bgs/landscape.jpg"/>
        </a>
        <a href="#" data-img="blueish.jpg" class="bg">
            <img src="img/bgs/blueish.jpg"/>
        </a>
        <a href="#" data-img="7.jpg" class="bg">
            <img src="img/bgs/7.jpg"/>
        </a>
        <a href="#" data-img="8.jpg" class="bg">
            <img src="img/bgs/8.jpg"/>
        </a>
        <a href="#" data-img="9.jpg" class="bg">
            <img src="img/bgs/9.jpg"/>
        </a>
        <a href="#" data-img="10.jpg" class="bg">
            <img src="img/bgs/10.jpg"/>
        </a>
        <a href="#" data-img="11.jpg" class="bg">
            <img src="img/bgs/11.jpg"/>
        </a>
    </div>
</div>


<div class="row-fluid login-wrapper">
    <a href="javascript:;">
        <img class="logo" src="img/logo-white.png"/>
    </a>

    <div class="span4 box">
        <div class="content-wrap">
            <h6>登 录</h6>

            <form action="#" method="post" id="loginForm" onsubmit="return false;">
                <input class="span12" type="text" name="username" id="username" placeholder="用户名"/>
                <input class="span12" type="password" name="password" id="password" placeholder="密码"/>
                <input class="span6" name="captcha" id="captcha" style="float: left;margin-top: 10px;" type="text" placeholder="验证码"/>
                <img id="captchaImg" src="patchca.htm" alt="验证码" title="点击换一张图片"
                     style="cursor:pointer;float: right;margin-top: 10px;height: 38px;margin-bottom: 20px;"
                     onclick="this.src=this.src+'?'+Math.random(); "/>

                <div style="display: none;color: red;font-size:14px;margin-top: 70px;margin-bottom: 10px;" id="errortips"></div>
                <a class="btn-glow primary login" href="javascript:;" onclick="login()">登 录</a>
            </form>
        </div>
    </div>
</div>

<!-- scripts -->
<script src="js/jquery-latest.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/theme.js"></script>

<!-- pre load bg imgs -->
<script type="text/javascript">
    $(function () {
        // bg switcher
        var $btns = $(".bg-switch .bg");
        $btns.click(function (e) {
            e.preventDefault();
            $btns.removeClass("active");
            $(this).addClass("active");
            var bg = $(this).data("img");

            $("html").css("background-image", "url('img/bgs/" + bg + "')");
        });

    });
    document.onkeydown = function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        if (e && e.keyCode == 13) { // enter 键
            login();
        }
    };
    function login() {
        $("#errortips").html("");
        $("#errortips").hide();
        $.ajax({
            type: "post",
            context: document.body,
            async:false,
            data:{username:$("#username").val(),password:$("#password").val(),captcha:$("#captcha").val()},
            url: "login.htm",
            success: function (data) {
                if (data == 1) {
                    window.location.href = "index.htm";
                } else if (data == 2) {
                    $("#errortips").html("验证码错误！");
                    $("#errortips").show();
                    $("#captchaImg").click();
                } else if (data == 3){
                    $("#errortips").html("用户名或密码错误！");
                    $("#errortips").show();
                    $("#captchaImg").click();
                }
            }
        });
    }
</script>

</body>
</html>