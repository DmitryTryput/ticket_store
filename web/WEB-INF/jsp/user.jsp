<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
</head>
<body>
<%@include file="header.jsp"%><br>

<c:forEach var="ticket" items="${requestScope.tickets}">
    <p><input type="checkbox" name="tickets" value="${ticket.id}">
    <p> Фильм: ${ticket.movieBaseInfoDto.title}</p>
    <p> Дата: ${ticket.movieBaseInfoDto.title}</p>
    <p> Ряд: ${ticket.row}   Место: ${ticket.seat} Цена: ${ticket.seanceBasicInfoDto.price}</p>
    <p> Дата: ${ticket.seanceBasicInfoDto.date}   Время: ${ticket.seanceBasicInfoDto.time}</p>
    </p>
</c:forEach>
</body>
</html>
