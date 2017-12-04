<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movies</title>
</head>
<body>
<%@include file="header.jsp"%><br>
Список всех фильмов:
<c:forEach var="movie" items="${requestScope.movies}">
    <p><a href="${pageContext.request.contextPath}/movie?id=${movie.id}">
            ${movie.title} </a></p>
</c:forEach>
</body>
</html>
