package com.woof.product.controller;

import com.woof.product.service.ProductDto;
import com.woof.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private ProductService productService;

    @GetMapping("/productPage")
    public String showProductPage(Model model) {
        List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        return "productPage";  // 對應到Thymeleaf的HTML文件
    }
}
