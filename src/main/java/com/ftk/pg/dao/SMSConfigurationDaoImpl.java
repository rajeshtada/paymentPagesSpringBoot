package com.ftk.pg.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.SMSConfiguration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SMSConfigurationDaoImpl implements SMSConfigurationDao {

	private Logger logger = LogManager.getLogger(SMSConfigurationDaoImpl.class);

	
	@Qualifier(value = "entityManagerFactory")
	private final EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	private EntityManager em;

	private EntityManager getCurrentSession() {
		return em;
	}
	
	@Override
	public SMSConfiguration findAllSmsConfigByNameAndTriggerName(SMSConfiguration smsConfiguration) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<SMSConfiguration> criteriaQuery = builder.createQuery(SMSConfiguration.class);
		Root<SMSConfiguration> root = criteriaQuery.from(SMSConfiguration.class);
		criteriaQuery.select(root);
		List<Predicate> conditionsList = new ArrayList<Predicate>();
		Predicate merchantName = builder.equal(root.get("mid"), smsConfiguration.getMid());
		Predicate trigerCode = builder.equal(root.get("triggerCode"), smsConfiguration.getTriggerCode());
		conditionsList.add(merchantName);
		conditionsList.add(trigerCode);
		if (smsConfiguration != null) {
			if (smsConfiguration.getIsMerchant()  && !smsConfiguration.getIsCustomer()) {
				logger.info("Inside IsMerchant");
				Predicate predicate1 = builder.equal(root.get("isMerchant"), smsConfiguration.getIsMerchant());
				Predicate predicate2 = builder.equal(root.get("isCustomer"), false);
				conditionsList.add(predicate1);
				conditionsList.add(predicate2);
			}
			if (smsConfiguration.getIsCustomer() && !smsConfiguration.getIsMerchant()) {
				logger.info("Inside IsCustomer");
				Predicate predicate1 = builder.equal(root.get("isCustomer"), smsConfiguration.getIsCustomer());
				Predicate predicate2 = builder.equal(root.get("isMerchant"), false);
				conditionsList.add(predicate1);
				conditionsList.add(predicate2);
			}
			if (smsConfiguration.getIsCustomer()&&smsConfiguration.getIsMerchant()) {
				logger.info("Inside isCus&Mer ");
				Predicate predicate1 = builder.equal(root.get("isMerchant"), smsConfiguration.getIsMerchant());
				Predicate predicate2 = builder.equal(root.get("isCustomer"), smsConfiguration.getIsCustomer());
				conditionsList.add(predicate1);
				conditionsList.add(predicate2);
			}

		}
		try {
			criteriaQuery.where(conditionsList.toArray(new Predicate[] {}));
			SMSConfiguration result = getCurrentSession().createQuery(criteriaQuery).getSingleResult();
			logger.info("Result Is Fetched: ");
			return result;
		} catch (Exception e) {
			logger.info("Error While Fetching SMS Config: " + e.getMessage());
			return null;
		}

	}
	
	@Override
	public List<SMSConfiguration> findAllSmsConfigByName(SMSConfiguration smsConfiguration) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<SMSConfiguration> criteriaQuery = builder.createQuery(SMSConfiguration.class);
		Root<SMSConfiguration> root = criteriaQuery.from(SMSConfiguration.class);
		criteriaQuery.select(root);
		criteriaQuery.where(builder.equal(root.get("mid"), smsConfiguration.getMid()));
		try {
			List<SMSConfiguration> resultList = getCurrentSession().createQuery(criteriaQuery).getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error While Fetching SMS Config List Based On " + smsConfiguration.getMid());
			return null;
		}
	}
	
}
