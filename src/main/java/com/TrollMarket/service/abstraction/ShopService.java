package com.TrollMarket.service.abstraction;

import com.TrollMarket.dto.ShipperOptionDTO;
import com.TrollMarket.dto.shop.ShopGridDTO;
import com.TrollMarket.dto.shop.ShopInsertUpdateDTO;
import com.TrollMarket.dto.shop.ShopMerchandiseInfoDTO;

import java.util.List;

public interface ShopService {
    List<ShopGridDTO> getMerchandises(int page, String merchandiseName, Integer categoryId, String merchandiseDescription);
    Long getTotalPages(String merchandiseName, Integer categoryId, String merchandiseDescription);
    ShopMerchandiseInfoDTO getMerchandiseInfo(Integer merchandiseId);
    List<ShipperOptionDTO> getShipperOptions();
    void saveToCart(ShopInsertUpdateDTO shopInsertUpdateDTO);
}
