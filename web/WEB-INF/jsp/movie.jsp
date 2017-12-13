<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie</title>
</head>
<body>
<%@include file="header.jsp"%><br>

Основная информация:

<p>Название: ${requestScope.movie.title}</p>
<p>Год выпуска: ${requestScope.movie.createDate}</p>
<p>Страна: ${requestScope.movie.country.name}</p>
<p>Режисер:<a href="${pageContext.request.contextPath}/person?id=${requestScope.movie.director.id}"> ${requestScope.movie.director.firstName} ${requestScope.movie.director.lastName}</a></p>
<p>Жанры: </p>
<c:forEach var="genre" items="${requestScope.movie.genres}">
    <p>   ${genre.name}  </p>
</c:forEach>
Актеры:
<c:forEach var="actor" items="${requestScope.movie.actors}">
    <p> <a href="${pageContext.request.contextPath}/person?id=${actor.id}">    ${actor.firstName} ${actor.lastName}</a></p>
</c:forEach>
Комментарии:
<c:if test="${sessionScope.movie.reviews eq true}">
<c:forEach var="review" items="${requestScope.movie.reviews}">
    <p> Пользователь:  <a href="${pageContext.request.contextPath}/user-profile?id=${review.user.id}">  ${review.user.firstName} ${review.user.lastName}</a></p>
    <p>    Отзыв: ${review.text} </p>
</c:forEach>
</c:if>
<form action="${pageContext.request.contextPath}/add-review?id=${requestScope.id}" method="post">
    <input type="text" maxlength="250" name="review">
    <button type="submit">Добавить комментарий</button>
    </form>
</body>
</html>
