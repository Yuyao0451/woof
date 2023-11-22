package com.woof.product.service;

import com.woof.product.dao.ProductRepository;
import com.woof.product.entity.Product;
import com.woof.product.entity.ProductCategory;
import com.woof.product.entity.ProductStatus;
import com.woof.promotionactivity.dao.PromotionActivityRepository;
import com.woof.promotionactivity.entity.PromotionActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private PromotionActivityRepository promotionActivityRepository;


    public ProductDto saveProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        // 在這裡處理 prodPhoto 的二進制數據
        product.setProdPhoto(productDto.getProdPhoto());
        Product savedProduct = repository.save(product);
        return convertToDto(savedProduct);
    }


    public ProductDto getProductById(int prodNo) {
        Optional<Product> productOptional = repository.findById(prodNo);
        ProductDto dto = productOptional.map(this::convertToDto).orElse(null);
        return dto;
    }

    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        if (productDto.getProdNo() != null) {
            product.setProdNo(productDto.getProdNo());
        }
        product.setProdCatName(getCategoryByName(productDto.getProdCatName()));
        product.setProdContent(productDto.getProdContent());
        product.setProdPrice(productDto.getProdPrice());
        product.setProdName(productDto.getProdName());
        product.setProdStatus(getStatusByName(productDto.getProdStatus()));
        product.setProdPhoto(productDto.getProdPhoto());
        product.setPromoId(productDto.getPromoId());
        return product;
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
        dto.setProdPhoto(product.getProdPhoto());
        dto.setPromoId(product.getPromoId());
        dto.setIsPromotion(product.getPromoId() != null);
        // 設置折扣價格
        if (product.getPromoId() != null) {
            // 從促銷活動repository中找到相應的促銷活動
            Optional<PromotionActivity> promoActivity = promotionActivityRepository.findById(product.getPromoId());
            if (promoActivity.isPresent()) {
                BigDecimal discount = promoActivity.get().getPaDiscount();
                // 計算折扣後的價格
                BigDecimal promoPrice = new BigDecimal(product.getProdPrice()).multiply(discount);
                dto.setPromoPrice(promoPrice.intValue());
            }
        }

        return dto;
    }

    public void resetPromoId() {
        List<Product> products = repository.findAll();
        for (Product product : products) {
            product.setPromoId(null);
            repository.save(product);
        }
    }


    //使用displayname獲得enum值
    private ProductCategory getCategoryByName(String displayName) {
        return Arrays.stream(ProductCategory.values())
                .filter(category -> category.getDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category name"));
    }

    //使用displayname獲得enum值
    private ProductStatus getStatusByName(String displayName) {
        return Arrays.stream(ProductStatus.values())
                .filter(status -> status.getDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status name"));
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


    public Page<ProductDto> getProductsPaged(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return products.map(this::convertToDto);
    }

    public ProductDto toggleProductStatus(int prodNo, boolean activate) {
        Optional<Product> productOptional = repository.findById(prodNo);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProdStatus(activate ? ProductStatus.ON_SHELF : ProductStatus.OFF_SHELF);
            Product updatedProduct = repository.save(product);
            return convertToDto(updatedProduct);
        }
        return null;
    }


    public List<ProductDto> getProductsByCategory(String category) {
        ProductCategory productCategory = getCategoryByName(category);
        List<Product> products = repository.findByProdCatName(productCategory);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    public List<ProductDto> getPromotionProducts() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .filter(ProductDto::isPromotion)
                .collect(Collectors.toList());
    }

    public List<ProductDto> searchProductsByName(String prodName) {
        List<Product> products = repository.findByProdNameContaining(prodName);
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
