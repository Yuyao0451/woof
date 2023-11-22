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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(
            @RequestParam("prodCatName") String prodCatName,
            @RequestParam("prodContent") String prodContent,
            @RequestParam("prodPrice") Integer prodPrice,
            @RequestParam("prodName") String prodName,
            @RequestParam("prodStatus") String prodStatus,
            @RequestParam("prodPhoto") MultipartFile prodPhoto){
        ProductDto productDto = new ProductDto();
        productDto.setProdCatName(prodCatName);
        productDto.setProdContent(prodContent);
        productDto.setProdPrice(prodPrice);
        productDto.setProdName(prodName);
        productDto.setProdStatus(prodStatus);
        try {
            productDto.setProdPhoto(prodPhoto.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        ProductDto savedProductDto = service.saveProduct(productDto);
        return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{prodNo}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Integer prodNo,
            @RequestParam(value = "prodCatName", required = false) String prodCatName,
            @RequestParam(value = "prodContent", required = false) String prodContent,
            @RequestParam(value = "prodPrice", required = false) Integer prodPrice,
            @RequestParam(value = "prodName", required = false) String prodName,
            @RequestParam(value = "prodStatus", required = false) String prodStatus,
            @RequestParam(value = "prodPhoto", required = false) MultipartFile prodPhoto,
            @RequestParam(value = "promoId", required = false) Integer promoId,
            @RequestParam(value = "updatePromoId", required = false, defaultValue = "false") boolean updatePromoId) {

        // 從資料庫中獲取現有商品資料
        ProductDto existingProductDto = service.getProductById(prodNo);
        if (existingProductDto == null) {
            // 如果商品不存在，返回404錯誤
            return ResponseEntity.notFound().build();
        }

        // 更新商品資料
        if (prodCatName != null) existingProductDto.setProdCatName(prodCatName);
        if (prodContent != null) existingProductDto.setProdContent(prodContent);
        if (prodPrice != null) existingProductDto.setProdPrice(prodPrice);
        if (prodName != null) existingProductDto.setProdName(prodName);
        if (prodStatus != null) existingProductDto.setProdStatus(prodStatus);
        if (updatePromoId) {
            existingProductDto.setPromoId(promoId);
        }

        // 處理照片更新
        if (prodPhoto != null && !prodPhoto.isEmpty()) {
            try {
                existingProductDto.setProdPhoto(prodPhoto.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // 儲存更新後的商品
        ProductDto updatedProductDto = service.saveProduct(existingProductDto);
        return ResponseEntity.ok(updatedProductDto);
    }


    @GetMapping("/products")
    public List<ProductDto> findAllProducts() {
        return service.getProducts();
    }

    //促銷商品
    @GetMapping("/promotionProducts")
    public ResponseEntity<List<ProductDto>> findPromotionProducts() {
        List<ProductDto> promotionProducts = service.getPromotionProducts();
        if (promotionProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(promotionProducts, HttpStatus.OK);
    }

    @PutMapping("/resetPromoId")
    public ResponseEntity<Void> resetPromotionForAllProducts() {
        try {
            service.resetPromoId();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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


    @PutMapping("/toggleStatus/{prodNo}/{activate}")
    public ResponseEntity<ProductDto> toggleProductStatus(@PathVariable int prodNo, @PathVariable boolean activate) {
        ProductDto productDto = service.toggleProductStatus(prodNo, activate);
        if (productDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/productsByCategory/{category}")
    public ResponseEntity<List<ProductDto>> findProductsByCategory(@PathVariable String category) {
        List<ProductDto> products = service.getProductsByCategory(category);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/searchProducts")
    public ResponseEntity<List<ProductDto>> searchProductsByName(@RequestParam String prodName) {
        List<ProductDto> products = service.searchProductsByName(prodName);
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
