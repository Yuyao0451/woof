package com.woof.promotionactivity.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.woof.promotionactivity.entity.PromotionActivity;

public interface PromotionActivityService {

//	PromotionActivity addPromotionActivity(PromotionActivity promotionActivity);
	
	PromotionActivity addPromotionActivity(String paName, BigDecimal paDiscount, String paContent, Timestamp paStart, Timestamp paEnd, Boolean paStatus);

	PromotionActivity updatePromotionActivity(PromotionActivity promotionActivity);

//	void deletePromotionActivity(Integer paNo);

	PromotionActivity findPromotionActivityByPaNo(Integer paNo);

	List<PromotionActivity> getAllPromotionActivity();

}
