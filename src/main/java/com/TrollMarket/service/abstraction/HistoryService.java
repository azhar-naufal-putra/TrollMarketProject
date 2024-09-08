package com.TrollMarket.service.abstraction;

import com.TrollMarket.dto.history.HistoryBuyerOptionDTO;
import com.TrollMarket.dto.history.HistoryGridDTO;
import com.TrollMarket.dto.history.HistorySellerOptionDTO;

import java.util.List;

public interface HistoryService {
    List<HistoryBuyerOptionDTO> getBuyerOptions();
    List<HistorySellerOptionDTO> getSellerOptions();
    List<HistoryGridDTO> getAllHistories(int page, String buyerUsername, String sellerUsername);
    Long getTotalPages(String buyerUsername, String sellerUsername);
}
