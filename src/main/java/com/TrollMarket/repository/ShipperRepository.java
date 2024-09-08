package com.TrollMarket.repository;

import com.TrollMarket.dto.ShipperOptionDTO;
import com.TrollMarket.dto.shipper.ShipperInsertUpdateDTO;
import com.TrollMarket.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Integer> {

    @Query("""
            SELECT new com.TrollMarket.dto.shipper.ShipperInsertUpdateDTO(
                shi.shipperId,
                shi.shipperName,
                shi.price,
                shi.service
            )
            FROM Shipper AS shi
            WHERE shi.shipperId = :shipperId
            """)
    ShipperInsertUpdateDTO getShipper(Integer shipperId);

    @Query("""
            SELECT new com.TrollMarket.dto.ShipperOptionDTO(
                shi.shipperId,
                shi.shipperName
            )
            FROM Shipper AS shi
            WHERE shi.service = TRUE
            """)
    List<ShipperOptionDTO> getShipperOptions();

    @Query("""
            SELECT COUNT(*)
            FROM Shipper AS shi
            WHERE
                (:shipperId IS NULL OR shi.shipperId != :shipperId)
                AND shi.shipperName = :shipperName
            """)
    Long checkExistingShipperName(Integer shipperId, String shipperName);
}
