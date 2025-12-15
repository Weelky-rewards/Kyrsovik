package com.example.todolist;

import com.example.todolist.model.Priority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.CsvSource;

class PriorityTest {

    @Test
    @DisplayName("Должен возвращать правильное количество элементов перечисления")
    void shouldHaveCorrectNumberOfValues() {
        Priority[] values = Priority.values();

        Assertions.assertEquals(3, values.length, "Должно быть 3 значения приоритета");
    }

    @Test
    @DisplayName("Должен содержать ожидаемые значения перечисления")
    void shouldContainExpectedValues() {
        Assertions.assertAll("Проверка всех значений перечисления",
                () -> Assertions.assertNotNull(Priority.LOW, "LOW должен существовать"),
                () -> Assertions.assertNotNull(Priority.MEDIUM, "MEDIUM должен существовать"),
                () -> Assertions.assertNotNull(Priority.HIGH, "HIGH должен существовать")
        );
    }

    @ParameterizedTest
    @EnumSource(Priority.class)
    @DisplayName("Должен возвращать displayName для каждого значения")
    void shouldReturnDisplayNameForEachValue(Priority priority) {
        String displayName = priority.getDisplayName();

        Assertions.assertNotNull(displayName, "DisplayName не должен быть null");
        Assertions.assertFalse(displayName.trim().isEmpty(), "DisplayName не должен быть пустым или состоять из пробелов");
    }

    @Test
    @DisplayName("Должен возвращать правильные отображаемые имена")
    void shouldReturnCorrectDisplayNames() {
        Assertions.assertEquals("Низкий", Priority.LOW.getDisplayName(), "Некорректный displayName для LOW");
        Assertions.assertEquals("Средний", Priority.MEDIUM.getDisplayName(), "Некорректный displayName для MEDIUM");
        Assertions.assertEquals("Высокий", Priority.HIGH.getDisplayName(), "Некорректный displayName для HIGH");
    }

    @Test
    @DisplayName("Должен правильно преобразовывать строку в значение перечисления")
    void shouldParseFromString() {
        Assertions.assertEquals(Priority.LOW, Priority.valueOf("LOW"), "Должен правильно парсить 'LOW'");
        Assertions.assertEquals(Priority.MEDIUM, Priority.valueOf("MEDIUM"), "Должен правильно парсить 'MEDIUM'");
        Assertions.assertEquals(Priority.HIGH, Priority.valueOf("HIGH"), "Должен правильно парсить 'HIGH'");
    }

    @Test
    @DisplayName("Должен возвращать массив значений в правильном порядке")
    void shouldReturnValuesInCorrectOrder() {
        Priority[] values = Priority.values();

        Assertions.assertArrayEquals(new Priority[]{Priority.LOW, Priority.MEDIUM, Priority.HIGH},
                values, "Порядок значений должен соответствовать объявлению");
    }

    @Test
    @DisplayName("Должен правильно работать с ordinal значениями")
    void shouldHaveCorrectOrdinalValues() {
        Assertions.assertEquals(0, Priority.LOW.ordinal(), "Некорректный ordinal для LOW");
        Assertions.assertEquals(1, Priority.MEDIUM.ordinal(), "Некорректный ordinal для MEDIUM");
        Assertions.assertEquals(2, Priority.HIGH.ordinal(), "Некорректный ordinal для HIGH");
    }

    @ParameterizedTest
    @CsvSource({
            "LOW, Низкий",
            "MEDIUM, Средний",
            "HIGH, Высокий"
    })
    @DisplayName("Должен возвращать правильные displayName для каждого значения (параметризованный тест)")
    void shouldReturnCorrectDisplayNameParametrized(String enumName, String expectedDisplayName) {
        Priority priority = Priority.valueOf(enumName);

        String actualDisplayName = priority.getDisplayName();

        Assertions.assertEquals(expectedDisplayName, actualDisplayName,
                "Некорректный displayName для " + enumName);
    }

    @Test
    @DisplayName("Метод toString() должен возвращать имя перечисления")
    void toStringShouldReturnEnumName() {
        Assertions.assertEquals("LOW", Priority.LOW.toString(), "Некорректный toString() для LOW");
        Assertions.assertEquals("MEDIUM", Priority.MEDIUM.toString(), "Некорректный toString() для MEDIUM");
        Assertions.assertEquals("HIGH", Priority.HIGH.toString(), "Некорректный toString() для HIGH");
    }
}