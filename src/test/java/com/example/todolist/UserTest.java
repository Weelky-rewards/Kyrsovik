package com.example.todolist;

import com.example.todolist.model.Role;
import com.example.todolist.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Validator validator;

    @BeforeEach
    void setUp() {
        user = new User();

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    @DisplayName("Должен создаваться с конструктором по умолчанию")
    void shouldCreateWithDefaultConstructor() {
        // Act & Assert
        assertNotNull(user);
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().isEmpty());
        assertNull(user.getTasks());
    }

    @Test
    @DisplayName("Должен создаваться с параметризованным конструктором")
    void shouldCreateWithParameterizedConstructor() {
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";

        User user = new User(username, email, password);

        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().isEmpty());
        assertNull(user.getTasks());
    }

    @Test
    @DisplayName("Должен устанавливать и получать id")
    void shouldSetAndGetId() {
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    @DisplayName("Должен устанавливать и получать username")
    void shouldSetAndGetUsername() {
        String username = "john_doe";
        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    @DisplayName("Должен проходить валидацию при корректном username")
    void shouldPassValidationWithValidUsername() {
        user.setUsername("validUser123");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertTrue(violations.isEmpty(), "Не должно быть нарушений валидации");
    }

    @Test
    @DisplayName("Не должен проходить валидацию при слишком длинном username")
    void shouldFailValidationWithTooLongUsername() {
        String longUsername = "a".repeat(51);
        user.setUsername(longUsername);
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertFalse(violations.isEmpty(), "Должны быть нарушения валидации");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("от 3 до 50 символов")));
    }

    @Test
    @DisplayName("Не должен проходить валидацию при null username")
    void shouldFailValidationWithNullUsername() {
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "username");
        assertFalse(violations.isEmpty(), "Должны быть нарушения валидации");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Имя пользователя обязательно")));
    }

    @Test
    @DisplayName("Должен устанавливать и получать email")
    void shouldSetAndGetEmail() {
        String email = "user@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("Должен проходить валидацию при корректном email")
    void shouldPassValidationWithValidEmail() {
        user.setEmail("user@example.com");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "email");
        assertTrue(violations.isEmpty(), "Не должно быть нарушений валидации");
    }

    @Test
    @DisplayName("Не должен проходить валидацию при null email")
    void shouldFailValidationWithNullEmail() {
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "email");
        assertFalse(violations.isEmpty(), "Должны быть нарушения валидации");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Email обязателен")));
    }

    @Test
    @DisplayName("Должен устанавливать и получать password")
    void shouldSetAndGetPassword() {
        String password = "securePassword123";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("Должен проходить валидацию при корректном password")
    void shouldPassValidationWithValidPassword() {
        user.setPassword("123456");
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertTrue(violations.isEmpty(), "Не должно быть нарушений валидации");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "short", ""})
    @DisplayName("Не должен проходить валидацию при слишком коротком password")
    void shouldFailValidationWithTooShortPassword(String shortPassword) {
        user.setPassword(shortPassword);
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty(), "Должны быть нарушения валидации");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("минимум 6 символов")));
    }

    @Test
    @DisplayName("Не должен проходить валидацию при null password")
    void shouldFailValidationWithNullPassword() {
        Set<ConstraintViolation<User>> violations = validator.validateProperty(user, "password");
        assertFalse(violations.isEmpty(), "Должны быть нарушения валидации");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Пароль обязателен")));
    }

    @Test
    @DisplayName("Должен устанавливать и получать roles")
    void shouldSetAndGetRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);
        user.setRoles(roles);
        assertEquals(roles, user.getRoles());
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(Role.USER));
        assertTrue(user.getRoles().contains(Role.ADMIN));
    }

    @Test
    @DisplayName("Должен иметь пустой набор roles по умолчанию")
    void shouldHaveEmptyRolesByDefault() {
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    @DisplayName("Должен добавлять role через addRole")
    void shouldAddRole() {
        user.addRole(Role.USER);
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(Role.USER));
    }

    @Test
    @DisplayName("Должен добавлять несколько roles")
    void shouldAddMultipleRoles() {
        user.addRole(Role.USER);
        user.addRole(Role.ADMIN);
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(Role.USER));
        assertTrue(user.getRoles().contains(Role.ADMIN));
    }

    @Test
    @DisplayName("Должен удалять role через removeRole")
    void shouldRemoveRole() {
        user.addRole(Role.USER);
        user.addRole(Role.ADMIN);

        user.removeRole(Role.USER);

        assertEquals(1, user.getRoles().size());
        assertFalse(user.getRoles().contains(Role.USER));
        assertTrue(user.getRoles().contains(Role.ADMIN));
    }

    @Test
    @DisplayName("Должен корректно сериализоваться и десериализоваться")
    void shouldSerializeAndDeserializeCorrectly() {
        Long id = 42L;
        String username = "serializedUser";
        String email = "serialized@example.com";
        String password = "serializedPass123";

        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.addRole(Role.USER);
        user.addRole(Role.ADMIN);

        User deserializedUser = new User();
        deserializedUser.setId(user.getId());
        deserializedUser.setUsername(user.getUsername());
        deserializedUser.setEmail(user.getEmail());
        deserializedUser.setPassword(user.getPassword());

        Set<Role> rolesCopy = new HashSet<>(user.getRoles());
        deserializedUser.setRoles(rolesCopy);

        assertEquals(user.getId(), deserializedUser.getId());
        assertEquals(user.getUsername(), deserializedUser.getUsername());
        assertEquals(user.getEmail(), deserializedUser.getEmail());
        assertEquals(user.getPassword(), deserializedUser.getPassword());
        assertEquals(user.getRoles().size(), deserializedUser.getRoles().size());
        assertTrue(deserializedUser.getRoles().contains(Role.USER));
        assertTrue(deserializedUser.getRoles().contains(Role.ADMIN));
    }

    @Test
    @DisplayName("Должен проходить полную валидацию при корректных данных")
    void shouldPassFullValidationWithCorrectData() {
        User validUser = new User("validuser", "valid@example.com", "password123");
        validUser.addRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(validUser);

        assertTrue(violations.isEmpty(), "Не должно быть нарушений валидации");
    }

    @ParameterizedTest
    @CsvSource({
            "us, invalid-email, 12345",
            "'', '', ''",
            "a, a@, short"
    })
    @DisplayName("Не должен проходить валидацию при некорректных данных")
    void shouldFailValidationWithInvalidData(String username, String email, String password) {
        User invalidUser = new User(username, email, password);

        Set<ConstraintViolation<User>> violations = validator.validate(invalidUser);

        assertFalse(violations.isEmpty(), "Должны быть нарушения валидации");
    }
}