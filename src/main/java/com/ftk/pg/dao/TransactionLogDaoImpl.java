package com.ftk.pg.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.FilterVo;
import com.ftk.pg.modal.TransactionLog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class TransactionLogDaoImpl implements TransactionLogDao {

	private Logger logger = LogManager.getLogger(TransactionLogDaoImpl.class);
	private final static String RESPONSE_CODE_VALUE = "00";

	
	@Qualifier(value = "entityManagerFactory")
	private final EntityManagerFactory entityManagerFactory;

//	@Autowired
//	SessionFactory sessionFactory;

	@PersistenceContext
	private EntityManager em;

	private EntityManager getCurrentSession() {
		return em;
	}

	private Class<TransactionLog> getPersistentClass() {
		return TransactionLog.class;
	}

	@Override
	public TransactionLog findByMerchantAndMerchantTxnId(FilterVo filterVo, Boolean duplicateMerchantTxnId) {
		try {

			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<TransactionLog> criteria = builder.createQuery(getPersistentClass());
			Root<TransactionLog> root = criteria.from(getPersistentClass());
			criteria.select(root);
			if (duplicateMerchantTxnId == false) {
				criteria.where(builder.equal(root.get("merchanttxnid"), filterVo.getMerchantTxnId()),
						builder.equal(root.get("merchantId"), filterVo.getMerchantId()));

				List<TransactionLog> transactionLog = getCurrentSession().createQuery(criteria).getResultList();
//				TransactionLog transactionLog2 = new TransactionLog();

				for (TransactionLog t : transactionLog) {
//					transactionLog2 = new TransactionLog();
					if (t.getTxnStatus().equalsIgnoreCase("SUCCESS")) {
						return t;
					}

				}
				return null;

			} else {
				criteria.where(builder.equal(root.get("merchanttxnid"), filterVo.getMerchantTxnId()),
						builder.equal(root.get("merchantId"), filterVo.getMerchantId()));

				TransactionLog transactionLog = getCurrentSession().createQuery(criteria).getSingleResult();
				return transactionLog;
			}
		} catch (Exception e) {
			logger.info("No transaction found for = " + filterVo.getMerchantId());
//			new GlobalExceptionHandler().customException(e);
		}

		return null;

	}

	@Override
	public List<TransactionLog> getSuccessTxnListByDateRangeAndMid(String fromDate, String toDate, Long merchantId) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<TransactionLog> query = builder.createQuery(TransactionLog.class);
			Root<TransactionLog> root = query.from(TransactionLog.class);
			query.select(root);

			Date fromDate1 = getStringDate(fromDate);
			Date toDate1 = getStringDate(toDate);
			Calendar c = Calendar.getInstance();
			c.setTime(toDate1);
			c.add(Calendar.DATE, 1);
			toDate1 = c.getTime();

			query.where(builder.between(root.get("createdDate").as(Date.class), fromDate1, toDate1),
					builder.equal(root.get("merchantId"), merchantId),
					builder.equal(root.get("responseCode"), RESPONSE_CODE_VALUE))
					.orderBy(builder.desc(root.get("createdDate")));

			return getCurrentSession().createQuery(query).getResultList();

		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	private java.util.Date getStringDate(String fromDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			java.util.Date date = dateFormat.parse(fromDate);
			return date;
		} catch (Exception e) {
			logger.info("Date  format not matched !!" + e.getMessage());
		}
		return null;
	}

	@Override
	public BigDecimal getTransactionSumOfMid(LocalDateTime fromDate, LocalDateTime toDate, Long merchantId) {
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<TransactionLog> root = criteriaQuery.from(TransactionLog.class);
		criteriaQuery.select(criteriaBuilder.sum(root.get("amt"))).where(
				criteriaBuilder.between(root.get("createdDate"), fromDate, toDate),
				criteriaBuilder.equal(root.get("merchantId"), merchantId),
				criteriaBuilder.equal(root.get("txnStatus"), "SUCCESS"));
		return getCurrentSession().createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public TransactionLog findById(Long valueOf) {
		
		return null;
	}
	
	
	public void update(TransactionLog tlog) {
		//this.getCurrentSession().update(tlog);
	}
}
