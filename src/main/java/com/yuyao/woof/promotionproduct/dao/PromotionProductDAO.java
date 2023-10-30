package com.yuyao.woof.promotionproduct.dao;

import com.yuyao.woof.promotionproduct.entity.PromotionProductVO;

import java.util.List;

public interface PromotionProductDAO {
	
	void insert (PromotionProductVO promotionProductVO);

	void delete (PromotionProductVO promotionProductVO);
		
	PromotionProductVO findByProdNo(Integer prodNo);
	PromotionProductVO findByPaNo(Integer paNo);
	
	List<PromotionProductVO> getAll();
}
