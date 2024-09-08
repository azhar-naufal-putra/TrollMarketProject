package com.TrollMarket.repository;

import com.TrollMarket.dto.CategoryOptionDTO;
import com.TrollMarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
            SELECT new com.TrollMarket.dto.CategoryOptionDTO(
                cat.categoryId,
                cat.categoryName,
                (:merchandiseCategoryId IS NOT NULL AND cat.categoryId = :merchandiseCategoryId)
            )
            FROM Category AS cat
            """)
    List<CategoryOptionDTO> getCategoryOptions(Integer merchandiseCategoryId);
}
