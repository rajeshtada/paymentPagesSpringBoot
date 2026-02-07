package com.ftk.pg.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.ConvenienceCharges;

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
public class ConvenienceChargesDaoImpl implements ConvenienceChargesDao {
	private Logger logger = LogManager.getLogger(ConvenienceChargesDaoImpl.class);

	
	@Qualifier(value = "entityManagerFactory")
	private final EntityManagerFactory entityManagerFactory;

	@PersistenceContext
	private EntityManager em;

	private EntityManager getCurrentSession() {
		return em;
	}

	private Class<ConvenienceCharges> getPersistentClass() {
		return ConvenienceCharges.class;
	}

//	@Autowired
//	SessionFactory sessionFactory;

//	  @Autowired
//	    private SessionFactory sessionFactory;
//	    
//	    private Session getCurrentSession() {
//	        return sessionFactory.getCurrentSession();
//	    }

	@Override
	public List<ConvenienceCharges> getConvenienceChargesByMidAndStatus(Long mid, int status) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> query = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = query.from(ConvenienceCharges.class);
			query.select(root).where(builder.equal(root.get("mid"), mid), builder.equal(root.get("status"), status));
			return getCurrentSession().createQuery(query).getResultList();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public ConvenienceCharges getChargesByMidPaymentModeAndCardType(Long mid, String paymentMode, String cardType) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> query = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = query.from(ConvenienceCharges.class);
			query.select(root);
			if (cardType == null || cardType.trim().equals("")) {
				logger.info("Inside card type null..");
				query.where(builder.equal(root.get("mid"), mid), builder.equal(root.get("paymentMode"), paymentMode),
						builder.equal(root.get("status"), 1));
			} else {
				logger.info("Inside card type is not null..");
				query.where(builder.equal(root.get("mid"), mid), builder.equal(root.get("paymentMode"), paymentMode),
						builder.equal(root.get("cardType"), cardType), builder.equal(root.get("status"), 1));
			}

			return getCurrentSession().createQuery(query).getSingleResult();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

//	@Override
//	public void saveInBulk(List<ConvenienceCharges> convenienceChargesList) {
//		try {
//			convenienceChargesList.forEach(charges -> {
////				sessionFactory.getCurrentSession().persist(charges);
//				getCurrentSession().saveOrUpdate(charges);
//			});
//		} catch (Exception e) {
//			new GlobalExceptionHandler().customException(e);
//		}
//	}

	@Override
	public List<ConvenienceCharges> getConvenienceChargesByMidsAndStatus(List<Long> mids, int status) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> query = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = query.from(ConvenienceCharges.class);
			query.select(root).where(root.get("mid").in(mids), builder.equal(root.get("status"), status));
			return getCurrentSession().createQuery(query).getResultList();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public List<ConvenienceCharges> getByMid(Long mid) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> query = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = query.from(ConvenienceCharges.class);
			query.select(root).where(builder.equal(root.get("mid"), mid));
			return getCurrentSession().createQuery(query).getResultList();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public List<ConvenienceCharges> findConvenienceCharge(ConvenienceCharges cCharges) {
		List<ConvenienceCharges> convenienceCharge = new ArrayList<>();
		try {

			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> criteria = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = criteria.from(ConvenienceCharges.class);
			criteria.select(root);

//		criteria.select(root).where(builder.equal(root.get("mid"), cCharges.getMid()));
//		convenienceCharge =  sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

			List<Predicate> conditionsList = new ArrayList<Predicate>();
//		
			Predicate status = builder.equal(root.get("status"), cCharges.getStatus());
			conditionsList.add(status);
//		
			if (cCharges.getMid() != null) {
				Predicate mid = builder.equal(root.get("mid"), cCharges.getMid());
				conditionsList.add(mid);
			}
			if (cCharges.getPaymentMode() != null && !cCharges.getPaymentMode().equals("")) {
				Predicate paymentMode = builder.equal(root.get("paymentMode"), cCharges.getPaymentMode());
				conditionsList.add(paymentMode);
			}
			if (cCharges.getCardType() != null && !cCharges.getCardType().equals("")) {
				Predicate cardType = builder.equal(root.get("cardType"), cCharges.getCardType());
				conditionsList.add(cardType);
			}
			if (cCharges.getBankId() != null) {
				Predicate bankId = builder.equal(root.get("bankId"), cCharges.getBankId());
				conditionsList.add(bankId);
			}

			criteria.where(conditionsList.toArray(new Predicate[] {}));
			convenienceCharge = getCurrentSession().createQuery(criteria).getResultList();
		} catch (Exception e) {
			logger.info("Error While Fetching ConvenienceCharges For Merchant Id: " + cCharges.getMid() + " & PayMode: "
					+ cCharges.getPaymentMode() + "  Reason: " + e.getMessage());
		}
		return convenienceCharge;

	}

	@Override
	public List<ConvenienceCharges> findConvenienceChargeOtherPayment(ConvenienceCharges cCharges) {
		List<ConvenienceCharges> convenienceCharge = new ArrayList<>();
		try {

			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> criteria = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = criteria.from(ConvenienceCharges.class);
			criteria.select(root);

//		criteria.select(root).where(builder.equal(root.get("mid"), cCharges.getMid()));
//		convenienceCharge =  sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

			List<Predicate> conditionsList = new ArrayList<Predicate>();
//		
			Predicate status = builder.equal(root.get("status"), 1);
			conditionsList.add(status);
//		
			if (cCharges.getMid() != null) {
				Predicate mid = builder.equal(root.get("mid"), cCharges.getMid());
				conditionsList.add(mid);
			}
			if (cCharges.getPaymentMode() != null && !cCharges.getPaymentMode().equals("")) {
				Predicate paymentMode = builder.equal(root.get("paymentMode"), cCharges.getPaymentMode());
				conditionsList.add(paymentMode);
			}
			if (cCharges.getCardType() != null && !cCharges.getCardType().equals("")) {
				Predicate cardType = builder.equal(root.get("cardType"), cCharges.getCardType());
				conditionsList.add(cardType);
			}
			if (cCharges.getBankId() != null) {
				Predicate bankId = builder.equal(root.get("bankId"), cCharges.getBankId());
				conditionsList.add(bankId);
			}

			criteria.where(conditionsList.toArray(new Predicate[] {}));
			convenienceCharge = getCurrentSession().createQuery(criteria).getResultList();
		} catch (Exception e) {
			logger.info("Error While Fetching ConvenienceCharges For Merchant Id: " + cCharges.getMid() + " & PayMode: "
					+ cCharges.getPaymentMode() + "  Reason: " + e.getMessage());
		}
		return convenienceCharge;

	}

	@Override
	public List<ConvenienceCharges> getChargesByMidPaymentModeAndCardType(ConvenienceCharges convenienceCharge) {
		List<ConvenienceCharges> cCharge = new ArrayList<>();
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> query = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = query.from(ConvenienceCharges.class);
			query.select(root);
			List<Predicate> conditionsList = new ArrayList<Predicate>();
			Predicate status = builder.equal(root.get("status"), 1);
			conditionsList.add(status);
			Predicate payment = builder.equal(root.get("paymentMode"), convenienceCharge.getPaymentMode());
			conditionsList.add(payment);
			Predicate mid = builder.equal(root.get("mid"), convenienceCharge.getMid());
			conditionsList.add(mid);
			if ((convenienceCharge.getCardType() == null || convenienceCharge.getCardType().trim().equals(""))
					&& (convenienceCharge.getBankId() != null)) {
				logger.info("Inside card type null..");
				Predicate bank = builder.equal(root.get("bankId"), convenienceCharge.getBankId());
				conditionsList.add(bank);
			} else {
				logger.info("Inside card type is not null..");
				Predicate cardtype = builder.equal(root.get("cardType"), convenienceCharge.getCardType());
				conditionsList.add(cardtype);
			}

			query.where(conditionsList.toArray(new Predicate[] {}));
			cCharge = getCurrentSession().createQuery(query).getResultList();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return cCharge;
	}

	@Override
	public List<ConvenienceCharges> getChargesByMidAndBankId(ConvenienceCharges cCharges) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> query = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = query.from(ConvenienceCharges.class);
			query.select(root);
			if ((cCharges.getCardType() == null || cCharges.getCardType().trim().equals(""))
					&& (cCharges.getBankId() != null)) {
				logger.info("Inside card type null..");
				query.where(builder.equal(root.get("mid"), cCharges.getMid()),
						builder.equal(root.get("bankId"), cCharges.getBankId()),
						builder.equal(root.get("paymentMode"), cCharges.getPaymentMode()),
						builder.equal(root.get("status"), 1));
			} else {
				logger.info("Inside card type is not null..");
				query.where(builder.equal(root.get("mid"), cCharges.getMid()),
						builder.equal(root.get("paymentMode"), cCharges.getPaymentMode()),
						builder.equal(root.get("cardType"), cCharges.getCardType()),
						builder.equal(root.get("status"), 1));
			}

			return getCurrentSession().createQuery(query).getResultList();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public List<ConvenienceCharges> getChargesByMidPaymentModeAndCardTypeAll(ConvenienceCharges cCharges) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<ConvenienceCharges> query = builder.createQuery(ConvenienceCharges.class);
			Root<ConvenienceCharges> root = query.from(ConvenienceCharges.class);
			query.select(root);
			if (cCharges.getPaymentMode().equalsIgnoreCase("NB")) {
				logger.info("Inside card type null..");
				logger.info("Inside card type is not null..");
				query.where(builder.equal(root.get("mid"), cCharges.getMid()),
						builder.equal(root.get("paymentMode"), cCharges.getPaymentMode()),
						builder.equal(root.get("cardType"), "ALL"), builder.equal(root.get("status"), 1));
			} else if (cCharges.getPaymentMode().equalsIgnoreCase("CC")
					|| cCharges.getPaymentMode().equalsIgnoreCase("DC")) {
				logger.info("Inside card type is not null..");
				query.where(builder.equal(root.get("mid"), cCharges.getMid()),
						builder.equal(root.get("paymentMode"), cCharges.getPaymentMode()),
						builder.equal(root.get("cardType"), "ALL"), builder.equal(root.get("status"), 1));
			} else {
				query.where(builder.equal(root.get("mid"), cCharges.getMid()),
						builder.equal(root.get("paymentMode"), cCharges.getPaymentMode()),
						builder.equal(root.get("status"), 1));
			}

			return getCurrentSession().createQuery(query).getResultList();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public void saveInBulk(List<ConvenienceCharges> convenienceChargesList) {
		// TODO Auto-generated method stub

	}

}
