<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task Manager - Мои задачи</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/css/style1.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<div class="container fade-in">

    <header class="header">
        <div class="header-content">
            <div class="user-info">
                <h1><i class="fas fa-user-circle"></i> Добро пожаловать, ${pageContext.request.userPrincipal.name}!</h1>
                <p>Управляйте своими задачами эффективно</p>
            </div>
            <form action="/auth/logout" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="logout-btn" type="submit">Выйти</button>
            </form>
        </div>
    </header>

    <main class="content">

        <div class="message-container">
            <c:if test="${param.success != null}">
                <div class="success-message">
                    <i class="fas fa-check-circle"></i>
                    <div>
                        <c:choose>
                            <c:when test="${param.success == 'updated'}"><strong>Успешно!</strong> Задача обновлена</c:when>
                            <c:when test="${param.success == 'deleted'}"><strong>Успешно!</strong> Задача удалена</c:when>
                            <c:otherwise><strong>Успешно!</strong> Новая задача создана</c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="error-message">
                    <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                </div>
            </c:if>
        </div>

        <div class="add-task-card">
            <h2><i class="fas fa-plus-circle"></i> Новая задача</h2>

            <form:form method="post" modelAttribute="task" class="task-form">
                <div class="form-row">
                    <div class="form-group">
                        <form:label path="title">
                            <i class="fas fa-heading"></i> Заголовок
                        </form:label>
                        <form:input path="title" required="true"
                                    placeholder="Введите название задачи"/>
                        <form:errors path="title" cssClass="error"/>
                    </div>

                    <div class="form-group">
                        <form:label path="priority">
                            <i class="fas fa-flag"></i> Приоритет
                        </form:label>
                        <form:select path="priority">
                            <form:option value="LOW">Низкий приоритет</form:option>
                            <form:option value="MEDIUM">Средний приоритет</form:option>
                            <form:option value="HIGH">Высокий приоритет</form:option>
                        </form:select>
                    </div>
                </div>

                <div class="form-group">
                    <form:label path="description">
                        <i class="fas fa-align-left"></i> Описание
                    </form:label>
                    <form:textarea path="description"
                                   placeholder="Добавьте описание задачи (необязательно)"/>
                </div>

                <div class="form-group">
                    <form:label path="dueDate">
                        <i class="fas fa-calendar-alt"></i> Срок выполнения
                    </form:label>
                    <form:input path="dueDate" type="date"/>
                </div>

                <button type="submit" class="submit-btn">
                    <i class="fas fa-plus"></i> Добавить задачу
                </button>
            </form:form>
        </div>

        <div class="tasks-section">
            <div class="tasks-header">
                <h2><i class="fas fa-tasks"></i> Мои задачи</h2>
                <div class="tasks-count">
                    Всего: ${taskPage.totalElements}
                </div>
            </div>

            <c:choose>
                <c:when test="${taskPage.totalElements == 0}">
                    <div class="no-tasks">
                        <div class="no-tasks-icon">
                            <i class="fas fa-clipboard-list"></i>
                        </div>
                        <h3>Пока нет задач</h3>
                        <p>Добавьте свою первую задачу используя форму выше</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="tasks-grid">
                        <c:forEach items="${taskPage.content}" var="task">
                            <div class="task-card ${task.status == 'COMPLETED' ? 'completed' : ''}">
                                <div class="task-header">
                                    <h3 class="task-title">${task.title}</h3>
                                    <span class="task-priority priority-${task.priority.name().toLowerCase()}">
                                        <i class="fas fa-flag"></i> ${task.priority.displayName}
                                    </span>
                                </div>

                                <c:if test="${not empty task.description}">
                                    <p class="task-description">${task.description}</p>
                                </c:if>

                                <div class="task-meta">
                                    <span class="meta-item">
                                        <i class="fas fa-info-circle"></i>
                                        <span class="status-badge status-${task.status.name().toLowerCase()}">
                                            <c:choose>
                                                <c:when test="${task.status == 'PENDING'}">
                                                    <i class="fas fa-clock"></i> В процессе
                                                </c:when>
                                                <c:when test="${task.status == 'COMPLETED'}">
                                                    <i class="fas fa-check"></i> Выполнено
                                                </c:when>
                                            </c:choose>
                                        </span>
                                    </span>

                                    <c:if test="${task.dueDate != null}">
                                        <span class="meta-item">
                                            <i class="fas fa-calendar-day"></i>
                                            Срок: ${task.dueDate}
                                        </span>
                                    </c:if>

                                    <span class="meta-item">
                                        <i class="fas fa-clock"></i>
                                        Создано: ${task.createdAt.toLocalDate()}
                                    </span>
                                </div>

                                <div class="task-actions">
                                    <a href="/tasks/toggle/${task.id}"
                                       class="action-btn primary">
                                        <c:choose>
                                            <c:when test="${task.status == 'PENDING'}">
                                                <i class="fas fa-check"></i> Выполнить
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-redo"></i> В работу
                                            </c:otherwise>
                                        </c:choose>
                                    </a>

                                    <a href="/tasks/edit/${task.id}"
                                       class="action-btn secondary">
                                        <i class="fas fa-edit"></i> Редактировать
                                    </a>

                                    <a href="/tasks/delete/${task.id}"
                                       onclick="return confirm('Вы уверены, что хотите удалить эту задачу?')"
                                       class="action-btn danger">
                                        <i class="fas fa-trash"></i> Удалить
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>

            <c:if test="${taskPage.totalPages > 1}">
                <div class="pagination">
                    <c:if test="${!taskPage.first}">
                        <a href="/tasks?page=${taskPage.number}" class="pagination-btn">
                            <i class="fas fa-chevron-left"></i> Назад
                        </a>
                    </c:if>

                    <span class="pagination-info">
                        Страница ${taskPage.number + 1} из ${taskPage.totalPages}
                    </span>

                    <c:if test="${!taskPage.last}">
                        <a href="/tasks?page=${taskPage.number + 2}" class="pagination-btn">
                            Вперед <i class="fas fa-chevron-right"></i>
                        </a>
                    </c:if>
                </div>
            </c:if>
        </div>
    </main>

</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const elements = document.querySelectorAll('.task-card');
        elements.forEach((el, index) => {
            el.style.animationDelay = `${index * 0.1}s`;
        });

        const deleteButtons = document.querySelectorAll('a[href*="/delete/"]');
        deleteButtons.forEach(btn => {
            btn.addEventListener('click', function(e) {
                if (!confirm('Вы уверены, что хотите удалить эту задачу?\nЭто действие нельзя отменить.')) {
                    e.preventDefault();
                }
            });
        });
    });
</script>
</body>
</html>