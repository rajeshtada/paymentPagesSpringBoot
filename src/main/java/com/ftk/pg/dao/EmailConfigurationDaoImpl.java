package com.ftk.pg.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.EmailConfiguration;

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
public class EmailConfigurationDaoImpl implements EmailConfigurationDao {

	
	private Logger logger = LogManager.getLogger(EmailConfigurationDaoImpl.class);

	
	@Qualifier(value = "entityManagerFactory")
	private final EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	private EntityManager em;

	private EntityManager getCurrentSession() {
		return em;
	}
	
	
	@Override
	public EmailConfiguration findEmailConfigByMid(EmailConfiguration emailConfiguration) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<EmailConfiguration> criteria = builder.createQuery(EmailConfiguration.class);
		Root<EmailConfiguration> root = criteria.from(EmailConfiguration.class);
		criteria.select(root);
		List<Predicate> conditionsList = new ArrayList<Predicate>();
		Predicate mNam = builder.equal(root.get("mId"), emailConfiguration.getmId());
		conditionsList.add(mNam);
		if (emailConfiguration.isCustomer()) {
			Predicate isDefault = builder.equal(root.get("isCustomer"), true);
			conditionsList.add(isDefault);
		}
		if (emailConfiguration.isMerchant()) {
			Predicate isDefault = builder.equal(root.get("isMerchant"), true);
			conditionsList.add(isDefault);
		}
		try {

			criteria.where(conditionsList.toArray(new Predicate[] {}));
			EmailConfiguration list = getCurrentSession().createQuery(criteria).getSingleResult();
			logger.info("Merchant's EmailConfiguration  Is Fetched: ");
			return list;
		} catch (Exception e) {
			logger.info("Unable to fetch  Merchant's EmailConfiguration == " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<EmailConfiguration> findConfigByMid(EmailConfiguration configuration) {
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<EmailConfiguration> criteriaQuery = criteriaBuilder.createQuery(EmailConfiguration.class);
		Root<EmailConfiguration> root = criteriaQuery.from(EmailConfiguration.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("mId"), configuration.getmId()));
		try {
			List<EmailConfiguration> resultList = getCurrentSession().createQuery(criteriaQuery).getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Error While Fetching Email Configuration: " + e);
			return null;
		}
	}

	@Override
	public EmailConfiguration findEmailConfigByTriggerCode(EmailConfiguration emailConfiguration) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<EmailConfiguration> criteria = builder.createQuery(EmailConfiguration.class);
		Root<EmailConfiguration> root = criteria.from(EmailConfiguration.class);
		criteria.select(root);
		List<Predicate> conditionsList = new ArrayList<Predicate>();
		Predicate merchantName = builder.equal(root.get("mId"), emailConfiguration.getmId());
		Predicate trigerCode = builder.equal(root.get("triggerCode"), emailConfiguration.getTriggerCode());
		conditionsList.add(merchantName);
		conditionsList.add(trigerCode);
		if (emailConfiguration.isMerchant()) {
			logger.info("Inside IsMerchant");
			Predicate predicate1 = builder.equal(root.get("isMerchant"), true);
			Predicate predicate2 = builder.equal(root.get("isCustomer"), false);
			conditionsList.add(predicate1);
			conditionsList.add(predicate2);
		}
		if (emailConfiguration.isCustomer()) {
			logger.info("Inside IsCustomer");
			Predicate predicate1 = builder.equal(root.get("isCustomer"), true);
			Predicate predicate2 = builder.equal(root.get("isMerchant"), false);
			conditionsList.add(predicate1);
			conditionsList.add(predicate2);
		}
		if (emailConfiguration.isCustomer() && emailConfiguration.isMerchant()) {
			logger.info("Inside isCus&Mer ");
			Predicate predicate1 = builder.equal(root.get("isMerchant"), true);
			Predicate predicate2 = builder.equal(root.get("isCustomer"), true);
			conditionsList.add(predicate1);
			conditionsList.add(predicate2);
		}

		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			EmailConfiguration list = getCurrentSession().createQuery(criteria).getSingleResult();
			logger.info("Result Is Fetched: ");
			return list;
		} catch (Exception e) {
			logger.info("Unable to fetch list of merchant's EmailConfiguration:  " + e.getMessage());
			return null;
		}
	}
	
}
