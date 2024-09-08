package com.TrollMarket.utility;

import com.TrollMarket.dto.CategoryOptionDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryOptionDTO> getCategoryOptions(Integer merchandiseId);
    List<CategoryOptionDTO> getCategoryOptionByCategoryId(Integer categoryId);
}
