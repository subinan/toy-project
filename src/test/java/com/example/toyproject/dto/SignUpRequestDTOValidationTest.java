package com.example.toyproject.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignUpRequestDTOValidationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    @DisplayName("유효한 회원가입 요청")
    void 요청_valid() {
        // given
        SignUpRequestDTO req = new SignUpRequestDTO("user1234", "Password1!", "nickname", "name", "010-1234-5678", "user@a.c");

        // when
        Set<ConstraintViolation<SignUpRequestDTO>> violations = validator.validate(req);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("유효하지 않은 회원가입 요청 - 아이디")
    void 아이디_검사() {
        // 사용자 아이디가 null인 경우
        SignUpRequestDTO req = new SignUpRequestDTO(null, "Password1!", "nickname", "name", "010-1234-5678", "req@example.com");
        checkInvalid(req);

        // 사용자 아이디가 비어있는 경우
        req.setUserId("");
        checkInvalid(req);

        // 사용자 아이디가 최소 길이 미만인 경우
        req.setUserId("us");
        checkInvalid(req);

        // 사용자 아이디에 허용되지 않는 문자가 포함된 경우
        req.setUserId("us!");
        checkInvalid(req);
    }

    @Test
    @DisplayName("유효하지 않은 회원가입 요청 - 패스워드")
    void 패스워드_검사() {
        // 패스워드가 null인 경우
        SignUpRequestDTO req = new SignUpRequestDTO("user1234", null, "nickname", "name", "010-1234-5678", "req@example.com");
        checkInvalid(req);

        // 사용자 패스워드가 비어있는 경우
        req.setPassword("");
        checkInvalid(req);

        // 패스워드가 최소 길이 미만인 경우
        req.setPhoneNumber("Pass!");
        checkInvalid(req);

        // 패스워드가 허용되지 않는 조건을 충족하지 않는 경우 (대문자, 소문자, 숫자, 특수문자 각각 하나 이상 포함되어야 함)
        req.setPassword("Password1");
        checkInvalid(req);
    }

    @Test
    @DisplayName("유효하지 않은 회원가입 요청 - 닉네임")
    void 닉네임_검사() {
        // 닉네임이 null인 경우
        SignUpRequestDTO req = new SignUpRequestDTO("user1234", "Password1!", null, "name", "010-1234-5678", "req@example.com");
        checkInvalid(req);

        // 닉네임이 비어있는 경우
        req.setNickname("");
        checkInvalid(req);

        // 닉네임이 최대 길이를 초과한 경우
        req.setNickname("abcdefghijklmnopqrstuv");
        checkInvalid(req);

        // 닉네임에 허용되지 않는 문자가 포함된 경우
        req.setNickname("nick@name");
        checkInvalid(req);
    }

    @Test
    @DisplayName("유효하지 않은 회원가입 요청 - 이름")
    void 이름_검사() {
        // 이름이 null인 경우
        SignUpRequestDTO req = new SignUpRequestDTO("user1234", "Password1!", "nickname", null, "010-1234-5678", "req@example.com");
        checkInvalid(req);

        // 이름이 비어있는 경우
        req.setName("");
        checkInvalid(req);

        // 이름이 최대 길이를 초과한 경우
        req.setName("abcdefghijklmno");
        checkInvalid(req);

        // 이름에 허용되지 않는 문자가 포함된 경우
        req.setName("name@");
        checkInvalid(req);
    }

    @Test
    @DisplayName("유효하지 않은 회원가입 요청 - 전화번호")
    void 전화번호_검사() {
        // 전화번호가 null인 경우
        SignUpRequestDTO req = new SignUpRequestDTO("user1234", "Password1!", "nickname", "name", null, "req@example.com");
        checkInvalid(req);

        // 전화번호가 비어있는 경우
        req.setPhoneNumber("");
        checkInvalid(req);

        // 전화번호가 유효하지 않은 형식인 경우
        req.setPhoneNumber("1234");
        checkInvalid(req);
    }

    @Test
    @DisplayName("유효하지 않은 회원가입 요청 - 이메일")
    void 이메일_검사() {
        // 이메일이 null인 경우
        SignUpRequestDTO req = new SignUpRequestDTO("user1234", "Password1!", "nickname", "name", "010-1234-5678", null);
        checkInvalid(req);

        // 이메일이 비어있는 경우
        req.setEmail("");
        checkInvalid(req);

        // 이메일이 유효하지 않은 형식인 경우
        req.setEmail("req@");
        checkInvalid(req);
    }

    private void checkInvalid(SignUpRequestDTO req) {
        Set<ConstraintViolation<SignUpRequestDTO>> validate;
        validate = validator.validate(req);
        assertFalse(validate.isEmpty());
    }
}