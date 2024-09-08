package com.TrollMarket.repository;

import com.TrollMarket.dto.merchandise.MerchandiseInsertUpdateDTO;
import com.TrollMarket.entity.Merchandise;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Integer> {
    @Query("""
            SELECT new com.TrollMarket.dto.merchandise.MerchandiseInsertUpdateDTO(
                mer.merchandiseId,
                mer.merchandiseName,
                mer.categoryId,
                mer.description,
                mer.price,
                mer.discontinue,
                mer.sellerUsername
            )
            FROM Merchandise AS mer
            WHERE mer.merchandiseId = :merchandiseId
            """)
    MerchandiseInsertUpdateDTO getMerchandise(Integer merchandiseId);

    @Query("""
            SELECT mer
            FROM Merchandise AS mer
            WHERE mer.sellerUsername = :username
            """)
    List<Merchandise> getMerchandises(Pageable page, String username);

    @Query("""
            SELECT mer
            FROM Merchandise AS mer
            WHERE
                NOT mer.discontinue
                AND (:merchandiseName = '' OR mer.merchandiseName LIKE %:merchandiseName%)
                AND (:categoryId IS NULL OR mer.categoryId = :categoryId)
                AND (:merchandiseDescription = '' OR mer.description LIKE %:merchandiseDescription%)
            """)
    List<Merchandise> getMerchandises(Pageable page, String merchandiseName, Integer categoryId, String merchandiseDescription);

    @Query("""
            SELECT COUNT(*)
            FROM Merchandise AS mer
            WHERE mer.sellerUsername = :username
            """)
    double getTotalPages(String username);

    @Query("""
            SELECT COUNT(*)
            FROM Merchandise AS mer
            WHERE
                NOT mer.discontinue
                AND (:merchandiseName = '' OR mer.merchandiseName LIKE %:merchandiseName%)
                AND (:categoryId IS NULL OR mer.categoryId = :categoryId)
                AND (:merchandiseDescription = '' OR mer.description LIKE %:merchandiseDescription%)
            """)
    double getTotalPages(String merchandiseName, Integer categoryId, String merchandiseDescription);
}
