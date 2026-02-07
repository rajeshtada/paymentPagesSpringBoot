package com.ftk.pg.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.CurrencyConvertor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CurrencyConvertorDaoImpl implements CurrencyConvertorDao {
	private Logger logger = LogManager.getLogger(CurrencyConvertorDaoImpl.class);

	
	@Qualifier(value = "entityManagerFactory")
	private final EntityManagerFactory entityManagerFactory;

//	@Autowired
//	SessionFactory sessionFactory;

	@PersistenceContext
	private EntityManager em;

	private EntityManager getCurrentSession() {
		return em;
	}

	private Class<CurrencyConvertor> getPersistentClass() {
		return CurrencyConvertor.class;
	}

	@Override
	public CurrencyConvertor findByCurrency(String fromCurrency, String toCurrency) {
		try {
			LocalDate lt = LocalDate.now();
			LocalDateTime date = lt.atStartOfDay();

			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<CurrencyConvertor> currencyconvert = builder.createQuery(CurrencyConvertor.class);
			Root<CurrencyConvertor> root = currencyconvert.from(CurrencyConvertor.class);
			currencyconvert.select(root).where(builder.equal(root.get("createdDate"), date),
					builder.equal(root.get("fromCurrency"), fromCurrency),
					builder.equal(root.get("toCurrency"), toCurrency));
			return getCurrentSession().createQuery(currencyconvert).getSingleResult();
		} catch (Exception e) {
			logger.info("No Currency found for fromCurrency= " + fromCurrency + "  toCurrency   =" + toCurrency);
		}
		return null;

	}

}
