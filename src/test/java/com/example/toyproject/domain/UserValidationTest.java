package com.example.toyproject.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class UserValidationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("유효한 유저")
    void 유저_valid() {
        // given
        User user = new User("user1234", "Password1!", "nickname", "name", "010-1234-5678", "user@a.c");

        // when
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("유효하지 않은 유저 - 아이디")
    void 아이디_검사() {
        // 사용자 아이디가 null인 경우
        User user = new User(null, "Password1!", "nickname", "name", "010-1234-5678", "user@example.com");
        checkInvalid(user);

        // 사용자 아이디가 비어있는 경우
        setField(user, "userId", "");
        checkInvalid(user);

        // 사용자 아이디가 최소 길이 미만인 경우
        setField(user, "userId", "us");
        checkInvalid(user);

        // 사용자 아이디에 허용되지 않는 문자가 포함된 경우
        setField(user, "userId", "!");
        checkInvalid(user);
    }

    @Test
    @DisplayName("유효하지 않은 유저 - 패스워드")
    void 패스워드_검사() {
        // 패스워드가 null인 경우
        User user = new User("user1234", null, "nickname", "name", "010-1234-5678", "user@example.com");
        checkInvalid(user);

        // 사용자 패스워드가 비어있는 경우
        setField(user, "password", "");
        checkInvalid(user);
    }

    @Test
    @DisplayName("유효하지 않은 유저 - 닉네임")
    void 닉네임_검사() {
        // 닉네임이 null인 경우
        User user = new User("user1234", "Password1!", null, "name", "010-1234-5678", "user@example.com");
        checkInvalid(user);

        // 닉네임이 비어있는 경우
        setField(user, "nickname", "");
        checkInvalid(user);

        // 닉네임이 최대 길이를 초과한 경우
        setField(user, "nickname", "abcdefghijklmnopqrstuv");
        checkInvalid(user);

        // 닉네임에 허용되지 않는 문자가 포함된 경우
        setField(user, "nickname", "nick@name");
        checkInvalid(user);
    }

    @Test
    @DisplayName("유효하지 않은 유저 - 이름")
    void 이름_검사() {
        // 이름이 null인 경우
        User user = new User("user1234", "Password1!", "nickname", null, "010-1234-5678", "user@example.com");
        checkInvalid(user);

        // 이름이 비어있는 경우
        setField(user, "name", "");
        checkInvalid(user);

        // 이름이 최대 길이를 초과한 경우
        setField(user, "name", "abcdefghijklmno");
        checkInvalid(user);

        // 이름에 허용되지 않는 문자가 포함된 경우
        setField(user, "name", "name@");
        checkInvalid(user);
    }

    @Test
    @DisplayName("유효하지 않은 유저 - 전화번호")
    void 전화번호_검사() {
        // 전화번호가 null인 경우
        User user = new User("user1234", "Password1!", "nickname", "name", null, "user@example.com");
        checkInvalid(user);
        
        // 전화번호가 비어있는 경우
        setField(user, "phoneNumber", "");
        checkInvalid(user);

        // 전화번호가 유효하지 않은 형식인 경우
        setField(user, "phoneNumber", "1234");
        checkInvalid(user);
    }

    @Test
    @DisplayName("유효하지 않은 유저 - 이메일")
    void 이메일_검사() {
        // 이메일이 null인 경우
        User user = new User("user1234", "Password1!", "nickname", "name", "010-1234-5678", null);
        checkInvalid(user);

        setField(user, "email", "");
        checkInvalid(user);

        // 이메일이 유효하지 않은 형식인 경우
        setField(user, "email", "user@");
        checkInvalid(user);
    }
    
    private void checkInvalid(User user) {
        Set<ConstraintViolation<User>> validate;
        validate = validator.validate(user);
        assertFalse(validate.isEmpty());
    }
}