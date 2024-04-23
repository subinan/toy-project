package com.example.toyproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDTO {
    private String userId;
    private String nickname;
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;
}
