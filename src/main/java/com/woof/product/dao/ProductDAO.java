package com.woof.product.dao;

import java.util.List;
import java.util.Map;

import com.woof.product.entity.Product;

public interface ProductDAO {
	int insert(Product product);

	int update(Product product);

	int delete(Integer prodNo);

	Product findByProdNo(Integer prodNo);

	List<Product> getAll();

	List<Product> getByCompositeQuery(Map<String, String> map);

	List<Product> getAll(int currentPage);

	long getTotal();
}
