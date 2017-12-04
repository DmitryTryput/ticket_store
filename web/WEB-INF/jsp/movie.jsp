<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie</title>
</head>
<body>
<%@include file="header.jsp"%><br>



<p>Название: ${requestScope.movie.title}</p>
<p>Год выпуска: ${requestScope.movie.createDate}</p>
<p>Страна: ${requestScope.movie.country.name}</p>
<p>Режисер: ${requestScope.movie.director.firstName} ${requestScope.movie.director.lastName}</p>
<p>Жанры: </p>
<c:forEach var="genre" items="${requestScope.movie.genres}">
    <p>   ${genre.name}  </p>
</c:forEach>
Актеры:
<c:forEach var="actor" items="${requestScope.movie.actors}">
    <p>     ${actor.firstName} ${actor.lastName}</p>
</c:forEach>
</body>
</html>
