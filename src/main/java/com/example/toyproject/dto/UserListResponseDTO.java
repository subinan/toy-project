package com.example.toyproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserListResponseDTO {
    private List<UserInfoResponseDTO> users;
    private PaginationInfoDto paginationInfoDto;
}
