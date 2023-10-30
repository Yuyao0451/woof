package com.woof.promotionactivity.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.woof.promotionactivity.entity.PromotionActivity;
import com.woof.util.HibernateUtil;

public class PromotionActivityDAOImpl implements PromotionActivityDAO {

	private SessionFactory factory;

	public PromotionActivityDAOImpl(SessionFactory factory) {
		this.factory = factory;
	}

	public Session getSession() {
		return factory.getCurrentSession();
	}

	
	@Override
	public int insert(PromotionActivity promotionActivity) {
		System.out.println("QQQQQQQQQQQQQ");
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.save(promotionActivity);
			session.getTransaction().commit();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
	        if (session != null && session.isOpen()) {
	            session.close();
	        }
	    }
		return -1;
	
//		return (Integer) getSession().save(promotionActivity);
	}

	@Override
	public int update(PromotionActivity promotionActivity) {
		try {
			getSession().update(promotionActivity);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

//	@Override
//	public int delete(Integer paNo) {
//		PromotionActivity promotionActivity = getSession().get(PromotionActivity.class, paNo);
//		if(promotionActivity != null) {
//			getSession().delete(promotionActivity);
//			return 1;
//		}else {
//			return -1;
//		}
//	}

	@Override
	public PromotionActivity findByPaNo(Integer paNo) {
		return getSession().get(PromotionActivity.class, paNo);
	}

	@Override
	public List<PromotionActivity> getAll() {
		return getSession().createQuery("FROM PromotionActivity", PromotionActivity.class).list();
	}
}
