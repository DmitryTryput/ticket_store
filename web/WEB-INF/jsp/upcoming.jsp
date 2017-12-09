<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upcoming</title>
</head>
<body>
<%@include file="header.jsp"%><br>


<ul><c:forEach var="cinema" items="${requestScope.seances}">

    <li>
        <p>Кинотеатр: ${cinema.id} ${cinema.title}</p>

            <c:forEach var="cinemahall" items="${cinema.halls}">
                <ul>
    <p>    Кинозал: ${cinemahall.id} ${cinemahall.title} </p>
    <li>
        <c:forEach var="seance" items="${cinemahall.seances}">
            <p><a href="${pageContext.request.contextPath}/movie?id=${seance.movie.id}"> ${seance.movie.title}</a> <p>Дата: <a href="${pageContext.request.contextPath}/buy-tickets?id=${seance.id}">${seance.seanceDate} ${seance.seanceTime}</a> Цена: ${seance.price} </p>
    </li>
            </c:forEach>
        </ul>
    </c:forEach>
    </li>
</c:forEach>
    </ul>
</body>
</html>


