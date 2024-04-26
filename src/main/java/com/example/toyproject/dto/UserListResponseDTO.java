package com.example.toyproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserListResponseDTO {
    private List<UserInfoResponseDTO> users;
    @JsonProperty("pagination_info")
    private PaginationInfoDto paginationInfo;
}
