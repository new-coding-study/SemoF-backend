package com.loung.semof.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CategoryDto {
    private int boardCategoryCode;
    private String boardCategoryName;
}
