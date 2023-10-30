package com.woof.product.service;

import com.woof.product.entity.Product;
import com.woof.product.dao.ProductRepository;
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

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(int id) {
        return repository.findById(id);
    }

    public String deleteProduct(int id) {
        repository.deleteById(id);
        return "Product removed: " + id;
    }

    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getProdNo()).orElse(null);
        existingProduct.setProdName(product.getProdName());
        existingProduct.setProdPrice(product.getProdPrice());
        return repository.save(existingProduct);
    }
}