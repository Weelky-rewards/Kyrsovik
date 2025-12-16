<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Вход в систему</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h2>Вход в систему</h2>

    <c:if test="${param.error != null}">
        <div class="error">Неверное имя пользователя или пароль</div>
    </c:if>

    <c:if test="${param.logout != null}">
        <div class="success">Вы успешно вышли из системы</div>
    </c:if>

    <form method="post" action="/auth/login">
        <div class="form-group">
            <label for="username">Имя пользователя:</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn">Войти</button>
    </form>

    <p>Нет аккаунта? <a href="/auth/register">Зарегистрироваться</a></p>
</div>
</body>
</html>