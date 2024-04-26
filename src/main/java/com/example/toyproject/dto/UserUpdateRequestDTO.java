package com.example.toyproject.dto;

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
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {

    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자 이상만 허용합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$",
            message = "비밀번호는 최소한 하나의 알파벳(대소문자), 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank
    @Size(max = 20, message = "닉네임은 20자 이하만 허용합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 알파벳, 숫자, 한글만 사용 가능합니다.")
    private String nickname;

    @NotBlank
    @Size(max = 10, message = "이름은 10자 이하만 허용합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 알파벳과 한글만 사용 가능합니다.")
    private String name;

    @JsonProperty("phone_number")
    @NotBlank
    @Pattern(regexp = "^01([016-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "핸드폰 번호 형식과 일치하지 않습니다.")
    private String phoneNumber;

    @NotBlank
    @Email(message = "이메일 형식과 일치하지 않습니다.")
    private String email;
}
