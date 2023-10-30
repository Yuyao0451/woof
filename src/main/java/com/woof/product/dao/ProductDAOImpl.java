package com.woof.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.woof.product.entity.Product;
import com.woof.util.Util;

public class ProductDAOImpl implements ProductDAO {

	private static final String INSERT_STMT = "INSERT INTO PRODUCT (PROD_CAT_NO, PROD_CONTENT, PROD_PRICE, PROD_NAME, PROD_STATUS) VALUES (?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE PRODUCT SET PROD_CAT_NO = ?, PROD_CONTENT = ?, PROD_PRICE = ?, PROD_NAME = ?, PROD_STATUS = ? WHERE PROD_NO = ?";
	private static final String DELETE_STMT = "DELETE FROM PRODUCT WHERE PROD_NO = ?";
	private static final String FIND_BY_PRODNO = "SELECT * FROM PRODUCT WHERE PROD_NO = ?";
	private static final String GET_ALL = "SELECT * FROM PRODUCT";

	@Override
	public void insert(Product product) {
		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			con = Util.getConnection();
			ps = con.prepareStatement(INSERT_STMT);
			ps.setInt(1, product.getProdCatNo());
			ps.setString(2, product.getProdContent());
			ps.setInt(3, product.getProdPrice());
			ps.setString(4, product.getProdName());
			ps.setBoolean(5, product.getProdStatus());

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeResources(con, ps, null);
		}
		if (count == 1) {
			System.out.println("新增成功");
		} else {
			System.out.println("新增失敗");
		}
	}

	@Override
	public void update(Product product) {
		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;

		try {
			con = Util.getConnection();
			ps = con.prepareStatement(UPDATE_STMT);

			ps.setInt(1, product.getProdCatNo());
			ps.setString(2, product.getProdContent());
			ps.setInt(3, product.getProdPrice());
			ps.setString(4, product.getProdName());
			ps.setBoolean(5, product.getProdStatus());
			ps.setInt(6, product.getProdNo());

			count = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeResources(con, ps, null);
		}
		if (count == 1) {
			System.out.println("修改成功");
		} else {
			System.out.println("修改失敗");
		}
	}

	@Override
	public void delete(Integer prodNo) {
		Connection con = null;
		PreparedStatement ps = null;
		int count = 0;
		System.out.println("------------------------");
		System.out.println("prodNo=" + prodNo);
		System.out.println("------------------------");
		try {
			con = Util.getConnection();
			ps = con.prepareStatement(DELETE_STMT);
			ps.setInt(1, prodNo);

			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeResources(con, ps, null);
		}
		if (count == 1) {
			System.out.println("刪除成功");
		} else {
			System.out.println("刪除失敗");
		}
	}

	@Override
	public List<Product> getAll() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Product> productList = new ArrayList<>();

		try {
			con = Util.getConnection();
			ps = con.prepareStatement(GET_ALL);
			rs = ps.executeQuery();

			while (rs.next()) {
				Product product = new Product();
				product.setProdNo(rs.getInt("PROD_NO"));
				product.setProdCatNo(rs.getInt("PROD_CAT_NO"));
				product.setProdContent(rs.getString("PROD_CONTENT"));
				product.setProdPrice(rs.getInt("PROD_PRICE"));
				product.setProdName(rs.getString("PROD_NAME"));
				product.setProdStatus(rs.getBoolean("PROD_STATUS"));
				productList.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeResources(con, ps, rs);
		}

		return productList;
	}

	@Override
	public Product findByProdNo(Integer prodNo) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Product product = null;

		try {
			con = Util.getConnection();
			ps = con.prepareStatement(FIND_BY_PRODNO);
			ps.setInt(1, prodNo);
			rs = ps.executeQuery();

			if (rs.next()) {
				product = new Product();
				product.setProdNo(rs.getInt("PROD_NO"));
				product.setProdCatNo(rs.getInt("PROD_CAT_NO"));
				product.setProdContent(rs.getString("PROD_CONTENT"));
				product.setProdPrice(rs.getInt("PROD_PRICE"));
				product.setProdName(rs.getString("PROD_NAME"));
				product.setProdStatus(rs.getBoolean("PROD_STATUS"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeResources(con, ps, rs);
		}

		return product;
	}

}
