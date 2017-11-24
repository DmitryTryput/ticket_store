
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Cinema</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/add-cinema" method="post">
    <p>Название кинотеатра:</p>
    <input type="text" name="title"><br>
    <button type="submit">save</button>
</form>
</body>
</html>
