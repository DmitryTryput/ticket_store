<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Hero</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/add-person" method="post">
    <p>Имя:</p>
    <input type="text" name="firstName"><br>
    <p>Фамилия:</p>
    <input type="text" name="lastName"><br>
    <p>День </p>
    <input type="number" name="day"><br><br>
    <p>месяц </p>
    <input type="number" name="month"><br><br>
    <p>год </p>
    <input type="number" name="year"><br><br>
    <p>страна </p>
    <input type="text" name="country"><br><br>
    <button type="submit">save</button>
</form>
</body>
</html>


