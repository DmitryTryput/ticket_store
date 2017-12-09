<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add Seance</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/add-seance" method="post">
    <%@include file="header.jsp"%>
    <p>Фильм:</p>
    <select name="movies">
        <c:forEach var="movie" items="${requestScope.movies}">
            <option value="${movie.id}">${movie.title}</option>
        </c:forEach>
    </select>


    <p>Дата:
    <select name="day">
        <c:forEach var="day" items="${requestScope.days}">
            <option value="${day}">${day}</option>
        </c:forEach>
    </select>

    <select name="month">
        <c:forEach var="month" items="${requestScope.months}">
            <option value="${month}">${month}</option>
        </c:forEach>
    </select>
    <select name="year">
        <c:forEach var="year" items="${requestScope.years}">
            <option value="${year}">${year}</option>
        </c:forEach>
    </select>
    </p>
    <p>Время:
    <select name="hour">
        <c:forEach var="hour" items="${requestScope.hours}">
            <option value="${hour}">${hour}</option>
        </c:forEach>
    </select>
    <select name="minutes">
        <c:forEach var="minutes" items="${requestScope.minutes}">
            <option value="${minutes}">${minutes}</option>
        </c:forEach>
    </select>


    <p>Кинозал: </p>
    <select name="cinemaHall">
        <c:forEach var="cinemahall" items="${requestScope.cinemaHalls}">
            <option value="${cinemahall.id}">${cinemahall.cinema.title} ${cinemahall.title}</option>
        </c:forEach>
    </select>

    <p>Цена:</p>
    <input type="number" required step="any" min="0" name="price">

   <p> <button type="submit">Сохранить</button></p>
</form>
</body>
</html>
