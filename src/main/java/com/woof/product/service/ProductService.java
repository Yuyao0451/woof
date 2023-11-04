package com.woof.product.service;

import com.woof.product.dao.ProductRepository;
import com.woof.product.entity.Product;
import com.woof.product.entity.ProductCategory;
import com.woof.product.entity.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;


    public ProductDto saveProduct(Product product) {
        Product savedProduct = repository.save(product);
        return convertToDto(savedProduct);
    }


    public ProductDto getProductById(int prodNo) {
        Optional<Product> productOptional = repository.findById(prodNo);
        ProductDto dto = productOptional.map(this::convertToDto).orElse(null);
        return dto;
    }


    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    public ProductDto updateProduct(Product product) {
        Product updatedProduct = repository.save(product);
        return convertToDto(updatedProduct);
    }

    public byte[] getProductImage(int prodNo) {
        return repository.findById(prodNo)
                .map(Product::getProdPhoto)
                .orElse(null);
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
        // 直接使用二進制數據
        if (product.getProdPhoto() != null) {
            dto.setProductPhotos(List.of(product.getProdPhoto()));
        }
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
