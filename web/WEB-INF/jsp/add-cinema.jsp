
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Cinema</title>
</head>
<body>

<%@include file="header.jsp"%>

<form action="${pageContext.request.contextPath}/add-cinema" method="post">
    <p>Название кинотеатра:</p>
    <input type="text" maxlength="250" name="title"><br>
    <button type="submit">Сохранить</button>
</form>
</body>
</html>
