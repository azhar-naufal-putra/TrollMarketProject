package com.TrollMarket.service.implementation;

import com.TrollMarket.dto.myCart.MyCartGridDTO;
import com.TrollMarket.dto.myCart.MyCartOrdersDTO;
import com.TrollMarket.dto.myCart.MyCartPurchaseDTO;
import com.TrollMarket.entity.Account;
import com.TrollMarket.entity.Order;
import com.TrollMarket.entity.OrderDetail;
import com.TrollMarket.entity.composite.OrderDetailId;
import com.TrollMarket.helpers.PriceHelper;
import com.TrollMarket.repository.AccountRepository;
import com.TrollMarket.repository.OrderDetailRepository;
import com.TrollMarket.repository.OrderRepository;
import com.TrollMarket.service.abstraction.MyCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class MyCartServiceImplementation implements MyCartService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final Integer rowInPage = 3;

    @Autowired
    public MyCartServiceImplementation(
            OrderDetailRepository orderDetailRepository,
            OrderRepository orderRepository,
            AccountRepository accountRepository
    ){
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public MyCartGridDTO getMyCart(int page, String username) {
        Pageable pagination = PageRequest.of(page - 1, rowInPage);
        List<OrderDetail> orderDetails = orderDetailRepository.getMyCart(pagination, username);
        MyCartGridDTO myCartGridDTO = new MyCartGridDTO();
        List<MyCartOrdersDTO> myCartOrdersDTOS = new LinkedList<>();
        for (OrderDetail orderDetail:
             orderDetails) {
            MyCartOrdersDTO myCartOrdersDTO = new MyCartOrdersDTO();
            myCartOrdersDTO.setMerchandiseId(orderDetail.getMerchandise().getMerchandiseId());
            myCartOrdersDTO.setQuantity(orderDetail.getQuantity());
            myCartOrdersDTO.setMerchandiseName(orderDetail.getMerchandise().getMerchandiseName());
            myCartOrdersDTO.setShipperName(orderDetail.getShipper().getShipperName());
            myCartOrdersDTO.setSellerName(orderDetail.getSeller().getName());
            BigDecimal totalPrice = new BigDecimal(0);
            totalPrice = totalPrice.add(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())));
            totalPrice = totalPrice.add(orderDetail.getShipmentPrice());
            myCartOrdersDTO.setTotalPrice(PriceHelper.parsePrice(totalPrice, new Locale("id", "ID")));
            myCartOrdersDTOS.add(myCartOrdersDTO);
        }
        if(orderDetails.size() > 0){
            myCartGridDTO.setOrderId(orderDetails.get(0).getId().getOrderId());
        }
        myCartGridDTO.setOrders(myCartOrdersDTOS);
        return myCartGridDTO;
    }

    @Override
    public Long getTotalPages(String username) {
        double totalOrderDetail = orderDetailRepository.getTotalPagesCart(username);
        double totalPages = totalOrderDetail / rowInPage;
        totalPages = Math.ceil(totalPages);
        return (long) (totalPages == 0 ? 1 : totalPages);
    }

    @Override
    public void removeOrderDetail(Integer orderId, Integer merchandiseId) {
        OrderDetailId orderDetailId = new OrderDetailId();
        orderDetailId.setOrderId(orderId);
        orderDetailId.setMerchandiseId(merchandiseId);
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow();
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    public MyCartPurchaseDTO getMyCartPaymentInfo(Integer orderId, String username){
        MyCartPurchaseDTO myCartPurchaseDTO = new MyCartPurchaseDTO();
        Order order = orderRepository.findById(orderId).orElseThrow();
        Account account = accountRepository.findById(username).orElseThrow();
        BigDecimal totalPayment = new BigDecimal(0);
        for (OrderDetail orderDetail:
             order.getOrderDetails()) {
            totalPayment = totalPayment.add(orderDetail.getShipmentPrice().add(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getQuantity()))));
        }
        myCartPurchaseDTO.setOrderId(orderId);
        myCartPurchaseDTO.setMyBalance(account.getBalance());
        myCartPurchaseDTO.setMyBalanceParsed(PriceHelper.parsePrice(account.getBalance(), new Locale("id", "ID")));
        myCartPurchaseDTO.setTotalPayment(totalPayment);
        myCartPurchaseDTO.setTotalPaymentParsed(PriceHelper.parsePrice(totalPayment, new Locale("id", "ID")));
        return myCartPurchaseDTO;
    };

    @Override
    public void purchaseOrder(MyCartPurchaseDTO myCartPurchaseDTO){
        Order order = orderRepository.findById(myCartPurchaseDTO.getOrderId()).orElseThrow();
        order.setStatus("Purchased");
        order.setOrderDate(LocalDate.now());
        Account buyer = accountRepository.findById(order.getBuyerUsername()).orElseThrow();
        buyer.setBalance(buyer.getBalance().subtract(myCartPurchaseDTO.getTotalPayment()));
        for (OrderDetail orderDetail:
             order.getOrderDetails()) {
            Account seller = accountRepository.findById(orderDetail.getSellerUsername()).orElseThrow();
            seller.setBalance(seller.getBalance().add(orderDetail.getShipmentPrice().add(orderDetail.getPrice().multiply(new BigDecimal(orderDetail.getQuantity())))));
            accountRepository.save(seller);
        }
        accountRepository.save(buyer);
        orderRepository.save(order);
    };


}
