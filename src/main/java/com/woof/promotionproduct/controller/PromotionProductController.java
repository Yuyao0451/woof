package com.woof.promotionproduct.controller;

import com.woof.promotionproduct.entity.PromotionProduct;
import com.woof.promotionproduct.entity.PromotionProduct.CompositeDetail;
import com.woof.promotionproduct.service.PromotionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotion-products")
public class PromotionProductController {

    @Autowired
    private PromotionProductService service;

    @GetMapping
    public List<PromotionProduct> getAll() {
        return service.getAll();
    }

    @GetMapping("/{prodNo}/{paNo}")
    public ResponseEntity<PromotionProduct> getById(@PathVariable Integer prodNo, @PathVariable Integer paNo) {
        CompositeDetail id = new CompositeDetail(prodNo, paNo);
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PromotionProduct create(@RequestBody PromotionProduct promotionProduct) {
        return service.save(promotionProduct);
    }

    @PutMapping("/{prodNo}/{paNo}")
    public PromotionProduct update(@PathVariable Integer prodNo, @PathVariable Integer paNo, @RequestBody PromotionProduct promotionProduct) {
        promotionProduct.setCompositeKey(new CompositeDetail(prodNo, paNo));
        return service.save(promotionProduct);
    }

    @DeleteMapping("/{prodNo}/{paNo}")
    public void delete(@PathVariable Integer prodNo, @PathVariable Integer paNo) {
        service.deleteById(new CompositeDetail(prodNo, paNo));
    }
}