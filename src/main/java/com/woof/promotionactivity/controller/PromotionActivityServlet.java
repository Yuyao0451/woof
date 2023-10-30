package com.woof.promotionactivity.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.woof.promotionactivity.entity.PromotionActivity;
import com.woof.promotionactivity.service.PromotionActivityService;
import com.woof.promotionactivity.service.PromotionActivityServiceImpl;

@WebServlet("/promotionactivity")
public class PromotionActivityServlet extends HttpServlet {

	private PromotionActivityService promotionActivityService;

	@Override
	public void init() throws ServletException {
		promotionActivityService = new PromotionActivityServiceImpl();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
//		add(req, resp);
		
		getAll(req, resp);
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		doPost(req, resp);

	}

	private void getAll(HttpServletRequest req, HttpServletResponse resp) {

		// 搜尋全部活動列表
		String action = req.getParameter("action");
		if ("getAll".equals(action)) {

			// 獲取所有促銷活動的資訊
			List<PromotionActivity> promotionActivityList = promotionActivityService.getAllPromotionActivity();

			// 將促銷活動的資訊儲存到 request 屬性中
			req.setAttribute("promotionActivityList", promotionActivityList);

			try { // 跳轉到 /promotionactivity.jsp 頁面 (下面是路徑)
				req.getRequestDispatcher("/backend/promotionactivity/getAllPA.jsp").forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//
//		// 新增活動
//		String action = req.getParameter("action");
//		// 接收表單資料
//		if ("add".equals(action)) {
//			String paName = req.getParameter("paName");
//			BigDecimal paDiscount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("paDiscount")));
//			String paContent = req.getParameter("paContent");
//			String paStart = req.getParameter("paStart");
//			String paEnd = req.getParameter("paEnd");
//			Boolean paStatus = Boolean.valueOf(req.getParameter("paStatus"));
//
//			// 建立促銷活動物件
//			PromotionActivity promotionActivity = new PromotionActivity();
//			promotionActivity.setPaName(paName);
//			promotionActivity.setPaDiscount(paDiscount);
//			promotionActivity.setPaContent(paContent);
//			promotionActivity.setPaStart(Timestamp.valueOf(paStart + " 00:00:00"));
//			promotionActivity.setPaEnd(Timestamp.valueOf(paEnd + " 23:59:59"));
//			promotionActivity.setPaStatus(paStatus);
//
//			promotionActivityService.addPromotionActivity(paName, paDiscount, paContent, paStart, paEnd, paStatus);
//			
//			resp.sendRedirect(req.getServletContext().getContextPath() + "/promotionactivity/getAll.jsp");
//	
//		}
//	}

}
