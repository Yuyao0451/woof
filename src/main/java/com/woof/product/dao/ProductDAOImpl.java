package com.woof.product.dao;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.woof.product.entity.Product;

public class ProductDAOImpl implements ProductDAO {
	private SessionFactory factory;

	public ProductDAOImpl(SessionFactory factory) {
		this.factory = factory;
	}

	private Session getSession() {
		return factory.getCurrentSession();
	}

	@Override
	public int insert(Product product) {
		return (Integer) getSession().save(product);
	}

	@Override
	public int update(Product product) {
		try {
			getSession().update(product);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public int delete(Integer prodNo) {
		Product product = getSession().get(Product.class, prodNo);
		if (product != null) {
			getSession().delete(product);
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public Product findByProdNo(Integer prodNo) {
		return getSession().get(Product.class, prodNo);
	}

	@Override
	public List<Product> getAll() {
		return getSession().createQuery("from Product", Product.class).list();
	}

	@Override
	public List<Product> getByCompositeQuery(Map<String, String> map) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
		Root<Product> root = criteria.from(Product.class);

		List<Predicate> predicates = new ArrayList<>();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if ("prodName".equals(key)) {
				predicates.add(builder.like(root.get("prodName"), "%" + value + "%"));
			}
		}

		criteria.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Product> query = getSession().createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Product> getAll(int currentPage) {
		int first = (currentPage - 1) * 10;
		return getSession().createQuery("from Product", Product.class).setFirstResult(first).setMaxResults(10).list();
	}

	@Override
	public long getTotal() {
		return getSession().createQuery("select count(*) from Product", Long.class).uniqueResult();
	}
}