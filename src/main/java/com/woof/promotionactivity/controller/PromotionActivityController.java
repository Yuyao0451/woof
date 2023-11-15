package com.woof.promotionactivity.controller;

import com.woof.promotionactivity.entity.PromotionActivity;
import com.woof.promotionactivity.service.PromotionActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion")
public class PromotionActivityController {

    @Autowired
    private PromotionActivityService service;

    @GetMapping
    public Page<PromotionActivity> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getAll(PageRequest.of(page, size));
    }


    @GetMapping("/{id}")
    public ResponseEntity<PromotionActivity> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PromotionActivity create(@RequestBody PromotionActivity promotionActivity) {
        return service.save(promotionActivity);
    }

    @PutMapping("/{id}")
    public PromotionActivity update(@PathVariable Integer id, @RequestBody PromotionActivity promotionActivity) {
        promotionActivity.setPaNo(id);
        return service.save(promotionActivity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}