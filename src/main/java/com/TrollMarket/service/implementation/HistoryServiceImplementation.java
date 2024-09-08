package com.TrollMarket.service.implementation;

import com.TrollMarket.dto.history.HistoryBuyerOptionDTO;
import com.TrollMarket.dto.history.HistoryGridDTO;
import com.TrollMarket.dto.history.HistorySellerOptionDTO;
import com.TrollMarket.entity.OrderDetail;
import com.TrollMarket.helpers.DateHelper;
import com.TrollMarket.helpers.PriceHelper;
import com.TrollMarket.repository.AccountRepository;
import com.TrollMarket.repository.OrderDetailRepository;
import com.TrollMarket.service.abstraction.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class HistoryServiceImplementation implements HistoryService {
    private final OrderDetailRepository orderDetailRepository;
    private final AccountRepository accountRepository;
    private final Integer rowInPage = 3;

    @Autowired
    public HistoryServiceImplementation(OrderDetailRepository orderDetailRepository, AccountRepository accountRepository){
        this.orderDetailRepository = orderDetailRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public List<HistoryBuyerOptionDTO> getBuyerOptions() {
        return accountRepository.getBuyerOptions();
    }

    @Override
    public List<HistorySellerOptionDTO> getSellerOptions() {
        return accountRepository.getSellerOptions();
    }

    @Override
    public List<HistoryGridDTO> getAllHistories(int page, String buyerUsername, String sellerUsername) {
        Pageable pagination = PageRequest.of(page - 1, rowInPage, Sort.by(Sort.Order.desc("order.orderDate")));
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetails(pagination, buyerUsername, sellerUsername);
        List<HistoryGridDTO> historyGridDTOS = new LinkedList<>();
        for (OrderDetail orderDetail:
             orderDetails) {
            HistoryGridDTO historyGridDTO = new HistoryGridDTO();
            historyGridDTO.setBuyerName(orderDetail.getOrder().getBuyer().getName());
            historyGridDTO.setSellerName(orderDetail.getSeller().getName());
            historyGridDTO.setMerchandiseName(orderDetail.getMerchandise().getMerchandiseName());
            historyGridDTO.setQuantity(orderDetail.getQuantity());
            historyGridDTO.setOrderDate(DateHelper.dateParse(orderDetail.getOrder().getOrderDate(), "dd/MM/yyyy", new Locale("id", "ID")));
            historyGridDTO.setShipperName(orderDetail.getShipper().getShipperName());
            historyGridDTO.setTotalPrice(PriceHelper.parsePrice(orderDetail.getShipmentPrice().add(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getQuantity()))), new Locale("id", "ID")));
            historyGridDTOS.add(historyGridDTO);
        }
        return historyGridDTOS;
    }

    @Override
    public Long getTotalPages(String buyerUsername, String sellerUsername) {
        double totalOrderDetail = orderDetailRepository.getTotalPages(buyerUsername, sellerUsername);
        double totalPages = totalOrderDetail / rowInPage;
        totalPages = Math.ceil(totalPages);
        return (long) (totalPages == 0 ? 1 : totalPages);
    }
}
