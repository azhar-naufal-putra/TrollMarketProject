package com.TrollMarket.utility;

import com.TrollMarket.dto.CategoryOptionDTO;
import com.TrollMarket.entity.Merchandise;
import com.TrollMarket.repository.CategoryRepository;
import com.TrollMarket.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService{
    private final CategoryRepository repository;
    private final MerchandiseRepository merchandiseRepository;

    @Autowired
    public CategoryServiceImplementation(CategoryRepository repository, MerchandiseRepository merchandiseRepository){
        this.repository = repository;
        this.merchandiseRepository = merchandiseRepository;
    }

    @Override
    public List<CategoryOptionDTO> getCategoryOptions(Integer merchandiseId){
        Merchandise merchandise = new Merchandise();
        if(merchandiseId != null){
            merchandise = merchandiseRepository.findById(merchandiseId).orElseThrow();
        }
        return repository.getCategoryOptions(merchandise.getCategoryId());
    };
    @Override
    public List<CategoryOptionDTO> getCategoryOptionByCategoryId(Integer categoryId){
        return repository.getCategoryOptions(categoryId);
    };
}
