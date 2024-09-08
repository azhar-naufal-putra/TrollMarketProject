package com.TrollMarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOptionDTO {
    private Integer categoryId;
    private String categoryName;
    private Boolean isSelected;
}
