<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upcoming</title>
</head>
<body>
<%@include file="header.jsp"%><br>


<c:forEach var="cinema" items="${requestScope.seances}">
<p>Кинотеатр: ${cinema.id} ${cinema.title}</p>
    <c:forEach var="cinemahall" items="${cinema.halls}">
<p>    Кинозал: ${cinemahall.id} ${cinemahall.title} </p>
        <c:forEach var="seance" items="${cinemahall.seances}">
<p>          Сеанс: ${seance.id} ${seance.movie.title} ${seance.seanceDate} ${seance.seanceTime} ${seance.price} </p>
        </c:forEach>
    </c:forEach>
</c:forEach>
</body>
</html>


