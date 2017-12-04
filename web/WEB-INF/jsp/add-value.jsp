
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add value</title>
</head>
<body>
<%@include file="header.jsp"%><br>

<form action="${pageContext.request.contextPath}/add-value" method="post">
<p>Внести на счет:</p>
<input type="number" required step="any" min="0" name="value"><br>
    <button type="submit">Добавить</button>
</form>
</body>
</html>
