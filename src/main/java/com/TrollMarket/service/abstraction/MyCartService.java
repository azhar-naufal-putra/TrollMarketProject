package com.TrollMarket.service.abstraction;

import com.TrollMarket.dto.myCart.MyCartGridDTO;
import com.TrollMarket.dto.myCart.MyCartPurchaseDTO;

public interface MyCartService {
    MyCartGridDTO getMyCart(int page, String username);
    Long getTotalPages(String username);
    void removeOrderDetail(Integer orderId, Integer merchandiseId);
    MyCartPurchaseDTO getMyCartPaymentInfo(Integer orderId, String username);
    void purchaseOrder(MyCartPurchaseDTO myCartPurchaseDTO);
}
