<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Person</title>
</head>
<body>
<%@include file="header.jsp"%><br>



<p>Имя: ${requestScope.person.firstName} ${requestScope.person.lastName}</p>
<p>Дата рождения: ${requestScope.person.dateOfBirth}</p>
<p>Страна: ${requestScope.person.country.name}</p>
<p>Режисерские работы:</p>
<c:forEach var="directors" items="${requestScope.person.director}">
    <p>   ${directors.title}  </p>
</c:forEach>
Актерские работы:
<c:forEach var="actors" items="${requestScope.person.actor}">
    <p>     ${actors.title}</p>
</c:forEach>
</body>
</html>
