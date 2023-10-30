package com.woof.promotionactivity.service;

import com.woof.promotionactivity.entity.PromotionActivity;
import com.woof.promotionactivity.dao.PromotionActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionActivityService {

    @Autowired
    private PromotionActivityRepository repository;

    public List<PromotionActivity> getAll() {
        return repository.findAll();
    }

    public Optional<PromotionActivity> getById(Integer id) {
        return repository.findById(id);
    }

    public PromotionActivity save(PromotionActivity promotionActivity) {
        return repository.save(promotionActivity);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}