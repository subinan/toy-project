package com.example.toyproject.dto;

import com.example.toyproject.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserInfoResponseDTO {
    private LocalDateTime createdAt;
    @JsonProperty("user_id")
    private String userId;
    private String nickname;
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;

    public static UserInfoResponseDTO fromUser(User user) {
        return new UserInfoResponseDTO(
                user.getCreatedAt(),
                user.getUserId(),
                user.getNickname(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail()
        );
    }
}
