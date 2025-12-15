<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h2>Регистрация</h2>

    <form:form method="post" modelAttribute="user">
        <div class="form-group">
            <form:label path="username">Имя пользователя:</form:label>
            <form:input path="username" required="true"/>
            <form:errors path="username" cssClass="error"/>
            <c:if test="${usernameError != null}">
                <div class="error">${usernameError}</div>
            </c:if>
        </div>

        <div class="form-group">
            <form:label path="email">Email:</form:label>
            <form:input path="email" type="email" required="true"/>
            <form:errors path="email" cssClass="error"/>
            <c:if test="${emailError != null}">
                <div class="error">${emailError}</div>
            </c:if>
        </div>

        <div class="form-group">
            <form:label path="password">Пароль:</form:label>
            <form:password path="password" required="true"/>
            <form:errors path="password" cssClass="error"/>
        </div>

        <div class="form-group">
            <label for="confirmPassword">Подтвердите пароль:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <c:if test="${passwordError != null}">
                <div class="error">${passwordError}</div>
            </c:if>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn">Зарегистрироваться</button>
    </form:form>

    <p>Уже есть аккаунт? <a href="/auth/login">Войти</a></p>
</div>
</body>
</html>