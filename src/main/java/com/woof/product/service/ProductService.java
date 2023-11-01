package com.woof.product.service;

import com.woof.product.dao.ProductRepository;
import com.woof.product.entity.Product;
import com.woof.product.entity.ProductCategory;
import com.woof.product.entity.ProductStatus;
import com.woof.productphoto.entity.ProductPhoto;
import com.woof.productphoto.service.ProductPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductPhotoService productPhotoService;


    public ProductDto saveProduct(Product product) {
        Product savedProduct = repository.save(product);
        return convertToDto(savedProduct);
    }


    public ProductDto getProductById(int prodNo) {
        Optional<Product> productOptional = repository.findById(prodNo);
        ProductDto dto = productOptional.map(this::convertToDto).orElse(null);
        if (dto != null) {
            // 從ProductPhotoService獲取商品照片
            List<ProductPhoto> photos = productPhotoService.getProductPhotosByProdNo(prodNo);

            // 將商品照片的二進制數據轉換為Base64編碼
            List<String> base64Photos = photos.stream()
                    .map(ProductPhoto::getProdPhoto)
                    .map(photo -> Base64.getEncoder().encodeToString(photo))
                    .collect(Collectors.toList());

            // 將Base64編碼的照片設置到ProductDto對象中
            dto.setBase64ProductPhotos(base64Photos);
//            System.out.println("Base64 Photos: " + base64Photos); //測試是否有作用
        }
        return dto;
    }





    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    public ProductDto updateProduct(Product product) {
        Product updatedProduct = repository.save(product);
        return convertToDto(updatedProduct);
    }


    public List<String> getProductCategories() {
        return Arrays.stream(ProductCategory.values())
                .map(ProductCategory::getDisplayName)
                .collect(Collectors.toList());
    }

    public List<String> getProductStatuses() {
        return Arrays.stream(ProductStatus.values())
                .map(ProductStatus::getDisplayName)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProducts() {
        List<Product> products = repository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProdNo(product.getProdNo());
        dto.setProdCatName(product.getProdCatName().getDisplayName());
        dto.setProdContent(product.getProdContent());
        dto.setProdPrice(product.getProdPrice());
        dto.setProdName(product.getProdName());
        dto.setProdStatus(product.getProdStatus().getDisplayName());
        return dto;
    }

    public Page<ProductDto> getProductsPaged(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return products.map(this::convertToDto);
    }

    public ProductDto toggleProductStatus(int prodNo) {
        Optional<Product> productOptional = repository.findById(prodNo);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getProdStatus() == ProductStatus.ON_SHELF) {
                product.setProdStatus(ProductStatus.OFF_SHELF);
            } else {
                product.setProdStatus(ProductStatus.ON_SHELF);
            }
            Product updatedProduct = repository.save(product);
            return convertToDto(updatedProduct);
        }
        return null;
    }

}
