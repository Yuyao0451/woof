package com.woof.product.service;

import com.woof.product.dao.ProductRepository;
import com.woof.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return repository.findById(id);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    public Product updateProduct(Product product) {
        return repository.save(product);
    }
}
