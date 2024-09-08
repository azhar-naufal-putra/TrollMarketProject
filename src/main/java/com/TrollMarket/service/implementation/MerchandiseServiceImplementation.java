package com.TrollMarket.service.implementation;

import com.TrollMarket.dto.merchandise.MerchandiseGridDTO;
import com.TrollMarket.dto.merchandise.MerchandiseInfoDTO;
import com.TrollMarket.dto.merchandise.MerchandiseInsertUpdateDTO;
import com.TrollMarket.entity.Merchandise;
import com.TrollMarket.helpers.PriceHelper;
import com.TrollMarket.repository.MerchandiseRepository;
import com.TrollMarket.repository.OrderDetailRepository;
import com.TrollMarket.service.abstraction.MerchandiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class MerchandiseServiceImplementation implements MerchandiseService {
    private final MerchandiseRepository repository;
    private final OrderDetailRepository orderDetailRepository;
    private final Integer rowInPage = 3;

    @Autowired
    public MerchandiseServiceImplementation(MerchandiseRepository repository, OrderDetailRepository orderDetailRepository){
        this.repository = repository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public List<MerchandiseGridDTO> getMerchandises(int page, String username){
        Pageable pagination = PageRequest.of(page - 1, rowInPage, Sort.by("merchandiseId"));
        List<Merchandise> merchandises = repository.getMerchandises(pagination, username);
        List<MerchandiseGridDTO> merchandiseGridDTOS = new LinkedList<>();
        for (Merchandise merchandise:
             merchandises) {
            MerchandiseGridDTO merchandiseGridDTO = new MerchandiseGridDTO();
            merchandiseGridDTO.setMerchandiseId(merchandise.getMerchandiseId());
            merchandiseGridDTO.setMerchandiseName(merchandise.getMerchandiseName());
            merchandiseGridDTO.setCategoryName(merchandise.getCategory().getCategoryName());
            merchandiseGridDTO.setDiscontinue(merchandise.getDiscontinue() ? "Yes" : "No");
            merchandiseGridDTO.setEditable(checkDependentMerchandise(merchandise.getMerchandiseId()));
            merchandiseGridDTOS.add(merchandiseGridDTO);
        }
        return merchandiseGridDTOS;
    };
    @Override
    public Long getTotalPages(String username){
        double totalMerchandise = repository.getTotalPages(username);
        double totalPages = totalMerchandise / rowInPage;
        totalPages = Math.ceil(totalPages);
        return (long) (totalPages == 0 ? 1 : totalPages);
    };

    @Override
    public MerchandiseInsertUpdateDTO getMerchandise(Integer merchandiseId){
        MerchandiseInsertUpdateDTO merchandiseInsertUpdateDTO = new MerchandiseInsertUpdateDTO();
        if(merchandiseId != null){
            merchandiseInsertUpdateDTO = repository.getMerchandise(merchandiseId);
        }
        return merchandiseInsertUpdateDTO;
    }

    @Override
    public void save(MerchandiseInsertUpdateDTO merchandiseInsertUpdateDTO){
        Merchandise merchandise = new Merchandise();
        merchandise.setMerchandiseId(merchandiseInsertUpdateDTO.getMerchandiseId());
        merchandise.setMerchandiseName(merchandiseInsertUpdateDTO.getMerchandiseName());
        merchandise.setPrice(merchandiseInsertUpdateDTO.getPrice());
        merchandise.setDiscontinue(merchandiseInsertUpdateDTO.getDiscontinue() != null);
        merchandise.setDescription(merchandiseInsertUpdateDTO.getDescription().equals("") ? null : merchandiseInsertUpdateDTO.getDescription());
        merchandise.setCategoryId(merchandiseInsertUpdateDTO.getCategoryId());
        merchandise.setSellerUsername(merchandiseInsertUpdateDTO.getSellerUsername());
        repository.save(merchandise);
    }

    @Override
    public void delete(Integer merchandiseId){
        Merchandise merchandise = repository.findById(merchandiseId).orElseThrow();
        repository.delete(merchandise);
    };

    @Override
    public void discontinue(Integer merchandiseId){
        Merchandise merchandise = repository.findById(merchandiseId).orElseThrow();
        merchandise.setDiscontinue(true);
        repository.save(merchandise);
    }

    @Override
    public MerchandiseInfoDTO getMerchandiseInfo(Integer merchandiseId){
        Merchandise merchandise = repository.findById(merchandiseId).orElseThrow();
        MerchandiseInfoDTO merchandiseInfoDTO = new MerchandiseInfoDTO();
        merchandiseInfoDTO.setMerchandiseName(merchandise.getMerchandiseName());
        merchandiseInfoDTO.setDescription(merchandise.getDescription());
        merchandiseInfoDTO.setCategoryName(merchandise.getCategory().getCategoryName());
        merchandiseInfoDTO.setPrice(PriceHelper.parsePrice(merchandise.getPrice(), new Locale("id", "ID")));
        merchandiseInfoDTO.setDiscontinue(merchandise.getDiscontinue() ? "Yes" : "No");
        merchandiseInfoDTO.setSellerName(merchandise.getSeller().getName());
        return merchandiseInfoDTO;
    };

    private Boolean checkDependentMerchandise(Integer merchandiseId){
        return orderDetailRepository.checkDependentMerchandise(merchandiseId) > 0;
    }

}
