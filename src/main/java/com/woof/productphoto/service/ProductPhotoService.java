package com.woof.productphoto.service;

import com.woof.productphoto.entity.ProductPhoto;
import com.woof.productphoto.dao.ProductPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductPhotoService {

    @Autowired
    private ProductPhotoRepository repository;

    public List<ProductPhoto> getAll() {
        return repository.findAll();
    }

    public List<ProductPhoto> getProductPhotosByProdNo(Integer prodNo) {
        return repository.findByProdNo(prodNo);
    }

    public ProductPhoto save(ProductPhoto productPhoto) {
        return repository.save(productPhoto);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}