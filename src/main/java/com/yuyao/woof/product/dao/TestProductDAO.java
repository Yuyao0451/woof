package com.yuyao.woof.product.dao;

import java.util.List;
import com.woof.product.entity.Product;

public class TestProductDAO {
		public static void main(String[] args) {
			ProductDAO productDAO = new ProductDAOImpl();
			Product productVO = new Product();

//			// 新增
//			productVO.setProdCatNo(1);
//			productVO.setProdContent("狗狗乾飼料");
//			productVO.setProdPrice(300);
//			productVO.setProdName("狗飼料");
//			productVO.setProdStatus(true);
//			productDAO.insert(productVO);

//			// 修改
//			productVO.setProdCatNo(1);
//			productVO.setProdContent("狗狗玩具球");
//			productVO.setProdPrice(50);
//			productVO.setProdName("狗玩具球");
//			productVO.setProdStatus(true);
//			productVO.setProdNo(1); // 假設要修改的商品編號是 1
//			productDAO.update(productVO);

//			// 刪除
//			productDAO.delete(1); // 假設要刪除的商品編號是 1

//			// 按照商品編號查詢
//			ProductVO queriedProduct = productDAO.findByProdNo(1);
//			System.out.println(queriedProduct);

//			// 查詢全部
//			List<ProductVO> productList = productDAO.getAll();
//			for (ProductVO prod : productList) {
//				System.out.println(prod);
//			}
		}
	}


