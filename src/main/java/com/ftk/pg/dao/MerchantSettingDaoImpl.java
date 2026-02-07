package com.ftk.pg.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ftk.pg.exception.GlobalExceptionHandler;
import com.ftk.pg.modal.MerchantSetting;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MerchantSettingDaoImpl implements MerchantSettingDao {
	private Logger logger = LogManager.getLogger(MerchantSettingDaoImpl.class);

	
	@Qualifier(value = "entityManagerFactory")
	private final EntityManagerFactory entityManagerFactory;

//	@Autowired
//	SessionFactory sessionFactory;

	@PersistenceContext
	private EntityManager em;

	private EntityManager getCurrentSession() {
		return em;
	}

	private Class<MerchantSetting> getPersistentClass() {
		return MerchantSetting.class;
	}

//	@Autowired
//	SessionFactory sessionFactory;
//
//	protected Session getCurrentSession() {
//		return sessionFactory.getCurrentSession();
//	}
//	
//	private Class<MerchantSetting> getPersistentClass() {
//		return MerchantSetting.class;
//	}
	@Override
	public List<MerchantSetting> findByMerchantName(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);
		criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()));
		try {
			List<MerchantSetting> merchantSetting = getCurrentSession().createQuery(criteria).getResultList();
			return merchantSetting;
		} catch (Exception e) {
			logger.info("Error While Fetching findByMerchantName Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public void changeStatus(Long id, Boolean status) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaUpdate<MerchantSetting> criteria = builder.createCriteriaUpdate(MerchantSetting.class);
			Root<MerchantSetting> root = criteria.from(getPersistentClass());
			criteria.set("status", status);
			criteria.where(builder.equal(root.get("merchantSettingId"), id));
			getCurrentSession().createQuery(criteria).executeUpdate();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	@Override
	public MerchantSetting findByMerchantSetting(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> whereList = new ArrayList<Predicate>();
		whereList.add(builder.equal(root.get("merchantId"), setting.getMerchantId()));
		whereList.add(builder.equal(root.get("currency"), setting.getCurrency()));
		whereList.add(builder.equal(root.get("paymentMode"), setting.getPaymentMode()));
		whereList.add(builder.equal(root.get("paymentType"), setting.getPaymentType()));
		whereList.add(builder.equal(root.get("isDefault"), setting.isDefault()));
		whereList.add(builder.equal(root.get("status"), true));
		if (setting.getBank() != null && setting.getBank() > 0) {
			whereList.add(builder.equal(root.get("bank"), setting.getBank()));
		}

		criteria.where(whereList.toArray(new Predicate[whereList.size()]));
		try {
			MerchantSetting merchantSetting = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchantSetting;
		} catch (Exception e) {
			logger.info("Error While Fetching findByMerchantSetting Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override

	public MerchantSetting findDefaultMerchantSetting(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);
		criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()),
				builder.equal(root.get("currency"), setting.getCurrency()),
				builder.equal(root.get("paymentMode"), setting.getPaymentMode()),
				builder.equal(root.get("productType"), setting.getProductType()),
				builder.equal(root.get("isDefault"), true), builder.equal(root.get("status"), true));

		try {
			MerchantSetting merchantSetting = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchantSetting;
		} catch (Exception e) {
			logger.info("Error While Fetching findDefaultMerchantSetting Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override

	public MerchantSetting findNonDefaultMerchantSetting(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get("merchantId"), setting.getMerchantId()));
		predicates.add(builder.equal(root.get("currency"), setting.getCurrency()));
		predicates.add(builder.equal(root.get("paymentMode"), setting.getPaymentMode()));
		predicates.add(builder.equal(root.get("productType"), setting.getProductType()));
		predicates.add(builder.equal(root.get("isDefault"), false));
		predicates.add(builder.equal(root.get("status"), true));
		if (setting.getBank() != null && setting.getBank() > 0 && setting.getPaymentMode() != null
				&& setting.getPaymentMode().equalsIgnoreCase("NB")) {
			predicates.add(builder.equal(root.get("bank"), setting.getBank()));
		}

		Predicate[] ps = new Predicate[predicates.size()];
		ps = predicates.toArray(ps);
		criteria.where(ps);
		try {
			MerchantSetting merchantSetting = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchantSetting;
		} catch (Exception e) {
			logger.info("Error While Fetching findNonDefaultMerchantSetting Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "Bank " + setting.getBank()
					+ "  Reason: " + e.getMessage());
		}
		return null;
	}

//	@Override
//	public Long saveMerchantSetting(MerchantSetting setting) {
//		try {
//			Long merchantSettingId = (Long) persist(setting);
//			return merchantSettingId;
//		} catch (Exception e) {
//			logger.info("Error While Fetching saveMerchantSetting Merchant setting For Merchant Id: "
//					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
//					+ e.getMessage());
//		}
//
//		return null;
//	}

	// findByMerchantName old Name
	@Override
	public List<MerchantSetting> findByMidAndDefault(MerchantSetting setting) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
			Root<MerchantSetting> root = criteria.from(getPersistentClass());
			criteria.select(root);
			criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()),
					builder.equal(root.get("isDefault"), true));
			return getCurrentSession().createQuery(criteria).getResultList();
		} catch (Exception e) {
			logger.info("Error While Fetching findByMidAndDefault Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public MerchantSetting findMerchantSettingByPaymodeCurr(MerchantSetting setting) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
			Root<MerchantSetting> root = criteria.from(getPersistentClass());
			criteria.select(root);
			criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()),
					builder.equal(root.get("currency"), setting.getCurrency()),
					builder.equal(root.get("paymentMode"), setting.getPaymentMode()),
					builder.equal(root.get("isDefault"), setting.isDefault()));
			return getCurrentSession().createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			System.out
					.println("Error While Fetching findMerchantSettingByPaymodeCurr Merchant setting For Merchant Id: "
							+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
							+ e.getMessage());
		}
		return null;
	}

	public List<MerchantSetting> findNotDefaultMerchantSetting(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);
		criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()),
				builder.equal(root.get("productType"), setting.getProductType()),
				builder.equal(root.get("isDefault"), setting.isDefault()));
		try {
			List<MerchantSetting> merchantSettings = getCurrentSession().createQuery(criteria).getResultList();
			return merchantSettings;
		} catch (Exception e) {
			logger.info("Error While Fetching findNotDefaultMerchantSetting Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

//	@Override
//	public MerchantSetting saveNotDefaultMerchantSetting(MerchantSetting setting) {
//		try {
//			MerchantSetting merchantS = (MerchantSetting) persist(setting);
//			return merchantS;
//		} catch (Exception e) {
//			logger.info("Error While Fetching saveNotDefaultMerchantSetting Merchant setting For Merchant Id: "
//					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
//					+ e.getMessage());
//		}
//		return null;
//	}

	@Override
	public Long MerchantSettingsRowCount(MerchantSetting merchantSetting) {
		logger.info("Merchant Setting Details inside Check: " + merchantSetting);
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> cqCount = builder.createQuery(Long.class);
		Root<MerchantSetting> root = cqCount.from(MerchantSetting.class);
		cqCount.select(builder.count(root));
		cqCount.where(builder.equal(root.get("merchantId"), merchantSetting.getMerchantId()),
				builder.equal(root.get("paymentType"), "ALL"),
				builder.equal(root.get("paymentMode"), merchantSetting.getPaymentMode()),
				builder.equal(root.get("currency"), merchantSetting.getCurrency()),
				builder.equal(root.get("processor"), merchantSetting.getProcessor()),
				builder.equal(root.get("isDefault"), merchantSetting.isDefault()));
		Long singleResult = getCurrentSession().createQuery(cqCount).getSingleResult();

		logger.info("singleResult :: ===== >> " + singleResult);
		logger.info("singleResult :: ===== >> " + singleResult);

		return singleResult;

	}

	@Override
	public List<MerchantSetting> findMerchantSetting(MerchantSetting setting) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
			Root<MerchantSetting> root = criteria.from(getPersistentClass());
			criteria.select(root);
			criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()),
					builder.equal(root.get("productType"), setting.getProductType()),
					builder.equal(root.get("isDefault"), setting.isDefault()));
			return (List<MerchantSetting>) getCurrentSession().createQuery(criteria).getResultList();
		} catch (Exception e) {
			logger.info("Error While Fetching findMerchantSetting Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public List<MerchantSetting> findNDefaultMerchantSetting(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);
		criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()),
				builder.equal(root.get("isDefault"), true));
		try {
			List<MerchantSetting> merchantSetting = getCurrentSession().createQuery(criteria).getResultList();
			return merchantSetting;
		} catch (Exception e) {
			logger.info("Error While Fetching findNDefaultMerchantSetting Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public Long defaultMerchantRowCount(MerchantSetting merchantSetting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> cqCount = builder.createQuery(Long.class);
		Root<MerchantSetting> root = cqCount.from(MerchantSetting.class);
		cqCount.select(builder.count(root));
		cqCount.where(builder.equal(root.get("merchantId"), merchantSetting.getMerchantId()),
				builder.equal(root.get("paymentMode"), merchantSetting.getPaymentMode()),
				builder.equal(root.get("currency"), merchantSetting.getCurrency()),
				builder.equal(root.get("processor"), merchantSetting.getProcessor()),
				builder.equal(root.get("isDefault"), true));
		return getCurrentSession().createQuery(cqCount).getSingleResult();
	}

	@Override
	public void deleteMerchantSettingsByName(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaDelete<MerchantSetting> criteria = builder.createCriteriaDelete(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()));
		try {
			getCurrentSession().createQuery(criteria).executeUpdate();
			logger.info("Merchant Setting Deleted");
		} catch (Exception e) {
			logger.info("Error While Fetching deleteMerchantSettingsByName Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
	}

	@Override
	public Map<Long, String> findAtomActiveMID() {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.multiselect(root.get("merchantId"), root.get("mloginId"));
		criteria.distinct(true);
		criteria.where(builder.equal(root.get("processor"), "PAYNETZ"), builder.notEqual(root.get("mloginId"), "197"));
		try {
			Map<Long, String> atomMid = new HashMap<>();
			List<Object[]> merchantSetting = getCurrentSession().createQuery(criteria).getResultList();
			for (Object[] objects : merchantSetting) {
				atomMid.put((Long) objects[0], (String) objects[1]);
			}
			return atomMid;
		} catch (Exception e) {
			logger.info("Error While Fetching findAtomActiveMID ==> " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<MerchantSetting> findByMids(List<Long> mids) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<MerchantSetting> query = builder.createQuery(MerchantSetting.class);
			Root<MerchantSetting> root = query.from(MerchantSetting.class);
			query.select(root).where(root.get("merchantId").in(mids));
			return getCurrentSession().createQuery(query).getResultList();
		} catch (Exception e) {
			logger.info("Error While Fetching merchantSettings by mids ==> " + mids);
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public List<MerchantSetting> deleteByIds(List<Long> ids) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaUpdate<MerchantSetting> query = builder.createCriteriaUpdate(MerchantSetting.class);

			Root<MerchantSetting> root = query.from(MerchantSetting.class);
			query.set(root.get("status"), false);
			query.where(root.get("merchantSettingId").in(ids));
			getCurrentSession().createQuery(query).executeUpdate();
		} catch (Exception e) {
			logger.info("Error in deleting merchant settings by ids ==> " + ids);
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public void persistInBatch(List<MerchantSetting> list) {
		try {
			list.forEach(mSetting -> {
				getCurrentSession().persist(mSetting);
			});
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
	}

	@Override
	public void remove(Long id) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaDelete<MerchantSetting> criteria = builder.createCriteriaDelete(MerchantSetting.class);
		Root<MerchantSetting> root = criteria.from(MerchantSetting.class);
		criteria.where(builder.equal(root.get("merchantSettingId"), id));
		getCurrentSession().createQuery(criteria).executeUpdate();
	}

	@Override
	public MerchantSetting findLyraMerchantSetting(MerchantSetting merchantObj) {

		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
			Root<MerchantSetting> root = criteria.from(getPersistentClass());
			criteria.select(root);
			criteria.where(builder.equal(root.get("merchantId"), merchantObj.getMerchantId()),
					builder.equal(root.get("currency"), merchantObj.getCurrency()),
					builder.equal(root.get("paymentMode"), merchantObj.getPaymentMode()),

					builder.equal(root.get("processor"), merchantObj.getProcessor()),
//			builder.equal(root.get("productType"), merchantObj.getProductType()),
					builder.equal(root.get("status"), merchantObj.isStatus()));
			return getCurrentSession().createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public MerchantSetting findDefaultMerchantSettingwithBank(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);
		criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()),
				builder.equal(root.get("currency"), setting.getCurrency()),
				builder.equal(root.get("paymentMode"), setting.getPaymentMode()),
				builder.equal(root.get("productType"), setting.getProductType()),
				builder.equal(root.get("bank"), setting.getBank()), builder.equal(root.get("isDefault"), true),
				builder.equal(root.get("status"), true));

		try {
			MerchantSetting merchantSetting = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchantSetting;
		} catch (Exception e) {
			logger.info("Error While Fetching findDefaultMerchantSetting Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public MerchantSetting findByMerchantSettingId(Long merchantSettingId) {
		try {
			CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
			CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
			Root<MerchantSetting> root = criteria.from(getPersistentClass());
			criteria.select(root);
			criteria.where(builder.equal(root.get("merchantSettingId"), merchantSettingId));
			return getCurrentSession().createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			new GlobalExceptionHandler().customException(e);
		}
		return null;
	}

	@Override
	public List<MerchantSetting> findNonDefaultMerchantSettingList(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(root.get("merchantId"), setting.getMerchantId()));
		predicates.add(builder.equal(root.get("currency"), setting.getCurrency()));
		predicates.add(builder.equal(root.get("paymentMode"), setting.getPaymentMode()));
		predicates.add(builder.equal(root.get("productType"), setting.getProductType()));
		predicates.add(builder.equal(root.get("isDefault"), false));
		predicates.add(builder.equal(root.get("status"), true));
		if (setting.getBank() != null && setting.getBank() > 0 && setting.getPaymentMode() != null
				&& setting.getPaymentMode().equalsIgnoreCase("NB")) {
			predicates.add(builder.equal(root.get("bank"), setting.getBank()));
		}

		Predicate[] ps = new Predicate[predicates.size()];
		ps = predicates.toArray(ps);
		criteria.where(ps);
		try {
			return getCurrentSession().createQuery(criteria).getResultList();
		} catch (Exception e) {
			logger.info("Error While Fetching getEnableMerchantSettingList Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "Bank " + setting.getBank()
					+ "  Reason: " + e.getMessage());
		}
		return null;
	}

	@Override
	public List<MerchantSetting> findDefaultMerchantSettingList(MerchantSetting setting) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);
		criteria.where(builder.equal(root.get("merchantId"), setting.getMerchantId()),
				builder.equal(root.get("currency"), setting.getCurrency()),
				builder.equal(root.get("paymentMode"), setting.getPaymentMode()),
				builder.equal(root.get("productType"), setting.getProductType()),
				builder.equal(root.get("isDefault"), true), builder.equal(root.get("status"), true));

		try {
			return getCurrentSession().createQuery(criteria).getResultList();
		} catch (Exception e) {
			logger.info("Error While Fetching findDefaultMerchantSettingList Merchant setting For Merchant Id: "
					+ setting.getMerchantId() + " & PayMode: " + setting.getPaymentMode() + "  Reason: "
					+ e.getMessage());
		}
		return null;
	}

	@Override
	public List<MerchantSetting> getEnableMerchantSettingList(MerchantSetting merchantSettingObj) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate status = builder.equal(root.get("status"), 1);
		predicates.add(status);

		if (merchantSettingObj.getMerchantId() != null && merchantSettingObj.getMerchantId() > 0) {
			Predicate merchantId = builder.equal(root.get("merchantId"), merchantSettingObj.getMerchantId());
			predicates.add(merchantId);
		}
		if (merchantSettingObj.getCurrency() != null && !merchantSettingObj.getCurrency().equals("")) {
			Predicate currency = builder.equal(root.get("currency"), merchantSettingObj.getCurrency());
			predicates.add(currency);
		}
		if (merchantSettingObj.getPaymentMode() != null && !merchantSettingObj.getPaymentMode().equals("")) {
			Predicate paymentMode = builder.equal(root.get("paymentMode"), merchantSettingObj.getPaymentMode());
			predicates.add(paymentMode);
		}
		if (merchantSettingObj.getProductType() != null && !merchantSettingObj.getProductType().equals("")) {
			Predicate productType = builder.equal(root.get("productType"), merchantSettingObj.getProductType());
			predicates.add(productType);
		}

		if (merchantSettingObj.getBank() != null && merchantSettingObj.getBank() > 0
				&& merchantSettingObj.getPaymentMode() != null
				&& merchantSettingObj.getPaymentMode().equalsIgnoreCase("NB")) {
			Predicate bank = builder.equal(root.get("bank"), merchantSettingObj.getBank());
			predicates.add(bank);
		}

		Predicate[] ps = new Predicate[predicates.size()];
		ps = predicates.toArray(ps);
		criteria.where(ps);
		try {
			return getCurrentSession().createQuery(criteria).getResultList();
		} catch (Exception e) {
			logger.info("Error While Fetching getEnableMerchantSettingList Merchant setting For Merchant Id: "
					+ merchantSettingObj.getMerchantId() + " & PayMode: " + merchantSettingObj.getPaymentMode()
					+ "Bank " + merchantSettingObj.getBank() + "  Reason: " + e.getMessage());
		}
		return null;
	}

	@Override
	public MerchantSetting findByMidandPaymentModeandProcessor(Long mid, String paymentMode, String processor) {
		CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<MerchantSetting> criteria = builder.createQuery(getPersistentClass());
		Root<MerchantSetting> root = criteria.from(getPersistentClass());
		criteria.select(root);
		criteria.where(builder.equal(root.get("merchantId"), mid), builder.equal(root.get("processor"), processor),
				builder.equal(root.get("paymentMode"), paymentMode), builder.equal(root.get("status"), true));

		try {
			MerchantSetting merchantSetting = getCurrentSession().createQuery(criteria).getSingleResult();
			return merchantSetting;
		} catch (Exception e) {
			logger.info("Error While Fetching findDefaultMerchantSetting Merchant setting For Merchant Id: " + mid
					+ " & PayMode: " + paymentMode + "  Reason: " + e.getMessage());
		}

		return null;
	}

}
