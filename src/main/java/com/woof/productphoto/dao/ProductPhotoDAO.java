package com.woof.productphoto.dao;

import java.util.List;
import java.util.Map;

import com.woof.productphoto.entity.ProductPhoto;

public interface ProductPhotoDAO {
	int insert(ProductPhoto productPhoto);

	int update(ProductPhoto productPhoto);

	int delete(Integer prodPhotoNo);

	ProductPhoto findByProdPhotoNo(Integer prodPhotoNo);

	List<ProductPhoto> getAll();

	List<ProductPhoto> getByCompositeQuery(Map<String, String> map);

	List<ProductPhoto> getAll(int currentPage);

	long getTotal();
}

