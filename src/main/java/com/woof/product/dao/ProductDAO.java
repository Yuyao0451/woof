package com.woof.product.dao;

import java.util.List;

import com.woof.product.entity.Product;

public interface ProductDAO {
    public void insert(Product product);
    public void update(Product product);
    public void delete(Integer prodNo);
    public Product findByProdNo(Integer prodNo);
    public List<Product> getAll();
}



