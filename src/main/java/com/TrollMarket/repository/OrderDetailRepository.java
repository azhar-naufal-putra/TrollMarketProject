package com.TrollMarket.repository;

import com.TrollMarket.entity.OrderDetail;
import com.TrollMarket.entity.composite.OrderDetailId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    @Query("""
            SELECT ordet
            FROM OrderDetail ordet
            JOIN ordet.order ord
            WHERE
                ord.status = 'Purchased'
                AND (ordet.sellerUsername = :username OR ord.buyerUsername = :username)
        """)
    List<OrderDetail> getOrderDetails(Pageable page, String username);

    @Query("""
            SELECT COUNT(*)
            FROM OrderDetail ordet
            JOIN ordet.order ord
            WHERE
                ord.status = 'Purchased'
                AND (ordet.sellerUsername = :username OR ord.buyerUsername = :username)
        """)
    Long getTotalPages(String username);

    @Query("""
            SELECT ordet
            FROM OrderDetail ordet
            JOIN ordet.order ord
            WHERE
                ord.status = 'Active'
                AND ord.buyerUsername = :username
        """)
    List<OrderDetail> getMyCart(Pageable page, String username);

    @Query("""
            SELECT COUNT(*)
            FROM OrderDetail ordet
            JOIN ordet.order ord
            WHERE
                ord.status = 'Active'
                AND ord.buyerUsername = :username
        """)
    Long getTotalPagesCart(String username);

    @Query("""
            SELECT ordet
            FROM OrderDetail ordet
            JOIN ordet.order ord
            WHERE
                ord.status = 'Purchased'
                AND (:sellerUsername = '' OR ordet.sellerUsername = :sellerUsername)
                AND (:buyerUsername = '' OR ord.buyerUsername = :buyerUsername)
        """)
    List<OrderDetail> getOrderDetails(Pageable page, String buyerUsername, String sellerUsername);
    @Query("""
            SELECT COUNT(*)
            FROM OrderDetail ordet
            JOIN ordet.order ord
            WHERE
                ord.status = 'Purchased'
                AND (:sellerUsername = '' OR ordet.sellerUsername = :sellerUsername)
                AND (:buyerUsername = '' OR ord.buyerUsername = :buyerUsername)
        """)
    Long getTotalPages(String buyerUsername, String sellerUsername);

    @Query("""
            SELECT COUNT(*)
            FROM OrderDetail AS ordet
            WHERE
                ordet.shipperId = :shipperId
            """)
    Long checkDependentShipper(Integer shipperId);

    @Query("""
            SELECT COUNT(*)
            FROM OrderDetail AS ordet
            WHERE
                ordet.id.merchandiseId = :merchandiseId
            """)
    Long checkDependentMerchandise(Integer merchandiseId);

}
