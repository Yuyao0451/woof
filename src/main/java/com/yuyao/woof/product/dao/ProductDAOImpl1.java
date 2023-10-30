package com.yuyao.woof.product.dao;//package com.woof.product.dao;
//
//import java.util.List;
//import java.util.Map;
//import java.util.ArrayList;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import com.woof.product.entity.Product1;
//
//public class ProductDAOImpl1 implements ProductDAO1 {
//	private SessionFactory factory;
//
//	public ProductDAOImpl1(SessionFactory factory) {
//		this.factory = factory;
//	}
//
//	private Session getSession() {
//		return factory.getCurrentSession();
//	}
//
//	@Override
//	public int insert(Product1 product) {
//		return (Integer) getSession().save(product);
//	}
//
//	@Override
//	public int update(Product1 product) {
//		try {
//			getSession().update(product);
//			return 1;
//		} catch (Exception e) {
//			return -1;
//		}
//	}
//
//	@Override
//	public int delete(Integer prodNo) {
//		Product1 product = getSession().get(Product1.class, prodNo);
//		if (product != null) {
//			getSession().delete(product);
//			return 1;
//		} else {
//			return -1;
//		}
//	}
//
//	@Override
//	public Product1 findByProdNo(Integer prodNo) {
//		return getSession().get(Product1.class, prodNo);
//	}
//
//	@Override
//	public List<Product1> getAll() {
//		return getSession().createQuery("from Product", Product1.class).list();
//	}
//
//	@Override
//	public List<Product1> getByCompositeQuery(Map<String, String> map) {
//		CriteriaBuilder builder = getSession().getCriteriaBuilder();
//		CriteriaQuery<Product1> criteria = builder.createQuery(Product1.class);
//		Root<Product1> root = criteria.from(Product1.class);
//
//		List<Predicate> predicates = new ArrayList<>();
//
//		for (Map.Entry<String, String> entry : map.entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//
//			if ("prodName".equals(key)) {
//				predicates.add(builder.like(root.get("prodName"), "%" + value + "%"));
//			}
//		}
//
//		criteria.where(predicates.toArray(new Predicate[0]));
//		TypedQuery<Product1> query = getSession().createQuery(criteria);
//		return query.getResultList();
//	}
//
//	@Override
//	public List<Product1> getAll(int currentPage) {
//		int first = (currentPage - 1) * 10;
//		return getSession().createQuery("from Product", Product1.class).setFirstResult(first).setMaxResults(10).list();
//	}
//
//	@Override
//	public long getTotal() {
//		return getSession().createQuery("select count(*) from Product", Long.class).uniqueResult();
//	}
//}