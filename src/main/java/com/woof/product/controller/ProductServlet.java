package com.woof.product.controller;

import java.io.IOException;
import java.util.*;
import com.woof.product.entity.Product;
import com.woof.product.service.ProductService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp??��?��??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/***************************
			 * 1.接收請求參數 - 輸入格式的錯誤處理
			 **********************/
			String str = req.getParameter("prodNo");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入員工編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/product/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer prodNo = null;
			try {
				prodNo = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("員工編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/product/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/***************************
			 * 2.開始查詢資料
			 *****************************************/
			ProductService productSvc = new ProductService();
			Product product = productSvc.getOneProduct(prodNo);
			if (product == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/product/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/***************************
			 * 3.查詢完成,準備轉交(Send the Success view)
			 *************/
			req.setAttribute("product", product); // 資料庫取出的empVO物件,存入req
			String url = "/product/listOneProduct.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);

		}

//	if("getOne_For_Update".equals(action))
//
//	{ // 修改單一商品listAllEmp.jsp
//
//		List<String> errorMsgs = new LinkedList<String>();
//		// Store this set in the request scope, in case we need to
//		// send the ErrorPage view.
//		req.setAttribute("errorMsgs", errorMsgs);
//		/***************************
//		 * 1.接收請求參數
//		 ****************************************/
//		Integer productno = Integer.valueOf(req.getParameter("prodNo"));
//		/***************************
//		 * 2.開始查詢資料
//		 ****************************************/
//		ProductService productSvc = new ProductService();
//		Product product = productSvc.getOneProduct(prodNo);
//		/***************************
//		 *3.查詢完成,準備轉交(Send the Success view)
//		 ************/
//		req.setAttribute("product", product); // 資料庫取出的empVO物件,存入req
//		String url = "/emp/update_emp_input.jsp";
//		RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
//		successView.forward(req, res);
//	}
//
//	if("update".equals(action))
//	{ // 修改商品update_emp_input.jsp
//
//		List<String> errorMsgs = new LinkedList<String>();
//		// Store this set in the request scope, in case we need to
//		// send the ErrorPage view.
//		req.setAttribute("errorMsgs", errorMsgs);
//		/***************************
//		 * 1.�����ШD�Ѽ� - ��J�榡�����~�B�z
//		 **********************/
//		// 商品編號
//		Integer prodNo = Integer.valueOf(req.getParameter("PROD_NO").trim());
//
//		// 商品名稱
//		String prodName = req.getParameter("PROD_NAME");
//		String productReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
//		if (prodName == null || prodName.trim().length() == 0) {
//			errorMsgs.add("請輸入商品名稱");
//		} else if (!prodName.trim().matches(productReg)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
//			errorMsgs.add("���u�m�W: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
//		}
//
//		// 商品價格
//		Integer prodPrice;
//		try {
//			prodPrice = Integer.valueOf(req.getParameter("PROD_PRICE").trim());
//		} catch (NumberFormatException e) {
//			prodPrice = 0;
//			errorMsgs.add("請輸入價格！");
//		}
//
//		// 商品狀態
////		Boolean prodStatus = 0;
////		try {
////			prodStatus = Integer.valueOf(req.getParameter("PROD_STATUS").trim());
////		} catch (NumberFormatException e) {
////			prodStatus = 0;
////			errorMsgs.add("商品未上架！");
////		}
//
//		
//
//		
//
//		// 商品類別編號
//		Integer prodCatNo = Integer.valueOf(req.getParameter("PROD_CAT_NO").trim());
//		Product product = new Product();
//		product.setProdNo(prodNo);
//		product.setProdName(prodName);
//		product.setProdPrice(prodPrice);
////		product.setProdStatus(prodStatus);
//		product.setProdCatNo(prodCatNo);
//
//		// Send the use back to the form, if there were errors
//		if (!errorMsgs.isEmpty()) {
//			req.setAttribute("product", product); // �t����J�榡���~��empVO����,�]�s�Jreq
//			RequestDispatcher failureView = req.getRequestDispatcher("/emp/update_emp_input.jsp");
//			failureView.forward(req, res);
//			return; // �{�����_
//		}
//		/***************************
//		 * 2.�}�l�ק���
//		 *****************************************/
//		ProductService productSvc = new ProductService();
//		product = productSvc.updateProduct(prodNo, prodName, prodPrice,prodCatNo);
//		/***************************
//		 * 3.�ק粒��,�ǳ����(Send the Success view)
//		 *************/
//		req.setAttribute("product", product); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
//		String url = "/emp/listOneEmp.jsp";
//		RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
//		successView.forward(req, res);
//	}
//  if ("insert".equals(action)) { // 新增商品addEmp.jsp
//
//		List<String> errorMsgs = new LinkedList<String>();
//		// Store this set in the request scope, in case we need to
//		// send the ErrorPage view.
//		req.setAttribute("errorMsgs", errorMsgs);
//
//		// (1)
//		// 商品名稱
//		String productname = req.getParameter("productname");
//		String productReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
//		if (productname == null || productname.trim().length() == 0) {
//			errorMsgs.add("請輸入商品名稱");
//		} else if (!productname.trim().matches(productReg)) { 
//			errorMsgs.add("商品名稱必須為2到30個字的中英數字符號");
//		}
//
//		// 商品價格
//		Integer productprice;
//		try {
//			productprice = Integer.valueOf(req.getParameter("productprice").trim());
//		} catch (NumberFormatException e) {
//			productprice = 0;
//			errorMsgs.add("請輸入價格！");
//		}
//
//		// 商品庫存
//		Integer productquantity;
//		try {
//			productquantity = Integer.valueOf(req.getParameter("productquantity").trim());
//		} catch (NumberFormatException e) {
//			productquantity = 0;
//			errorMsgs.add("請輸入庫存量！");
//		}
//
//		// 商品狀態
//		Integer productstatus = 0;
//		try {
//			productstatus = Integer.valueOf(req.getParameter("productstatus").trim());
//		} catch (NumberFormatException e) {
//			productstatus = 0;
//			errorMsgs.add("商品未上架！");
//		}
//
//		// 商品總評價
//		Integer producttotalreviewcount;
//		try {
//			producttotalreviewcount = Integer.valueOf(req.getParameter("producttotalreviewcount").trim());
//		} catch (NumberFormatException e) {
//			producttotalreviewcount = 0;
//			errorMsgs.add("尚未評價");
//		}
//
//		// 商品評價人數
//		Integer producttotalreviewstatus;
//		try {
//			producttotalreviewstatus = Integer.valueOf(req.getParameter("producttotalreviewstatus").trim());
//		} catch (NumberFormatException e) {
//			producttotalreviewstatus = 0;
//			errorMsgs.add("沒有人評價");
//		}
//
//		// 商品類別編號
//		Integer productcategoryno = Integer.valueOf(req.getParameter("productcategoryno").trim());
//		try {
//			productcategoryno = Integer.valueOf(req.getParameter("productcategoryno").trim());
//		} catch (NumberFormatException e) {
//			productcategoryno = 0;
//			errorMsgs.add("請輸入類別編號");
//		}
//
//		ProductVO productVO = new ProductVO();
//		productVO.setProductname(productname);
//		productVO.setProductprice(productprice);
//		productVO.setProductquantity(productquantity);
//		productVO.setProductstatus(productstatus);
//		productVO.setProducttotalreviewcount(producttotalreviewcount);
//		productVO.setProducttotalreviewstatus(producttotalreviewstatus);
//		productVO.setProductcategoryno(productcategoryno);
//
//		// Send the use back to the form, if there were errors
//		if (!errorMsgs.isEmpty()) {
//			req.setAttribute("productVO", productVO); 
//			RequestDispatcher failureView = req.getRequestDispatcher("/view/addProduct.jsp");
//			failureView.forward(req, res);
//			return;
//		}
//
//		// (2)查詢
//		ProductService productSvc = new ProductService();
//		productVO = productSvc.addProduct(productname, productprice, productquantity, productstatus,
//				producttotalreviewcount, producttotalreviewstatus, productcategoryno);
//
//		// (3)送出畫面
//		String url = "/view/listAllProduct.jsp";
//		RequestDispatcher successView = req.getRequestDispatcher(url); // listAllEmp.jsp
//		successView.forward(req, res);
//	}

		if ("delete".equals(action)) { // 刪除（下架）商品listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			// (1)
			Integer prodNo = Integer.valueOf(req.getParameter("prodNo"));

			// (2)查詢
			ProductService productSvc = new ProductService();
			productSvc.deleteProduct(prodNo);

			// (3)送出畫面
			String url = "/product/listAllProduct.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
	}
 }
