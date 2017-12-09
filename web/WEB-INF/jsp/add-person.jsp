<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Hero</title>
</head>
<body>
<%@include file="header.jsp"%>
<form action="${pageContext.request.contextPath}/add-person" method="post">
    <p>Имя:</p>
    <input type="text" maxlength="250" name="firstName"><br>
    <p>Фамилия:</p>
    <input type="text" maxlength="250" name="lastName"><br>
    <p>Дата рождения:</p>
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
    </select><br><br>
    <p>Гражданство: </p>
    <select name="countryId">
        <c:forEach var="country" items="${requestScope.countries}">
            <option value="${country.id}">${country.name}</option>
        </c:forEach>
    </select><br><br>
    <button type="submit">Сохранить</button>
</form>
</body>
</html>


