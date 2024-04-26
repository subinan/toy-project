package com.example.toyproject.dto;

import com.example.toyproject.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {
    @Parameter(description = "아이디: 알파벳과 숫자 4-20자")
    @JsonProperty("user_id")
    @NotBlank
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하만 허용합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영어 대문자, 소문자, 숫자만 사용 가능합니다.")
    private String userId;

    @Parameter(description = "비밀번호: 알파벳, 숫자, 특수문자를 전부 포함하여 8자 이상")
    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자 이상만 허용합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$",
            message = "비밀번호는 최소한 하나의 알파벳(대소문자), 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @Parameter(description = "닉네임: 알파벳, 숫자, 한글 20자 이하")
    @NotBlank
    @Size(max = 20, message = "닉네임은 20자 이하만 허용합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 알파벳, 숫자, 한글만 사용 가능합니다.")
    private String nickname;

    @Parameter(description = "이름: 알파벳, 한글 10자 이하")
    @NotBlank
    @Size(max = 10, message = "이름은 10자 이하만 허용합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 알파벳과 한글만 사용 가능합니다.")
    private String name;

    @Parameter(description = "핸드폰 번호")
    @JsonProperty("phone_number")
    @NotBlank
    @Pattern(regexp = "^01([016-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "핸드폰 번호 형식과 일치하지 않습니다.")
    private String phoneNumber;

    @Parameter(description = "이메일")
    @NotBlank
    @Email(message = "이메일 형식과 일치하지 않습니다.")
    private String email;

    public static User toUser(SignUpRequestDTO signUpRequestDTO) {
        return new User(signUpRequestDTO.getUserId(),
                signUpRequestDTO.getPassword(),
                signUpRequestDTO.getNickname(),
                signUpRequestDTO.getName(),
                signUpRequestDTO.getPhoneNumber(),
                signUpRequestDTO.getEmail()
        );
    }
}
