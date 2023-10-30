package com.woof.productcategory.service;

import com.woof.productcategory.entity.ProductCategory;
import com.woof.productcategory.dao.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    public List<ProductCategory> getAll() {
        return repository.findAll();
    }

    public Optional<ProductCategory> getById(Integer id) {
        return repository.findById(id);
    }

    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}