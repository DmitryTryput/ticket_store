<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add movie</title>
</head>
<body>
<%@include file="header.jsp"%>
    <form action="${pageContext.request.contextPath}/add-movie" method="post">
        <p>Название фильма:</p>
        <input type="text"  maxlength="250" name="title"><br>
        <p>Год выхода:</p>
        <select name="year">
            <c:forEach var="year" items="${requestScope.years}">
                <option value="${year}">${year}</option>
            </c:forEach>
        </select>
        <p>Режесер:</p>
        <select name="director">
            <c:forEach var="director" items="${requestScope.persons}">
                <option value="${director.id}">${director.firstName} ${director.lastName}</option>
            </c:forEach>
        </select>
        <p>Актеры:</p>
        <c:forEach var="actors" items="${requestScope.persons}">
            ${actors.firstName} ${actors.lastName} <input type="checkbox" name="actors" value="${actors.id}"><br>
        </c:forEach>
        <p>Страна:</p>
        <select name="country">
            <c:forEach var="country" items="${requestScope.countries}">
                <option value="${country.id}">${country.name}</option>
            </c:forEach>
        </select>

        <p>Жанры:</p>
        <c:forEach var="genres" items="${requestScope.genres}">
            ${genres.name} <input type="checkbox" name="genres" value="${genres.id}"><br>
        </c:forEach>

        <button type="submit">Сохранить</button>
    </form>
</body>
</html>
