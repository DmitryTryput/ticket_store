<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
<%@include file="header.jsp"%><br>
<p> Имя: ${requestScope.user.firstName}</p>
<p> Фамилия: ${requestScope.user.lastName}</p>
<p>Оставленные комментарии:</p>
<c:forEach var="review" items="${requestScope.user.reviews}">
    <p> Фильм:<a href="${pageContext.request.contextPath}/movie?id=${review.movie.id}"> ${review.movie.title}</a></p>
    <p> Отзыв: ${review.text}</p>
</c:forEach>
</body>
</html>
