package com.woof.productcategory.controller;

import com.woof.productcategory.entity.ProductCategory;
import com.woof.productcategory.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService service;

    @GetMapping
    public List<ProductCategory> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductCategory create(@RequestBody ProductCategory productCategory) {
        return service.save(productCategory);
    }

    @PutMapping("/{id}")
    public ProductCategory update(@PathVariable Integer id, @RequestBody ProductCategory productCategory) {
        productCategory.setProdCatNo(id);
        return service.save(productCategory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}