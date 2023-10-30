package com.woof.promotionproduct.service;

import com.woof.promotionproduct.entity.PromotionProduct;
import com.woof.promotionproduct.dao.PromotionProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionProductService {

    @Autowired
    private PromotionProductRepository repository;

    public List<PromotionProduct> getAll() {
        return repository.findAll();
    }

    public Optional<PromotionProduct> getById(PromotionProduct.CompositeDetail id) {
        return repository.findById(id);
    }

    public PromotionProduct save(PromotionProduct promotionProduct) {
        return repository.save(promotionProduct);
    }

    public void deleteById(PromotionProduct.CompositeDetail id) {
        repository.deleteById(id);
    }
}