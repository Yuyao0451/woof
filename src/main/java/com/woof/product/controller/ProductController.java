package com.woof.product.controller;

import com.woof.product.entity.Product;
import com.woof.product.service.ProductDto;
import com.woof.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public ProductDto addProduct(@RequestBody Product product) {
        return service.saveProduct(product);
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

    @GetMapping("/productsPaged")//分頁
    public Page<ProductDto> findAllProductsPaged(Pageable pageable) {
        return service.getProductsPaged(pageable);
    }


    @PutMapping("/update")
    public ProductDto updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
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
