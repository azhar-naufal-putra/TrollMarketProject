package com.TrollMarket.service.implementation;

import com.TrollMarket.dto.ShipperOptionDTO;
import com.TrollMarket.dto.shop.ShopGridDTO;
import com.TrollMarket.dto.shop.ShopInsertUpdateDTO;
import com.TrollMarket.dto.shop.ShopMerchandiseInfoDTO;
import com.TrollMarket.entity.Merchandise;
import com.TrollMarket.entity.Order;
import com.TrollMarket.entity.OrderDetail;
import com.TrollMarket.entity.Shipper;
import com.TrollMarket.entity.composite.OrderDetailId;
import com.TrollMarket.helpers.PriceHelper;
import com.TrollMarket.repository.MerchandiseRepository;
import com.TrollMarket.repository.OrderDetailRepository;
import com.TrollMarket.repository.OrderRepository;
import com.TrollMarket.repository.ShipperRepository;
import com.TrollMarket.service.abstraction.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ShopServiceImplementation implements ShopService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final ShipperRepository shipperRepository;
    private final Integer rowInPage = 3;

    @Autowired
    public ShopServiceImplementation(
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            MerchandiseRepository merchandiseRepository,
            ShipperRepository shipperRepository
    ){
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.merchandiseRepository = merchandiseRepository;
        this.shipperRepository = shipperRepository;
    }

    @Override
    public List<ShopGridDTO> getMerchandises(int page, String merchandiseName, Integer categoryId, String merchandiseDescription){
        Pageable pagination = PageRequest.of(page - 1, rowInPage, Sort.by("merchandiseName"));
        List<Merchandise> merchandises = merchandiseRepository.getMerchandises(pagination, merchandiseName, categoryId, merchandiseDescription);
        List<ShopGridDTO> shopGridDTOS = new LinkedList<>();
        for (Merchandise merchandise:
             merchandises) {
            ShopGridDTO shopGridDTO = new ShopGridDTO();
            shopGridDTO.setMerchandiseId(merchandise.getMerchandiseId());
            shopGridDTO.setMerchandiseName(merchandise.getMerchandiseName());
            shopGridDTO.setPrice(PriceHelper.parsePrice(merchandise.getPrice(), new Locale("id", "ID")));
            shopGridDTOS.add(shopGridDTO);
        }
        return shopGridDTOS;
    };
    @Override
    public Long getTotalPages(String merchandiseName, Integer categoryId, String merchandiseDescription){
        double totalMerchandise = merchandiseRepository.getTotalPages(merchandiseName, categoryId, merchandiseDescription);
        double totalPages = totalMerchandise / rowInPage;
        totalPages = Math.ceil(totalPages);
        return (long) (totalPages == 0 ? 1 : totalPages);
    };

    @Override
    public ShopMerchandiseInfoDTO getMerchandiseInfo(Integer merchandiseId){
        Merchandise merchandise = merchandiseRepository.findById(merchandiseId).orElseThrow();
        ShopMerchandiseInfoDTO shopMerchandiseInfoDTO = new ShopMerchandiseInfoDTO();
        shopMerchandiseInfoDTO.setMerchandiseName(merchandise.getMerchandiseName());
        shopMerchandiseInfoDTO.setPrice(PriceHelper.parsePrice(merchandise.getPrice(), new Locale("id", "ID")));
        shopMerchandiseInfoDTO.setCategoryName(merchandise.getCategory().getCategoryName());
        shopMerchandiseInfoDTO.setDescription(merchandise.getDescription());
        shopMerchandiseInfoDTO.setSellerName(merchandise.getSeller().getName());
        return shopMerchandiseInfoDTO;
    };

    @Override
    public List<ShipperOptionDTO> getShipperOptions(){
        return shipperRepository.getShipperOptions();
    };

    @Override
    public void saveToCart(ShopInsertUpdateDTO shopInsertUpdateDTO) {
        Order order = orderRepository.getActiveOrder(shopInsertUpdateDTO.getBuyerUsername());
        if (order == null) {
            Integer[] lastOrderId = orderRepository.getLastOrder();
            order = new Order();
            if(lastOrderId.length != 0){
                order.setOrderId(lastOrderId[0] == null ? null : lastOrderId[0] + 1);
            }
            order.setBuyerUsername(shopInsertUpdateDTO.getBuyerUsername());
            order.setStatus("Active");
            order = orderRepository.save(order);
        }

        Merchandise merchandise = merchandiseRepository.findById(shopInsertUpdateDTO.getMerchandiseId()).orElseThrow();
        Shipper shipper = shipperRepository.findById(shopInsertUpdateDTO.getShipperId()).orElseThrow();

        OrderDetailId orderDetailId = new OrderDetailId(order.getOrderId(), merchandise.getMerchandiseId());
        Optional<OrderDetail> detail = orderDetailRepository.findById(orderDetailId);
        OrderDetail orderDetail = new OrderDetail();
        if(detail.isPresent()){
            orderDetail = detail.get();
            orderDetail.setQuantity(orderDetail.getQuantity() + shopInsertUpdateDTO.getQuantity());
            orderDetail.setShipperId(shopInsertUpdateDTO.getShipperId());
        }else{
            orderDetail.setId(orderDetailId);
            orderDetail.setOrder(order);
            orderDetail.setMerchandise(merchandise);
            orderDetail.setQuantity(shopInsertUpdateDTO.getQuantity());
            orderDetail.setPrice(merchandise.getPrice());
            orderDetail.setShipmentPrice(shipper.getPrice());
            orderDetail.setSellerUsername(merchandise.getSellerUsername());
            orderDetail.setShipperId(shopInsertUpdateDTO.getShipperId());
        }
        orderDetailRepository.save(orderDetail);
    }






}
