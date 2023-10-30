package com.woof.product.service;

import java.util.List;

import com.woof.product.dao.ProductDAO;
import com.woof.product.dao.ProductDAOImpl;
import com.woof.product.entity.Product;

public class ProductService {

    private ProductDAO dao;

    public ProductService() {
        dao = new ProductDAOImpl();
    }

    public Product addProduct(Integer prodNo, Integer prodCatNo, String prodContent, Integer prodPrice, String prodName, Boolean prodStatus) {

        Product product = new Product();

        product.setProdNo(prodNo);
        product.setProdCatNo(prodCatNo);
        product.setProdContent(prodContent);
        product.setProdPrice(prodPrice);
        product.setProdName(prodName);
        product.setProdStatus(prodStatus);
        dao.insert(product);

        return product;
    }

    public Product updateProduct(Integer prodNo, Integer prodCatNo, String prodContent, Integer prodPrice, String prodName, Boolean prodStatus) {

        Product product = new Product();

        product.setProdNo(prodNo);
        product.setProdCatNo(prodCatNo);
        product.setProdContent(prodContent);
        product.setProdPrice(prodPrice);
        product.setProdName(prodName);
        product.setProdStatus(prodStatus);
        dao.update(product);

        return product;
    }

    public void deleteProduct(Integer prodNo) {
        dao.delete(prodNo);
    }

    public Product getOneProduct(Integer prodNo) {
        return dao.findByProdNo(prodNo);
    }

    public List<Product> getAll() {
        return dao.getAll();
    }

	
}