package com.woof.promotionproduct.dao;

import com.woof.promotionproduct.entity.PromotionProduct;
import com.woof.promotionproduct.entity.PromotionProduct.CompositeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProductRepository extends JpaRepository<PromotionProduct, CompositeDetail> {
}