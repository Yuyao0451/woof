package com.woof.product.controller;

import com.woof.product.entity.Product;
import com.woof.product.service.ProductDto;
import com.woof.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto savedProductDto = service.saveProduct(productDto);
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public List<ProductDto> findAllProducts() {
        return service.getProducts();
    }

    @GetMapping("/productById/{prodNo}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable int prodNo) {
        ProductDto productDto = service.getProductById(prodNo);
        if (productDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/productImage/{prodNo}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int prodNo) {
        byte[] imageBytes = service.getProductImage(prodNo);
        if (imageBytes != null) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG) // 根據圖片實際類型設定
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/productsPaged")//分頁
    public Page<ProductDto> findAllProductsPaged(Pageable pageable) {
        return service.getProductsPaged(pageable);
    }


    @PutMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = service.saveProduct(productDto);
        return ResponseEntity.ok(updatedProductDto);
    }

    @DeleteMapping("/delete/{prodNo}")
    public void deleteProduct(@PathVariable int prodNo) {
        service.deleteProduct(prodNo);
    }

    @GetMapping("/productCategories")
    public List<String> getProductCategories() {
        return service.getProductCategories();
    }

    @GetMapping("/productStatuses")
    public List<String> getProductStatuses() {
        return service.getProductStatuses();
    }


    @PutMapping("/toggleStatus/{prodNo}")
    public ResponseEntity<ProductDto> toggleProductStatus(@PathVariable int prodNo) {
        ProductDto productDto = service.toggleProductStatus(prodNo);
        if (productDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }


}
