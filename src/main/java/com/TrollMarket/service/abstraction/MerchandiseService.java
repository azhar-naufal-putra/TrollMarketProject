package com.TrollMarket.service.abstraction;

import com.TrollMarket.dto.CategoryOptionDTO;
import com.TrollMarket.dto.merchandise.MerchandiseGridDTO;
import com.TrollMarket.dto.merchandise.MerchandiseInfoDTO;
import com.TrollMarket.dto.merchandise.MerchandiseInsertUpdateDTO;

import java.util.List;

public interface MerchandiseService {
    List<MerchandiseGridDTO> getMerchandises(int page, String username);
    Long getTotalPages(String username);
    MerchandiseInsertUpdateDTO getMerchandise(Integer merchandiseId);
    void save(MerchandiseInsertUpdateDTO merchandiseInsertUpdateDTO);
    void delete(Integer merchandiseId);
    void discontinue(Integer merchandiseId);
    MerchandiseInfoDTO getMerchandiseInfo(Integer merchandiseId);
}
