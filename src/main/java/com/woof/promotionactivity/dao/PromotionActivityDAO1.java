package com.woof.promotionactivity.dao;

import java.util.List;

import com.woof.promotionactivity.entity.PromotionActivity;

public interface PromotionActivityDAO1 {

	int insert(PromotionActivity promotionActivity);

	int update(PromotionActivity promotionActivity);

//	int delete(Integer paNo);

	PromotionActivity findByPaNo(Integer paNo);

	List<PromotionActivity> getAll();

}
