<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Persons</title>
</head>
<body>
<%@include file="header.jsp"%><br>
Список всех актеров/режиссеров:
<c:forEach var="person" items="${requestScope.persons}">
    <p><a href="${pageContext.request.contextPath}/person?id=${person.id}">
            ${person.firstName} ${person.lastName}</a></p>
</c:forEach>
</body>
</html>
