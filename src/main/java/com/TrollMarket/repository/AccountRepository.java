package com.TrollMarket.repository;

import com.TrollMarket.dto.history.HistoryBuyerOptionDTO;
import com.TrollMarket.dto.history.HistorySellerOptionDTO;
import com.TrollMarket.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("""
            SELECT new com.TrollMarket.dto.history.HistoryBuyerOptionDTO(
                acc.username,
                acc.name
            )
            FROM Account AS acc
            WHERE
                acc.role = 'Buyer'
            """)
    List<HistoryBuyerOptionDTO> getBuyerOptions();
    @Query("""
            SELECT new com.TrollMarket.dto.history.HistorySellerOptionDTO(
                acc.username,
                acc.name
            )
            FROM Account AS acc
            WHERE
                acc.role = 'Seller'
            """)
    List<HistorySellerOptionDTO> getSellerOptions();
}
