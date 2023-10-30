package com.woof.promotionactivity.dao;

import com.woof.promotionactivity.entity.PromotionActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionActivityRepository extends JpaRepository<PromotionActivity, Integer> {
}