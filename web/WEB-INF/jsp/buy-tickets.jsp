<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Buy Tickets</title>
</head>
<body>
<%@include file="header.jsp"%><br>

<p>${requestScope.tickets.cinemaHall.cinema.title}</p>
<p>${requestScope.tickets.cinemaHall.title}</p>
<p>${requestScope.tickets.movie.title} ${requestScope.tickets.seanceDate} ${requestScope.tickets.seanceTime}</p>


<form action="${pageContext.request.contextPath}/confirm?id=${requestScope.tickets.id}&price=${requestScope.tickets.price}" method="post">
    <c:set var="startIndex" scope="page" value="1"/>
    <c:set var="endIndex" scope="page" value="${requestScope.tickets.cinemaHall.seats}"/>



        <c:forEach begin="${startIndex}" end="${endIndex}" step="1" var="index">
            &nbsp; ${index}  &nbsp;

        </c:forEach><br>
<c:forEach var="row" items="${requestScope.tickets.rowSeatTickets}">
    ${row.key} <c:forEach var="seat" items="${row.value}">
     <c:if test="${seat.value.purchased eq true}">
        <input type="checkbox" disabled name="seat" value="${seat.value.id}">
    </c:if>
        <c:if test="${seat.value.purchased eq false}">
            <input type="checkbox"  name="seat" value="${seat.value.id}">
        </c:if>
</c:forEach><br>
</c:forEach>
    <button type="submit">Купить билеты</button>
</form>
${result}
</body>
</html>

