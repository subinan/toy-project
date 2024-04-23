package com.example.toyproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationInfoDto {
    private int page;
    private int size;
    private int totalPages;
}
