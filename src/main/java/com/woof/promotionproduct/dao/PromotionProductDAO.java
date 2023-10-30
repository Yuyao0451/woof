package com.woof.promotionproduct.dao;

import com.woof.promotionproduct.entity.PromotionProduct;

import java.util.List;

public interface PromotionProductDAO {
	
	void insert (PromotionProduct promotionProductVO);

	void delete (PromotionProduct promotionProductVO);
		
	PromotionProduct findByProdNo(Integer prodNo);
	PromotionProduct findByPaNo(Integer paNo);
	
	List<PromotionProduct> getAll();
}
