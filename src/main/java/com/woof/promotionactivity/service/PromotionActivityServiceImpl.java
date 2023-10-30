package com.woof.promotionactivity.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import com.woof.promotionactivity.dao.PromotionActivityDAO;
import com.woof.promotionactivity.dao.PromotionActivityDAOImpl;
import com.woof.promotionactivity.entity.PromotionActivity;
import com.woof.util.HibernateUtil;

public class PromotionActivityServiceImpl implements PromotionActivityService {

	private PromotionActivityDAO dao;

	public PromotionActivityServiceImpl() {
		dao = new PromotionActivityDAOImpl(HibernateUtil.getSessionFactory());
	}

//	@Override
//	public PromotionActivity addPromotionActivity(PromotionActivity promotionActivity) {
//		
//		dao.insert(promotionActivity);
//		
//		return null;
//	}

	@Override
	public PromotionActivity addPromotionActivity(String paName, BigDecimal paDiscount, String paContent, Timestamp paStart, Timestamp paEnd, Boolean paStatus) {
		
		PromotionActivity promotionActivity = new PromotionActivity();
		promotionActivity.setPaName(paName);
		promotionActivity.setPaDiscount(paDiscount);
		promotionActivity.setPaContent(paContent);
		promotionActivity.setPaStart(paStart);
		promotionActivity.setPaEnd(paEnd);
		promotionActivity.setPaStatus(paStatus);
		
		dao.insert(promotionActivity);
		
		return promotionActivity;
	}

	@Override
	public PromotionActivity updatePromotionActivity(PromotionActivity promotionActivity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();
		int result = dao.update(promotionActivity);
		if (result == 1) {

			session.getTransaction().commit();
			return promotionActivity;
		}
		session.getTransaction().rollback();

		return null;
	}

//	@Override
//	public void deletePromotionActivity(Integer paNo) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public PromotionActivity findPromotionActivityByPaNo(Integer paNo) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		return null;
	}

	@Override
	public List<PromotionActivity> getAllPromotionActivity() {

		// filter已經有寫所以這邊可以省略
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

//		session.beginTransaction();
		List<PromotionActivity> promotionActivityList = dao.getAll();
//		session.getTransaction().commit();

		// TODO Auto-generated method stub
		return promotionActivityList;

	}

}
