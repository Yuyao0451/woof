package com.woof.productcategory.dao;

import java.util.List;
import java.util.Map;

import com.woof.productcategory.entity.ProductCategory;

public interface ProductCategoryDAO {
	int insert(ProductCategory ProductCategory);

	int update(ProductCategory ProductCategory);

	int delete(Integer prodCatNo);

	ProductCategory findByProdCatNo(Integer prodCatNo);

	List<ProductCategory> getAll();

	List<ProductCategory> getByCompositeQuery(Map<String, String> map);

	List<ProductCategory> getAll(int currentPage);

	long getTotal();
}
