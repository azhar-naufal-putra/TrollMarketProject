package com.TrollMarket.service.abstraction;

import com.TrollMarket.dto.shipper.ShipperGridDTO;
import com.TrollMarket.dto.shipper.ShipperInsertUpdateDTO;

import java.util.List;

public interface ShipperService {
    List<ShipperGridDTO> getShippers(int page);
    Long getTotalPages();
    ShipperInsertUpdateDTO getShipper(Integer shipperId);
    void save(ShipperInsertUpdateDTO shipperInsertUpdateDTO);
    void stopService(Integer shipperId);
    void delete(Integer shipperId);
    Boolean checkExistingShipperName(Integer shipperId, String shipperName);
}
