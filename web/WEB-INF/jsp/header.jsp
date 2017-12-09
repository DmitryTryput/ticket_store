<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<table>
    <tr>
        <td>
            <form action="${pageContext.request.contextPath}/user">
                <button type="submit">Кабинет пользователя</button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/upcoming">
                <button type="submit">Премьеры</button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/persons">
                <button type="submit">Список актеров/режиссеров</button>
            </form>
        </td>
        <td>

            <form action="${pageContext.request.contextPath}/movies">
                <button type="submit">Список фильмов</button>
            </form>
        </td>

        <td>

            <form action="${pageContext.request.contextPath}/add-value">
                <button type="submit">Добавить средств</button>
            </form>
        </td>

        <td>

            <form action="${pageContext.request.contextPath}/logged-out">
                <button type="submit">Выйти</button>
            </form>
        </td>
        <td>
            <p>Пользователь: ${sessionScope.firstName} ${sessionScope.lastName}</p>
            <p>Средства: ${sessionScope.value}</p>
        </td>
    </tr>

    <c:if test="${sessionScope.role eq 'Администратор'}">
    <tr>
        <td>
            <form action="${pageContext.request.contextPath}/add-cinema">
                <button type="submit">Добавить кинотеатр</button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/add-cinemahall">
                <button type="submit">Добавить кинозал</button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/add-person">
                <button type="submit">Добавить актера/режиссера</button>
            </form>
        </td>
        <td>

            <form action="${pageContext.request.contextPath}/add-movie">
                <button type="submit">Добавить фильм</button>
            </form>
        </td>

        <td>

            <form action="${pageContext.request.contextPath}/add-seance">
                <button type="submit">Добавить сеанс</button>
            </form>
        </td>
    </tr>
    </c:if>
</table>
</body>
</html>
