<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en">
<head>
    <title>login</title>
    <link rel="stylesheet" href="/css/index.css">
    <script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
    <script type="text/javascript" src="/js/index.js"></script>
</head>
<body>
<h2>我是服务器：${pageContext.request.localPort}</h2>
<h2>当前sessionId：${pageContext.session.id}</h2>
<div class="login-panel">
    <form>
        <div class="form-item">
            <label>用户名：</label>
            <input id="username" type="text" />
        </div>
        <div class="form-item">
            <label>密码：</label>
            <input id="password" type="password"/>
        </div>
        <div class="btn-area">
            <button type="button" class="my-btn" id="login-btn">登录</button>
        </div>
    </form>
</div>
</body>
</html>
