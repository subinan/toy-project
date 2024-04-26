package com.example.toyproject.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginationInfoDto {
    private int page;
    private int size;
    @JsonProperty("total_pages")
    private int totalPages;
}
