
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="Login"/>


<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <p><fmt:message key = "login" /></p>
    <input type="email" required name="email"><br>
    <p><fmt:message key = "password" /></p>
    <input type="password" required name="password"><br>
    <p> <button type="submit"><fmt:message key = "enter" /></button></p>
</form>
<form action="${pageContext.request.contextPath}/register">
    <button type="submit"><fmt:message key = "registration" /></button>
</form>
<p> ${requestScope.result}</p><br>

</form>
<form action="${pageContext.request.contextPath}/locale" method="post">
    <input type="radio" name="locale" value="ru_RU" onchange="submit()" ${sessionScope.locale eq 'ru_RU' ? "checked" : ""}><fmt:message key = "ru" />
    <input type="radio" name="locale" value="en_US" onchange="submit()" ${sessionScope.locale eq 'en_US' ? "checked" : ""}><fmt:message key = "en" />
</form>
</body>
</html>
