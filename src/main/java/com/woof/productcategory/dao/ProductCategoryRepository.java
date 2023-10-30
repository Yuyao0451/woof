package com.woof.productcategory.dao;

import com.woof.product.entity.Product;
import com.woof.productcategory.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
