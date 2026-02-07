package com.ftk.pg.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.Merchant;
import com.ftk.pg.modal.MerchantCommision;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MerchantCommisionDaoImpl implements MerchantCommisionDao {
	private Logger logger = LogManager.getLogger(MerchantCommisionDaoImpl.class);
//	@Autowired
//	SessionFactory sessionFactory;
//
//	protected Session getCurrentSession() {
//		return sessionFactory.getCurrentSession();
//	}
//	
//	private Class<MerchantCommision> getPersistentClass() {
//		return MerchantCommision.class;
//	}


	@Qualifier(value = "entityManagerFactory")
	private final  EntityManagerFactory entityManagerFactory;

//	@Autowired
//	SessionFactory sessionFactory;

	@PersistenceContext
	private EntityManager em;

	private EntityManager getCurrentSession() {
		return em;
	}

	private Class<MerchantCommision> getPersistentClass() {
		return MerchantCommision.class;
	}

	@Override
	public MerchantCommision findByMidAndPayMode(MerchantCommision commision) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantCommision> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantCommision> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> conditionsList = new ArrayList<Predicate>();
		Predicate predicate = builder.equal(root.get("merchantId"), commision.getMerchantId());
		conditionsList.add(predicate);
		Predicate predicate1 = builder.equal(root.get("paymentMode"), commision.getPaymentMode());
		conditionsList.add(predicate1);
		Predicate predicate2 = builder.equal(root.get("productType"), commision.getProductType());
		conditionsList.add(predicate2);
		Predicate predicate3 = builder.equal(root.get("isDefault"), false);
		conditionsList.add(predicate3);
		if (commision.getCommisionvalue() != null) {
			Predicate predicate4 = builder.lessThanOrEqualTo(root.<BigDecimal>get("fromAmount"),
					commision.getCommisionvalue());
			Predicate predicate5 = builder.greaterThanOrEqualTo(root.<BigDecimal>get("toAmount"),
					commision.getCommisionvalue());
			conditionsList.add(predicate4);
			conditionsList.add(predicate5);
		}
		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			MerchantCommision merchantCommision = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchantCommision;
		} catch (Exception e) {
			logger.info("Error While Fetching findDefaultCommison Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public MerchantCommision findByMidAndBank(MerchantCommision commision) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantCommision> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantCommision> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> conditionsList = new ArrayList<Predicate>();
		Predicate predicate = builder.equal(root.get("merchantId"), commision.getMerchantId());
		conditionsList.add(predicate);
		Predicate predicate1 = builder.equal(root.get("paymentMode"), commision.getPaymentMode());
		conditionsList.add(predicate1);
		Predicate predicate2 = builder.equal(root.get("productType"), commision.getProductType());
		conditionsList.add(predicate2);
		Predicate predicate3 = builder.equal(root.get("isDefault"), false);
		conditionsList.add(predicate3);
		if (commision.getCommisionvalue() != null) {
			/*
			 * Predicate predicate4 =
			 * builder.lessThanOrEqualTo(root.<BigDecimal>get("fromAmount"),
			 * commision.getCommisionvalue()); Predicate predicate5 =
			 * builder.greaterThanOrEqualTo(root.<BigDecimal>get("toAmount"),
			 * commision.getCommisionvalue()); conditionsList.add(predicate4);
			 * conditionsList.add(predicate5);
			 */

			List<Predicate> amtPredicates = new ArrayList<Predicate>();
			Predicate predicate4 = builder.lessThanOrEqualTo(root.<BigDecimal>get("fromAmount"),
					commision.getCommisionvalue());
			Predicate predicate5 = builder.greaterThanOrEqualTo(root.<BigDecimal>get("toAmount"),
					commision.getCommisionvalue());

			amtPredicates.add(predicate4);
			amtPredicates.add(predicate5);

			Predicate[] aPredicates = new Predicate[2];

			Predicate andPredicate = builder.and(amtPredicates.toArray(aPredicates));

			Predicate orPredicate = builder.or(builder.isNull(root.get("fromAmount")), andPredicate);
			conditionsList.add(orPredicate);

		}
		Predicate predicate6 = builder.equal(root.get("subType"), commision.getSubType());
		conditionsList.add(predicate6);
		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			MerchantCommision merchantCommision = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchantCommision;
		} catch (Exception e) {
			logger.info("Error While Fetching findByMidAndBank Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public MerchantCommision findDefaultCommison(MerchantCommision commision) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantCommision> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantCommision> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> conditionsList = new ArrayList<Predicate>();
		Predicate predicate = builder.equal(root.get("merchantId"), commision.getMerchantId());
		conditionsList.add(predicate);
		Predicate predicate1 = builder.equal(root.get("paymentMode"), commision.getPaymentMode());
		conditionsList.add(predicate1);
		Predicate predicate2 = builder.equal(root.get("productType"), commision.getProductType());
		conditionsList.add(predicate2);
		Predicate predicate3 = builder.equal(root.get("isDefault"), true);
		conditionsList.add(predicate3);
		if (commision.getCommisionvalue() != null) {
			Predicate predicate4 = builder.lessThanOrEqualTo(root.<BigDecimal>get("fromAmount"),
					commision.getCommisionvalue());
			Predicate predicate5 = builder.greaterThanOrEqualTo(root.<BigDecimal>get("toAmount"),
					commision.getCommisionvalue());
			conditionsList.add(predicate4);
			conditionsList.add(predicate5);
		}

		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			MerchantCommision merchantCommision = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchantCommision;
		} catch (Exception e) {
			logger.info("Error While Fetching findDefaultCommison Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public String findMerchantPaymentModeByName(MerchantCommision commision) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<String> criteria = builder.createQuery(String.class);
		Root<Merchant> root = criteria.from(Merchant.class);
		criteria.select(root.get("enabledPaymentModes"));
		criteria.where(builder.equal(root.get("merchantId"), commision.getMerchantId()));
		try {
			String merchant = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchant;
		} catch (Exception e) {
			System.out
					.println("Error While Fetching findMerchantPaymentModeByName Merchant Commission For Merchant Id: "
							+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
							+ e.getMessage());
		}
		return null;
	}

	@Override
	public List<MerchantCommision> findMerchatCommisions(MerchantCommision commision) {

		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantCommision> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantCommision> root = criteria.from(getPersistentClass());
		criteria.select(root);
		List<Predicate> conditionsList = new ArrayList<Predicate>();
		logger.info("Inside comission ::- " + commision.isDefault());

		Predicate True = builder.equal(root.get("isDefault"), commision.isDefault());
		conditionsList.add(True);

		if (commision.getMerchantId() != null) {
			logger.info("Inside::- " + commision.getMerchantId());
			Predicate predicate = builder.equal(root.get("merchantId"), commision.getMerchantId());
			conditionsList.add(predicate);
		}
		if (commision.getProductType() != null && !commision.getProductType().trim().equals("")) {
			logger.info("Inside::- " + commision.getProductType());
			Predicate predicate = builder.equal(root.get("productType"), commision.getProductType());
			conditionsList.add(predicate);
		}
		if (commision.getPaymentMode() != null && !commision.getPaymentMode().trim().equals("")) {
			logger.info("Inside::- " + commision.getPaymentMode());
			Predicate predicate = builder.equal(root.get("paymentMode"), commision.getPaymentMode());
			conditionsList.add(predicate);
		}
		if (commision.getCommisionType() != null && !commision.getCommisionType().trim().equals("")) {
			logger.info("Inside::- " + commision.getCommisionType());
			Predicate predicate = builder.equal(root.get("commisionType"), commision.getCommisionType());
			conditionsList.add(predicate);
		}
		if (commision.getChargeType() != null && !commision.getChargeType().trim().equals("")) {
			logger.info("Inside::- " + commision.getChargeType());
			Predicate predicate = builder.equal(root.get("chargeType"), commision.getChargeType());
			conditionsList.add(predicate);
		}
		if (commision.getSubType() != null && !commision.getSubType().trim().equals("")) {
			logger.info("Inside::- " + commision.getSubType());
			Predicate predicate = builder.equal(root.get("subType"), commision.getSubType());
			conditionsList.add(predicate);
		}
		if (commision.getCommisionvalue() != null) {
			Predicate predicate1 = builder.lessThanOrEqualTo(root.<BigDecimal>get("fromAmount"),
					commision.getCommisionvalue());
			Predicate predicate2 = builder.greaterThanOrEqualTo(root.<BigDecimal>get("toAmount"),
					commision.getCommisionvalue());
			conditionsList.add(predicate1);
			conditionsList.add(predicate2);
		}

		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			List<MerchantCommision> list = (List<MerchantCommision>) getCurrentSession().createQuery(criteria)
					.getResultList();
			// logger.info("Commission Result Is Fetched: " + list);
			return list;
		} catch (Exception e) {
			logger.info("Error While Fetching findMerchatCommisions Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public int deleteMerchantCommission(MerchantCommision commision) {
		int executeUpdate = 0;
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaDelete<MerchantCommision> criteria = builder.createCriteriaDelete(MerchantCommision.class);
		Root<MerchantCommision> root = criteria.from(MerchantCommision.class);
		criteria.where(builder.equal(root.get("merchantId"), commision.getMerchantId()));
		try {
			executeUpdate = getCurrentSession().createQuery(criteria).executeUpdate();
			logger.info("Merchant Commission Deleted");
			return executeUpdate;
		} catch (Exception e) {
			logger.info("Error While Fetching deleteMerchantCommission Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return executeUpdate;
	}

	@Override
	public List<MerchantCommision> bankWiseComissionList(MerchantCommision commision) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantCommision> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantCommision> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> conditionsList = new ArrayList<Predicate>();
		Predicate predicate = builder.equal(root.get("merchantId"), commision.getMerchantId());
		conditionsList.add(predicate);
		Predicate predicate1 = builder.equal(root.get("paymentMode"), commision.getPaymentMode());
		conditionsList.add(predicate1);
		Predicate predicate2 = builder.equal(root.get("productType"), commision.getProductType());
		conditionsList.add(predicate2);
		Predicate predicate3 = builder.equal(root.get("isDefault"), false);
		conditionsList.add(predicate3);
		if (commision.getCommisionvalue() != null) {
			List<Predicate> amtPredicates = new ArrayList<Predicate>();
			Predicate predicate4 = builder.lessThanOrEqualTo(root.<BigDecimal>get("fromAmount"),
					commision.getCommisionvalue());
			Predicate predicate5 = builder.greaterThanOrEqualTo(root.<BigDecimal>get("toAmount"),
					commision.getCommisionvalue());

			amtPredicates.add(predicate4);
			amtPredicates.add(predicate5);

			Predicate[] aPredicates = new Predicate[2];

			Predicate andPredicate = builder.and(amtPredicates.toArray(aPredicates));

			Predicate orPredicate = builder.or(builder.isNull(root.get("fromAmount")), andPredicate);
			conditionsList.add(orPredicate);

		}
		Predicate predicate6 = builder.isNotNull(root.get("subType"));
		conditionsList.add(predicate6);
		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			List<MerchantCommision> merchantCommision = getCurrentSession().createQuery(criteria).getResultList();
			return merchantCommision;
		} catch (Exception e) {
			logger.info("Error While Fetching bankWiseComissionList Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public List<MerchantCommision> findByMids(List<Long> mids) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<MerchantCommision> query = builder.createQuery(MerchantCommision.class);
			Root<MerchantCommision> root = query.from(MerchantCommision.class);
			query.select(root).where(root.get("merchantId").in(mids));
			return getCurrentSession().createQuery(query).getResultList();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public void deleteByIds(List<Long> ids) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaDelete<MerchantCommision> query = builder.createCriteriaDelete(MerchantCommision.class);
			Root<MerchantCommision> root = query.from(MerchantCommision.class);
			query.where(root.get("id").in(ids));

			getCurrentSession().createQuery(query).executeUpdate();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	@Override
	public List<MerchantCommision> searchMerchatCommision(MerchantCommision commision) {

		if (commision.getMerchantId() == null || commision.getMerchantId() <= 0) {
			return null;
		}

		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantCommision> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantCommision> root = criteria.from(getPersistentClass());
		criteria.select(root);
		List<Predicate> conditionsList = new ArrayList<Predicate>();
		logger.info("Inside comission ::- " + commision.isDefault());

		Predicate isDefaultCondition = builder.equal(root.get("isDefault"), commision.isDefault());
		conditionsList.add(isDefaultCondition);

		Predicate merchantIdPredicate = builder.equal(root.get("merchantId"), commision.getMerchantId());
		conditionsList.add(merchantIdPredicate);

		if (commision.getProductType() != null && !commision.getProductType().trim().equals("")) {
			logger.info("Inside::- " + commision.getProductType());
			Predicate predicate = builder.equal(root.get("productType"), commision.getProductType());
			conditionsList.add(predicate);
		}
		if (commision.getPaymentMode() != null && !commision.getPaymentMode().trim().equals("")) {
			logger.info("Inside::- " + commision.getPaymentMode());
			Predicate predicate = builder.equal(root.get("paymentMode"), commision.getPaymentMode());
			conditionsList.add(predicate);
		}
		if (commision.getSubType() != null && !commision.getSubType().trim().equals("")) {
			logger.info("Inside::- " + commision.getSubType());
			Predicate predicate = builder.equal(root.get("subType"), commision.getSubType());
			conditionsList.add(predicate);
		}
		if (commision.getCommisionvalue() != null) {
			List<Predicate> amtPredicates = new ArrayList<Predicate>();
			Predicate predicate4 = builder.lessThanOrEqualTo(root.<BigDecimal>get("fromAmount"),
					commision.getCommisionvalue());
			Predicate predicate5 = builder.greaterThanOrEqualTo(root.<BigDecimal>get("toAmount"),
					commision.getCommisionvalue());

			amtPredicates.add(predicate4);
			amtPredicates.add(predicate5);

			Predicate[] aPredicates = new Predicate[2];

			Predicate andPredicate = builder.and(amtPredicates.toArray(aPredicates));

			Predicate orPredicate = builder.or(builder.isNull(root.get("fromAmount")), andPredicate);
			conditionsList.add(orPredicate);
		}

		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			List<MerchantCommision> list = (List<MerchantCommision>) getCurrentSession().createQuery(criteria)
					.getResultList();
			// logger.info("Commission Result Is Fetched: " + list);
			return list;
		} catch (Exception e) {
			logger.info("Error While Fetching findMerchatCommisions Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public void persistInBatch(List<MerchantCommision> list) {
		try {
			list.forEach(mComm -> {
				getCurrentSession().persist(mComm);
			});
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	@Override
	public List<MerchantCommision> searchMerchatCommisionByContype(MerchantCommision commision, String conSubType) {

		if (commision.getMerchantId() == null || commision.getMerchantId() <= 0) {
			return null;
		}

		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantCommision> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantCommision> root = criteria.from(getPersistentClass());
		criteria.select(root);
		List<Predicate> conditionsList = new ArrayList<Predicate>();
		logger.info("Inside comission ::- " + commision.isDefault());

		Predicate isDefaultCondition = builder.equal(root.get("isDefault"), commision.isDefault());
		conditionsList.add(isDefaultCondition);

		Predicate merchantIdPredicate = builder.equal(root.get("merchantId"), commision.getMerchantId());
		conditionsList.add(merchantIdPredicate);

		if (commision.getProductType() != null && !commision.getProductType().trim().equals("")) {
			logger.info("Inside::- " + commision.getProductType());
			Predicate predicate = builder.equal(root.get("productType"), commision.getProductType());
			conditionsList.add(predicate);
		}
		if (commision.getPaymentMode() != null && !commision.getPaymentMode().trim().equals("")) {
			logger.info("Inside::- " + commision.getPaymentMode());
			Predicate predicate = builder.equal(root.get("paymentMode"), commision.getPaymentMode());
			conditionsList.add(predicate);
		}
		if (commision.getSubType() != null && !commision.getSubType().trim().equals("")) {
			logger.info("Inside::- " + commision.getSubType());
			Predicate predicate = builder.equal(root.get("subType"), conSubType);
			conditionsList.add(predicate);
		}
		if (commision.getCommisionvalue() != null) {
			List<Predicate> amtPredicates = new ArrayList<Predicate>();
			Predicate predicate4 = builder.lessThanOrEqualTo(root.<BigDecimal>get("fromAmount"),
					commision.getCommisionvalue());
			Predicate predicate5 = builder.greaterThanOrEqualTo(root.<BigDecimal>get("toAmount"),
					commision.getCommisionvalue());

			amtPredicates.add(predicate4);
			amtPredicates.add(predicate5);

			Predicate[] aPredicates = new Predicate[2];

			Predicate andPredicate = builder.and(amtPredicates.toArray(aPredicates));

			Predicate orPredicate = builder.or(builder.isNull(root.get("fromAmount")), andPredicate);
			conditionsList.add(orPredicate);
		}

		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			List<MerchantCommision> list = (List<MerchantCommision>) getCurrentSession().createQuery(criteria)
					.getResultList();
			// logger.info("Commission Result Is Fetched: " + list);
			return list;
		} catch (Exception e) {
			logger.info("Error While Fetching findMerchatCommisions Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}
	
	@Override
	public MerchantCommision searchMerchantCommisionGreaterThanSlabSingleResult(MerchantCommision commision) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantCommision> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantCommision> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> conditionsList = new ArrayList<Predicate>();
		Predicate predicate = builder.equal(root.get("merchantId"), commision.getMerchantId());
		conditionsList.add(predicate);
		Predicate predicate1 = builder.equal(root.get("paymentMode"), commision.getPaymentMode());
		conditionsList.add(predicate1);
		Predicate predicate2 = builder.equal(root.get("productType"), commision.getProductType());
		conditionsList.add(predicate2);
		Predicate predicate3 = builder.equal(root.get("isDefault"), commision.isDefault());
		conditionsList.add(predicate3);
		if (commision.getSubType() != null ) {
		Predicate predicate6 = builder.equal(root.get("subType"), commision.getSubType());
		conditionsList.add(predicate6);
		}
		Predicate lessThanOrEqualTo = builder.lessThanOrEqualTo(root.get("toAmount"), commision.getCommisionvalue());
		conditionsList.add(lessThanOrEqualTo);

		try {
			criteria.where(conditionsList.toArray(new Predicate[] {}));
			criteria.orderBy(builder.desc(root.get("toAmount")));
			MerchantCommision merchantCommision = getCurrentSession().createQuery(criteria).setMaxResults(1).getSingleResult();
			return merchantCommision;
		} catch (Exception e) {
			logger.info("Error While Fetching findByMidAndBank Merchant Commission For Merchant Id: "
					+ commision.getMerchantId() + " & PayMode: " + commision.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}
	
	

}