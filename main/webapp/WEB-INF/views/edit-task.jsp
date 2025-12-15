<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Редактировать задачу</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h2>Редактировать задачу</h2>

    <form:form method="post" modelAttribute="task">
        <form:hidden path="id"/>

        <div class="form-group">
            <form:label path="title">Заголовок:</form:label>
            <form:input path="title" required="true"/>
            <form:errors path="title" cssClass="error"/>
        </div>

        <div class="form-group">
            <form:label path="description">Описание:</form:label>
            <form:textarea path="description" rows="3"/>
        </div>

        <div class="form-group">
            <form:label path="priority">Приоритет:</form:label>
            <form:select path="priority">
                <form:option value="LOW">Низкий</form:option>
                <form:option value="MEDIUM">Средний</form:option>
                <form:option value="HIGH">Высокий</form:option>
            </form:select>
        </div>

        <div class="form-group">
            <form:label path="status">Статус:</form:label>
            <form:select path="status">
                <form:option value="PENDING">В процессе</form:option>
                <form:option value="COMPLETED">Выполнено</form:option>
            </form:select>
        </div>

        <div class="form-group">
            <form:label path="dueDate">Срок выполнения:</form:label>
            <form:input path="dueDate" type="date"/>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn">Сохранить</button>
            <a href="/tasks" class="btn">Отмена</a>
        </div>
    </form:form>
</div>
</body>
</html>