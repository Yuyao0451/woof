package com.woof.productphoto.controller;

import com.woof.productphoto.entity.ProductPhoto;
import com.woof.productphoto.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductPhotoController {

    @Autowired
    private ProductPhotoService service;

    @GetMapping("/productPhotos")
    public List<ProductPhoto> getAll() {
        return service.getAll();
    }

    @GetMapping("/productPhotos/{prodNo}")
    public List<ProductPhoto> findPhotosByProdNo(@PathVariable Integer prodNo) {
        return service.getProductPhotosByProdNo(prodNo);
    }

    @PostMapping("/addPhoto/{id}")
    public ProductPhoto create(@RequestBody ProductPhoto productPhoto) {
        return service.save(productPhoto);
    }

    @PutMapping("/updatePhoto/{id}")
    public ProductPhoto update(@PathVariable Integer id, @RequestBody ProductPhoto productPhoto) {
        productPhoto.setProdPhotoNo(id);
        return service.save(productPhoto);
    }

    @DeleteMapping("/deletePhoto/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}