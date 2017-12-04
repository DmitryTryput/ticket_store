
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/register" method="post">
    <p>Почтовый адресс:</p>
    <input type="email" required name="email"><br>
    <p>Пароль:</p>
    <input type="password" required name="password"><br>
    <p>Повторите пароль:</p>
    <input type="password" required name="checkPassword"><br>
    <p>Имя:</p>
    <input type="text" required name="firstName"><br>
    <p>Фамилия:</p>
    <input type="text" required name="lastName"><br>
    <button type="submit">save</button>
</form>

<p> ${requestScope.result}</p><br>

</body>
</html>
