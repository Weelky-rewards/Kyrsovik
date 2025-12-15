package com.example.todolist;

import com.example.todolist.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TaskStatusTest {

    @Test
    @DisplayName("Должен возвращать правильное количество элементов перечисления")
    void shouldHaveCorrectNumberOfValues() {
        TaskStatus[] values = TaskStatus.values();

        assertEquals(2, values.length, "Должно быть 2 значения статуса задачи");
    }

    @Test
    @DisplayName("Должен содержать ожидаемые значения перечисления")
    void shouldContainExpectedValues() {
        assertAll("Проверка всех значений перечисления",
                () -> assertNotNull(TaskStatus.PENDING, "PENDING должен существовать"),
                () -> assertNotNull(TaskStatus.COMPLETED, "COMPLETED должен существовать")
        );
    }

    @Test
    @DisplayName("Должен возвращать значения в правильном порядке")
    void shouldReturnValuesInCorrectOrder() {
        TaskStatus[] values = TaskStatus.values();

        assertArrayEquals(new TaskStatus[]{TaskStatus.PENDING, TaskStatus.COMPLETED},
                values, "Порядок значений должен соответствовать объявлению");
    }

    @ParameterizedTest
    @EnumSource(TaskStatus.class)
    @DisplayName("Должен возвращать displayName для каждого значения")
    void shouldReturnDisplayNameForEachValue(TaskStatus status) {
        String displayName = status.getDisplayName();
        assertNotNull(displayName, "DisplayName не должен быть null для " + status);
        assertFalse(displayName.trim().isEmpty(),
                "DisplayName не должен быть пустым или состоять из пробелов для " + status);
    }

    @Test
    @DisplayName("Должен возвращать правильные отображаемые имена")
    void shouldReturnCorrectDisplayNames() {
        assertEquals("В процессе", TaskStatus.PENDING.getDisplayName(),
                "Некорректный displayName для PENDING");
        assertEquals("Выполнено", TaskStatus.COMPLETED.getDisplayName(),
                "Некорректный displayName для COMPLETED");
    }

    @Test
    @DisplayName("Должен правильно преобразовывать строку в значение перечисления")
    void shouldParseFromString() {
        assertEquals(TaskStatus.PENDING, TaskStatus.valueOf("PENDING"),
                "Должен правильно парсить 'PENDING'");
        assertEquals(TaskStatus.COMPLETED, TaskStatus.valueOf("COMPLETED"),
                "Должен правильно парсить 'COMPLETED'");
    }

    @Test
    @DisplayName("Должен бросать исключение при попытке парсинга несуществующего значения")
    void shouldThrowExceptionWhenParsingNonExistentValue() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> TaskStatus.valueOf("NON_EXISTENT"),
                "Должно выбрасываться IllegalArgumentException для несуществующего значения");

        assertTrue(exception.getMessage().contains("NON_EXISTENT"),
                "Сообщение об ошибке должно содержать имя несуществующего значения");
    }

    @Test
    @DisplayName("Должен правильно работать с ordinal значениями")
    void shouldHaveCorrectOrdinalValues() {
        assertEquals(0, TaskStatus.PENDING.ordinal(), "Некорректный ordinal для PENDING");
        assertEquals(1, TaskStatus.COMPLETED.ordinal(), "Некорректный ordinal для COMPLETED");
    }

    @ParameterizedTest
    @CsvSource({
            "PENDING, В процессе",
            "COMPLETED, Выполнено"
    })
    @DisplayName("Должен возвращать правильные displayName для каждого значения (параметризованный тест)")
    void shouldReturnCorrectDisplayNameParametrized(String enumName, String expectedDisplayName) {
        TaskStatus status = TaskStatus.valueOf(enumName);
        String actualDisplayName = status.getDisplayName();
        assertEquals(expectedDisplayName, actualDisplayName,
                "Некорректный displayName для " + enumName);
    }

    @Test
    @DisplayName("Метод toString() должен возвращать имя перечисления")
    void toStringShouldReturnEnumName() {
        assertEquals("PENDING", TaskStatus.PENDING.toString(),
                "Некорректный toString() для PENDING");
        assertEquals("COMPLETED", TaskStatus.COMPLETED.toString(),
                "Некорректный toString() для COMPLETED");
    }

    @Test
    @DisplayName("Должен корректно работать с методом name()")
    void shouldReturnCorrectName() {
        assertEquals("PENDING", TaskStatus.PENDING.name(),
                "Некорректный name() для PENDING");
        assertEquals("COMPLETED", TaskStatus.COMPLETED.name(),
                "Некорректный name() для COMPLETED");
    }

    @ParameterizedTest
    @ValueSource(strings = {"PENDING", "COMPLETED"})
    @DisplayName("Должен возвращать правильный объект через valueOf для существующих значений")
    void shouldReturnCorrectEnumValue(String enumName) {
        TaskStatus status = TaskStatus.valueOf(enumName);
        assertNotNull(status, "Статус не должен быть null для " + enumName);
        assertEquals(enumName, status.name(),
                "Имя статуса должно соответствовать исходной строке");
    }

    @Test
    @DisplayName("Должен корректно работать в операторе равенства")
    void shouldWorkCorrectlyWithEqualityOperator() {
        TaskStatus pending1 = TaskStatus.PENDING;
        TaskStatus pending2 = TaskStatus.PENDING;
        TaskStatus completed = TaskStatus.COMPLETED;
        assertSame(pending1, pending2, "Два PENDING должны быть одним и тем же объектом");
        assertNotSame(pending1, completed, "PENDING и COMPLETED должны быть разными объектами");
        assertEquals(pending1, pending2, "Два PENDING должны быть равны");
        assertNotEquals(pending1, completed, "PENDING и COMPLETED не должны быть равны");
    }

    @Test
    @DisplayName("Должен корректно работать с hashCode")
    void shouldHaveConsistentHashCode() {
        TaskStatus pending1 = TaskStatus.PENDING;
        TaskStatus pending2 = TaskStatus.PENDING;

        assertEquals(pending1.hashCode(), pending2.hashCode(),
                "hashCode должен быть одинаковым для одинаковых значений");
    }

    @Test
    @DisplayName("Должен корректно работать с методом ordinal() для использования в массивах")
    void shouldWorkCorrectlyWithOrdinalForArrays() {

        TaskStatus[] statusArray = new TaskStatus[2];

        statusArray[TaskStatus.PENDING.ordinal()] = TaskStatus.PENDING;
        statusArray[TaskStatus.COMPLETED.ordinal()] = TaskStatus.COMPLETED;

        assertEquals(TaskStatus.PENDING, statusArray[0],
                "PENDING должен быть на позиции 0");
        assertEquals(TaskStatus.COMPLETED, statusArray[1],
                "COMPLETED должен быть на позиции 1");
    }

    @Test
    @DisplayName("Должен поддерживать получение всех значений через values()")
    void shouldReturnAllValuesThroughValuesMethod() {

        TaskStatus[] allValues = TaskStatus.values();

        assertEquals(2, allValues.length,
                "Должно возвращаться 2 значения");
        assertArrayEquals(new TaskStatus[]{TaskStatus.PENDING, TaskStatus.COMPLETED},
                allValues, "Должны возвращаться все значения в правильном порядке");
    }

    @Test
    @DisplayName("Должен корректно сериализоваться и десериализоваться")
    void shouldSerializeAndDeserializeCorrectly() {

        TaskStatus original = TaskStatus.PENDING;

        String serialized = original.name();
        TaskStatus deserialized = TaskStatus.valueOf(serialized);

        assertEquals(original, deserialized,
                "Десериализованное значение должно совпадать с оригиналом");
        assertSame(original, deserialized,
                "Десериализация должна возвращать тот же объект из кэша");
    }
}