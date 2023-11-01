package com.woof.productphoto.dao;

import com.woof.productphoto.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {
    List<ProductPhoto> findByProdNo(Integer prodNo);
}