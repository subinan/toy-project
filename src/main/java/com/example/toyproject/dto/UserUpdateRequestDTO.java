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
}
