package com.woof.product.dao;

import com.woof.product.entity.Product;
import com.woof.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProdCatName(ProductCategory prodCatName);
}