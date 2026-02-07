package com.ftk.pg.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.CardBean;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Repository
public class CardBeanDaoImpl implements CardBeanDao {
	private Logger logger = LogManager.getLogger(CardBeanDaoImpl.class);

	
	@Qualifier(value = "entityManagerFactory")
	private final EntityManagerFactory entityManagerFactory;

//	@Autowired
//	SessionFactory sessionFactory;
	@PersistenceContext
	private EntityManager em;

	private EntityManager getCurrentSession() {
		return em;
	}

	private Class<CardBean> getPersistentClass() {
		return CardBean.class;
	}
	@Override
	public CardBean getBeanDetailbyBeanValue(int value) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<CardBean> query = builder.createQuery(CardBean.class);
			Root<CardBean> root = query.from(CardBean.class);
			query.select(root).where(builder.lessThanOrEqualTo(root.get("fromBin").as(Integer.class), value),
					builder.greaterThanOrEqualTo(root.get("toBin").as(Integer.class), value));
			return getCurrentSession().createQuery(query).getSingleResult();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

}
