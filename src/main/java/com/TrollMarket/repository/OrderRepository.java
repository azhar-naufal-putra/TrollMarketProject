package com.TrollMarket.repository;

import com.TrollMarket.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("""
            SELECT ord
            FROM Order AS ord
            WHERE
                ord.buyerUsername = :username
                AND ord.status = 'Active'
            """)
    Order getActiveOrder(String username);

    @Query("""
            SELECT ord.orderId
            FROM Order AS ord
            ORDER BY ord.orderId DESC
            """)
    Integer[] getLastOrder();



}
