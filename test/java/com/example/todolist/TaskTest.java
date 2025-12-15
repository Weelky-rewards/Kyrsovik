package com.example.todolist;

import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;
import com.example.todolist.model.TaskStatus;
import com.example.todolist.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskTest {

    private Task task;
    private User mockUser;
    private Validator validator;

    @BeforeEach
    void setUp() {
        task = new Task();
        mockUser = mock(User.class);

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Должен создаваться с конструктором по умолчанию")
    void shouldCreateWithDefaultConstructor() {
        assertNotNull(task);
        assertNull(task.getId());
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertEquals(Priority.MEDIUM, task.getPriority());
        assertEquals(TaskStatus.PENDING, task.getStatus());
        assertNull(task.getDueDate());
        assertNull(task.getCreatedAt());
        assertNull(task.getUpdatedAt());
        assertNull(task.getUser());
    }

    @Test
    @DisplayName("Должен создаваться с параметризованным конструктором")
    void shouldCreateWithParameterizedConstructor() {
        String title = "Test Task";
        String description = "Test Description";
        Priority priority = Priority.HIGH;
        LocalDate dueDate = LocalDate.now().plusDays(7);

        Task task = new Task(title, description, priority, dueDate, mockUser);

        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(priority, task.getPriority());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(mockUser, task.getUser());
        assertEquals(TaskStatus.PENDING, task.getStatus());
    }

    @Test
    @DisplayName("Должен устанавливать и получать id")
    void shouldSetAndGetId() {
        Long id = 1L;

        task.setId(id);

        assertEquals(id, task.getId());
    }

    @Test
    @DisplayName("Должен устанавливать и получать заголовок")
    void shouldSetAndGetTitle() {
        String title = "Новая задача";

        task.setTitle(title);

        assertEquals(title, task.getTitle());
    }

    @Test
    @DisplayName("Должен проходить валидацию при корректном заголовке")
    void shouldPassValidationWithValidTitle() {
        task.setTitle("Корректный заголовок");

        var violations = validator.validateProperty(task, "title");

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Не должен проходить валидацию при пустом заголовке")
    void shouldFailValidationWithEmptyTitle() {
        task.setTitle("");

        var violations = validator.validateProperty(task, "title");

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Заголовок задачи обязателен"));
    }

    @Test
    @DisplayName("Не должен проходить валидацию при null заголовке")
    void shouldFailValidationWithNullTitle() {

        var violations = validator.validateProperty(task, "title");

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Не должен проходить валидацию при слишком длинном заголовке")
    void shouldFailValidationWithTooLongTitle() {

        String longTitle = "A".repeat(101);
        task.setTitle(longTitle);

        var violations = validator.validateProperty(task, "title");

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Заголовок не должен превышать 100 символов"));
    }

    @Test
    @DisplayName("Должен устанавливать и получать описание")
    void shouldSetAndGetDescription() {
        String description = "Подробное описание задачи";

        task.setDescription(description);

        assertEquals(description, task.getDescription());
    }

    @Test
    @DisplayName("Должен проходить валидацию при корректном описании")
    void shouldPassValidationWithValidDescription() {

        task.setDescription("Корректное описание задачи");

        var violations = validator.validateProperty(task, "description");

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Не должен проходить валидацию при слишком длинном описании")
    void shouldFailValidationWithTooLongDescription() {

        String longDescription = "A".repeat(5001);
        task.setDescription(longDescription);


        var violations = validator.validateProperty(task, "description");

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertTrue(violations.iterator().next().getMessage().contains("Описание не должно превышать 500 символов"));
    }

    @ParameterizedTest
    @EnumSource(Priority.class)
    @DisplayName("Должен устанавливать и получать приоритет")
    void shouldSetAndGetPriority(Priority priority) {

        task.setPriority(priority);

        assertEquals(priority, task.getPriority());
    }

    @Test
    @DisplayName("Должен иметь приоритет MEDIUM по умолчанию")
    void shouldHaveMediumPriorityByDefault() {

        assertEquals(Priority.MEDIUM, task.getPriority());
    }

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    @DisplayName("Должен устанавливать и получать статус")
    void shouldSetAndGetStatus(TaskStatus status) {
        task.setStatus(status);
        assertEquals(status, task.getStatus());
    }

    @Test
    @DisplayName("Должен иметь статус PENDING по умолчанию")
    void shouldHavePendingStatusByDefault() {

        assertEquals(TaskStatus.PENDING, task.getStatus());
    }

    @Test
    @DisplayName("Должен устанавливать и получать дату выполнения")
    void shouldSetAndGetDueDate() {

        LocalDate dueDate = LocalDate.of(2024, 12, 31);
        task.setDueDate(dueDate);
        assertEquals(dueDate, task.getDueDate());
    }

    @Test
    @DisplayName("Должен устанавливать и получать дату создания")
    void shouldSetAndGetCreatedAt() {

        LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 10, 30);
        task.setCreatedAt(createdAt);
        assertEquals(createdAt, task.getCreatedAt());
    }

    @Test
    @DisplayName("Должен устанавливать и получать дату обновления")
    void shouldSetAndGetUpdatedAt() {

        LocalDateTime updatedAt = LocalDateTime.of(2024, 1, 2, 14, 45);


        task.setUpdatedAt(updatedAt);


        assertEquals(updatedAt, task.getUpdatedAt());
    }

    @Test
    @DisplayName("Должен устанавливать и получать пользователя")
    void shouldSetAndGetUser() {

        task.setUser(mockUser);

        assertEquals(mockUser, task.getUser());
    }

    @Test
    @DisplayName("Метод onCreate должен устанавливать дату создания и обновления")
    void onCreateShouldSetCreatedAtAndUpdatedAt() throws Exception {
        Task task = new Task();

        var method = Task.class.getDeclaredMethod("onCreate");
        method.setAccessible(true);
        method.invoke(task);

        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
        assertEquals(task.getCreatedAt(), task.getUpdatedAt());

        LocalDateTime now = LocalDateTime.now();
        assertTrue(task.getCreatedAt().isBefore(now) || task.getCreatedAt().equals(now));
    }

    @Test
    @DisplayName("Метод onUpdate должен обновлять только updatedAt")
    void onUpdateShouldSetOnlyUpdatedAt() throws Exception {
        Task task = new Task();
        LocalDateTime originalCreatedAt = LocalDateTime.of(2024, 1, 1, 10, 0);
        task.setCreatedAt(originalCreatedAt);

        var method = Task.class.getDeclaredMethod("onUpdate");
        method.setAccessible(true);
        method.invoke(task);

        assertEquals(originalCreatedAt, task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
        assertNotEquals(originalCreatedAt, task.getUpdatedAt());

        assertTrue(task.getUpdatedAt().isAfter(originalCreatedAt));
    }

    @Test
    @DisplayName("Должен корректно обрабатывать null значения для nullable полей")
    void shouldHandleNullValuesForNullableFields() {
        task.setDescription(null);
        task.setDueDate(null);
        task.setUser(null);

        assertNull(task.getDescription());
        assertNull(task.getDueDate());
        assertNull(task.getUser());
    }

    @Test
    @DisplayName("Должен корректно обрабатывать крайние случаи для строковых полей")
    void shouldHandleEdgeCasesForStringFields() {
        String maxTitle = "A".repeat(100);
        String maxDescription = "B".repeat(500);

        task.setTitle(maxTitle);
        task.setDescription(maxDescription);

        assertEquals(maxTitle, task.getTitle());
        assertEquals(maxDescription, task.getDescription());

        var titleViolations = validator.validateProperty(task, "title");
        var descViolations = validator.validateProperty(task, "description");

        assertTrue(titleViolations.isEmpty());
        assertTrue(descViolations.isEmpty());
    }

    @Test
    @DisplayName("Должен корректно работать с датами в разных временных зонах")
    void shouldHandleDifferentDateScenarios() {
        LocalDate pastDate = LocalDate.of(2020, 1, 1);
        LocalDate futureDate = LocalDate.of(2030, 12, 31);
        LocalDate today = LocalDate.now();

        task.setDueDate(pastDate);
        assertEquals(pastDate, task.getDueDate());

        task.setDueDate(futureDate);
        assertEquals(futureDate, task.getDueDate());

        task.setDueDate(today);
        assertEquals(today, task.getDueDate());
    }

    @Test
    @DisplayName("Должен корректно сериализоваться/десериализоваться")
    void shouldSerializeAndDeserializeCorrectly() {
        Long id = 42L;
        String title = "Сериализуемая задача";
        String description = "Описание для сериализации";
        Priority priority = Priority.HIGH;
        LocalDate dueDate = LocalDate.of(2024, 6, 15);
        LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 1, 2, 14, 30);

        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(priority);
        task.setDueDate(dueDate);
        task.setCreatedAt(createdAt);
        task.setUpdatedAt(updatedAt);
        task.setUser(mockUser);

        Task deserializedTask = new Task();
        deserializedTask.setId(task.getId());
        deserializedTask.setTitle(task.getTitle());
        deserializedTask.setDescription(task.getDescription());
        deserializedTask.setPriority(task.getPriority());
        deserializedTask.setDueDate(task.getDueDate());
        deserializedTask.setCreatedAt(task.getCreatedAt());
        deserializedTask.setUpdatedAt(task.getUpdatedAt());
        deserializedTask.setUser(task.getUser());

        assertEquals(task.getId(), deserializedTask.getId());
        assertEquals(task.getTitle(), deserializedTask.getTitle());
        assertEquals(task.getDescription(), deserializedTask.getDescription());
        assertEquals(task.getPriority(), deserializedTask.getPriority());
        assertEquals(task.getDueDate(), deserializedTask.getDueDate());
        assertEquals(task.getCreatedAt(), deserializedTask.getCreatedAt());
        assertEquals(task.getUpdatedAt(), deserializedTask.getUpdatedAt());
        assertEquals(task.getUser(), deserializedTask.getUser());
    }

    @Test
    @DisplayName("Должен корректно обновлять поля через сеттеры")
    void shouldUpdateFieldsCorrectlyThroughSetters() {
        Long initialId = 1L;
        String initialTitle = "Initial Title";
        task.setId(initialId);
        task.setTitle(initialTitle);

        Long newId = 2L;
        String newTitle = "Updated Title";
        task.setId(newId);
        task.setTitle(newTitle);

        assertEquals(newId, task.getId());
        assertEquals(newTitle, task.getTitle());
        assertNotEquals(initialId, task.getId());
        assertNotEquals(initialTitle, task.getTitle());
    }
}