package com.woof.promotionproduct.dao;

import com.woof.promotionproduct.entity.PromotionProduct;
import com.woof.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PromotionProductDAOImpl implements PromotionProductDAO {
	
	private static final String INSERT_STMT = "INSERT INTO promotion_product (prod_no, pa_no) VALUES ( ? , ? )";
	private static final String DELETE_STMT = "DELETE FROM promotion_product WHERE prod_no = ? and pa_no = ?";
	private static final String FIND_BY_PRODNO = "SELECT * FROM promotion_product WHERE prod_no = ?";
	private static final String FIND_BY_PANO = "SELECT * FROM promotion_product WHERE pa_no = ?";
	private static final String GET_ALL = "SELECT * FROM promotion_product";
	
	
	@Override
    public void insert(PromotionProduct promotionProductVO) {
        Connection con = null;
        PreparedStatement ps = null;
        int count = 0;

        try {
            con = Util.getConnection();
            ps = con.prepareStatement(INSERT_STMT);
            ps.setInt(1, promotionProductVO.getProdNo());
            ps.setInt(2, promotionProductVO.getPaNo());

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
	    public void delete(PromotionProduct promotionProductVO) {
	        Connection con = null;
	        PreparedStatement ps = null;
	        int count = 0;

	        try {
	            con = Util.getConnection();
	            ps = con.prepareStatement(DELETE_STMT);
	            ps.setInt(1, promotionProductVO.getProdNo());
	            ps.setInt(2, promotionProductVO.getPaNo());
	            
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
	    public PromotionProduct findByProdNo(Integer prodNo) {

	        Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        PromotionProduct promotionProductVO = null;

	        try {
	            con = Util.getConnection();
	            ps = con.prepareStatement(FIND_BY_PRODNO);
	            ps.setInt(1, prodNo);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	            	promotionProductVO = new PromotionProduct();
	            	promotionProductVO.setProdNo(prodNo);
	            	promotionProductVO.setPaNo(rs.getInt("pa_no"));

	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            Util.closeResources(con, ps, rs);
	        }

	        return promotionProductVO;
	    }
	 
	 @Override
	    public PromotionProduct findByPaNo(Integer paNo) {

	        Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        PromotionProduct promotionProductVO = null;

	        try {
	            con = Util.getConnection();
	            ps = con.prepareStatement(FIND_BY_PANO);
	            ps.setInt(1, paNo);
	            rs = ps.executeQuery();

	            if (rs.next()) {
	            	promotionProductVO = new PromotionProduct();
	            	promotionProductVO.setPaNo(paNo);
	            	promotionProductVO.setProdNo(rs.getInt("prod_no"));

	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            Util.closeResources(con, ps, rs);
	        }

	        return promotionProductVO;
	    }
	
	 @Override
	    public List<PromotionProduct> getAll() {

	        Connection con = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        List<PromotionProduct> promotionProductVOList = new ArrayList<>();

	        try {
	            con = Util.getConnection();
	            ps = con.prepareStatement(GET_ALL);
	            rs = ps.executeQuery();

	            while (rs.next()) {
	            	PromotionProduct promotionProductVO = new PromotionProduct();
	            	promotionProductVO.setProdNo(rs.getInt("prod_no"));
	            	promotionProductVO.setPaNo(rs.getInt("pa_no"));
	            	
	            	promotionProductVOList.add(promotionProductVO);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            Util.closeResources(con, ps, rs);
	        }

	        return promotionProductVOList;
	    }
	 
	 public static void main(String[] args){
		 PromotionProductDAO promotionProductDAO = new PromotionProductDAOImpl();
		 
		//查詢多筆資料
//       List<PromotionProductVO> promotionProductVOList = promotionProductDAO.getAll();
//       
//       for (PromotionProductVO PromotionProductVOList: promotionProductVOList){
//           System.out.println(PromotionProductVOList.getProdNo());
//           System.out.println(PromotionProductVOList.getPaNo());
//       }
		 
		 
//       新增單筆資料
//       PromotionProductVO promotionProduct = new PromotionProductVO();
//       promotionProduct.setProdNo(1);
//       promotionProduct.setPaNo(2);
//	   	
//       promotionProductDAO.insert(promotionProduct);
//			
//		}	 
		 
		 
	        // 刪除單筆資料
//		 PromotionProductVO promotionProduct = new PromotionProductVO();	
//		 promotionProduct.setProdNo(1);
//		 promotionProduct.setPaNo(2);
//	    		
//		 promotionProductDAO.delete(promotionProduct);  
//		 }
		  
	       //findByProdNo查詢資料	
//		 PromotionProductVO promotionProduct = promotionProductDAO.findByProdNo(1);
// 		 System.out.println(promotionProduct);
// 		 }
		 
		 //findByPaNo查詢資料	
		 PromotionProduct promotionProduct = promotionProductDAO.findByPaNo(2);
 		 System.out.println(promotionProduct);
 		 }
		 
	 
}
