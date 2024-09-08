package com.TrollMarket.service.implementation;

import com.TrollMarket.dto.shipper.ShipperGridDTO;
import com.TrollMarket.dto.shipper.ShipperInsertUpdateDTO;
import com.TrollMarket.entity.Shipper;
import com.TrollMarket.helpers.PriceHelper;
import com.TrollMarket.repository.OrderDetailRepository;
import com.TrollMarket.repository.ShipperRepository;
import com.TrollMarket.service.abstraction.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class ShipperServiceImplementation implements ShipperService {
    private final ShipperRepository repository;
    private final OrderDetailRepository orderDetailRepository;
    private final Integer rowInPage = 3;

    @Autowired
    public ShipperServiceImplementation(ShipperRepository repository, OrderDetailRepository orderDetailRepository){
        this.repository = repository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<ShipperGridDTO> getShippers(int page){
        Pageable pagination = PageRequest.of(page - 1, rowInPage, Sort.by("shipperId"));
        Page<Shipper> shippers = repository.findAll(pagination);
        List<ShipperGridDTO> shipperGridDTOS = new LinkedList<>();
        for (Shipper shipper:
             shippers) {
            ShipperGridDTO shipperGridDTO = new ShipperGridDTO();
            shipperGridDTO.setShipperId(shipper.getShipperId());
            shipperGridDTO.setShipperName(shipper.getShipperName());
            shipperGridDTO.setPrice(PriceHelper.parsePrice(shipper.getPrice(), new Locale("id", "ID")));
            shipperGridDTO.setService(shipper.getService() ? "Yes" : "No");
            shipperGridDTO.setEditable(checkDependentShipper(shipperGridDTO.getShipperId()));
            shipperGridDTOS.add(shipperGridDTO);
        }
        return shipperGridDTOS;
    };

    @Override
    public Long getTotalPages(){
        double totalShipper = repository.count();
        double totalPages = totalShipper / rowInPage;
        long result = (long)(Math.ceil(totalPages));
        return result == 0 ? 1 : result;
    }

    private Boolean checkDependentShipper(Integer shipperId){
        return orderDetailRepository.checkDependentShipper(shipperId) > 0;
    }

    @Override
    public ShipperInsertUpdateDTO getShipper(Integer shipperId){
        return repository.getShipper(shipperId);
    };


    @Override
    public void save(ShipperInsertUpdateDTO shipperInsertUpdateDTO){
        Shipper shipper = new Shipper();
        shipper.setShipperId(shipperInsertUpdateDTO.getShipperId());
        shipper.setShipperName(shipperInsertUpdateDTO.getShipperName());
        shipper.setPrice(shipperInsertUpdateDTO.getPrice());
        shipper.setService(shipperInsertUpdateDTO.getService());
        repository.save(shipper);
    };

    @Override
    public void stopService(Integer shipperId){
        Shipper shipper = repository.findById(shipperId).orElseThrow();
        shipper.setService(false);
        repository.save(shipper);
    };


    @Override
    public void delete(Integer shipperId){
        Shipper shipper = repository.findById(shipperId).orElseThrow();
        repository.delete(shipper);
    }

    @Override
    public Boolean checkExistingShipperName(Integer shipperId, String shipperName){
        return repository.checkExistingShipperName(shipperId, shipperName) > 0;
    };



}
