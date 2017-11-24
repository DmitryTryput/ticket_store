<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add CinemaHall</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/add-cinemahall" method="post">
    <select name="cinemaId">
        <c:forEach var="cinema" items="${requestScope.cinemas}">
            <option value="${cinema.id}">${cinema.title}</option>
        </c:forEach>
    </select>
    <p>Название зала:</p>
    <input type="text" name="title" ><br>
    <p>Количетво рядов:</p>
    <input type="number" name="rows" ><br>
    <p>Количество сидений:</p>
    <input type="number" name="row_seats"><br>
    <button type="submit">save</button>
</form>
</body>
</html>
