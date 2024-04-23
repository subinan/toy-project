package com.example.toyproject.dto;

import com.example.toyproject.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDTO {
    @JsonProperty("user_id")
    private String userId;
    private String password;
    private String nickname;
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
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
