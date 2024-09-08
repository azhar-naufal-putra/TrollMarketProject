package com.TrollMarket.service.implementation;

import com.TrollMarket.dto.profile.ProfileAddBalanceDTO;
import com.TrollMarket.dto.profile.ProfileHistoryGridDTO;
import com.TrollMarket.dto.profile.ProfileInfoDTO;
import com.TrollMarket.entity.Account;
import com.TrollMarket.entity.Order;
import com.TrollMarket.entity.OrderDetail;
import com.TrollMarket.helpers.DateHelper;
import com.TrollMarket.helpers.PriceHelper;
import com.TrollMarket.repository.AccountRepository;
import com.TrollMarket.repository.OrderDetailRepository;
import com.TrollMarket.repository.OrderRepository;
import com.TrollMarket.service.abstraction.ProfileService;
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
public class ProfileServiceImplementation implements ProfileService {
    private final AccountRepository accountRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final Integer rowInPage = 3;

    @Autowired
    public ProfileServiceImplementation(
            AccountRepository accountRepository,
            OrderDetailRepository orderDetailRepository
    ){
        this.accountRepository = accountRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<ProfileHistoryGridDTO> getUserHistories(int page, String username) {
        Pageable pagination = PageRequest.of(page - 1, rowInPage, Sort.by(Sort.Order.desc("order.orderDate")));
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetails(pagination, username);
        List<ProfileHistoryGridDTO> profileHistoryGridDTOS = new LinkedList<>();
        for (OrderDetail orderDetail:
             orderDetails) {
            ProfileHistoryGridDTO profileHistoryGridDTO = new ProfileHistoryGridDTO();
            profileHistoryGridDTO.setOrderDate(DateHelper.dateParse(orderDetail.getOrder().getOrderDate(), "dd/MM/yyyy", new Locale("id", "ID")));
            profileHistoryGridDTO.setQuantity(orderDetail.getQuantity());
            profileHistoryGridDTO.setMerchandiseName(orderDetail.getMerchandise().getMerchandiseName());
            profileHistoryGridDTO.setShipperName(orderDetail.getShipper().getShipperName());
            BigDecimal totalPrice = new BigDecimal(0);
            totalPrice = totalPrice.add(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())));
            totalPrice = totalPrice.add(orderDetail.getShipmentPrice());
            profileHistoryGridDTO.setTotalPrice(PriceHelper.parsePrice(totalPrice, new Locale("id", "ID")));
            profileHistoryGridDTOS.add(profileHistoryGridDTO);
        }
        return profileHistoryGridDTOS;
    }

    @Override
    public ProfileInfoDTO getProfile(String username) {
        Account account = accountRepository.findById(username).orElseThrow();
        ProfileInfoDTO profileInfoDTO = new ProfileInfoDTO();
        profileInfoDTO.setBalance(PriceHelper.parsePrice(account.getBalance(), new Locale("id", "ID")));
        profileInfoDTO.setAddress(account.getAddress());
        profileInfoDTO.setRole(account.getRole());
        profileInfoDTO.setName(account.getName());
        return profileInfoDTO;
    }

    @Override
    public Long getTotalPages(String username) {
        double totalOrderDetail = orderDetailRepository.getTotalPages(username);
        double totalPages = totalOrderDetail / rowInPage;
        totalPages = Math.ceil(totalPages);
        return (long) (totalPages == 0 ? 1 : totalPages);
    }

    @Override
    public void updateBalance(ProfileAddBalanceDTO profileAddBalanceDTO){
        Account account = accountRepository.findById(profileAddBalanceDTO.getUsername()).orElseThrow();
        account.setBalance(account.getBalance().add(profileAddBalanceDTO.getBalance()));
        accountRepository.save(account);
    };

}
