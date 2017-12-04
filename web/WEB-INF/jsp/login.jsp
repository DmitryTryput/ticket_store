
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <p>Почтовый адресс:</p>
    <input type="email" required name="email"><br>
    <p>Пароль:</p>
    <input type="password" required name="password"><br>
    <button type="submit">Login</button>
</form>
<form action="${pageContext.request.contextPath}/register">
    <button type="submit">Register</button>
</form>
<p> ${requestScope.result}</p><br>

</body>
</html>
