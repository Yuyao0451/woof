package com.woof.promotionactivity.dao;

import java.util.List;
import java.util.Map;

import com.woof.promotionactivity.entity.PromotionActivity;

public interface PromotionActivityDAO {

	int insert(PromotionActivity promotionActivity);

	int update(PromotionActivity promotionActivity);

//	int delete(Integer paNo);

	PromotionActivity findByPaNo(Integer paNo);

	List<PromotionActivity> getAll();

}
