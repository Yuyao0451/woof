package com.woof.productphoto.controller;

import com.woof.productphoto.entity.ProductPhoto;
import com.woof.productphoto.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-photos")
public class ProductPhotoController {

    @Autowired
    private ProductPhotoService service;

    @GetMapping
    public List<ProductPhoto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductPhoto> getById(@PathVariable Integer id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductPhoto create(@RequestBody ProductPhoto productPhoto) {
        return service.save(productPhoto);
    }

    @PutMapping("/{id}")
    public ProductPhoto update(@PathVariable Integer id, @RequestBody ProductPhoto productPhoto) {
        productPhoto.setProdPhotoNo(id);
        return service.save(productPhoto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}