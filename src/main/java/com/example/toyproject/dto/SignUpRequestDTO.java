package com.example.toyproject.dto;

import com.example.toyproject.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("user_id")
    @NotBlank
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영어 대문자, 소문자, 숫자")
    private String userId;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$")
    private String password;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$")
    private String nickname;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^[a-zA-Z가-힣]*$")
    private String name;

    @JsonProperty("phone_number")
    @NotBlank
    @Pattern(regexp = "^01([016-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    private String phoneNumber;

    @NotBlank
    @Email
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
