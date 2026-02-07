package com.ftk.pg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ftk.pg.modal.ConvenienceCharges;

import jakarta.persistence.*;


public interface ConvenienceChargesRepo extends JpaRepository<ConvenienceCharges, Long> {

	@Query(value = "SELECT * FROM convenience_charges WHERE status = 1 " + "AND (:mid IS NULL OR mid = :mid) "
			+ "AND (:paymentMode IS NULL OR payment_mode = :paymentMode) "
			+ "AND (:cardType IS NULL OR card_type = :cardType) "
			+ "AND (:bankId IS NULL OR bank_id = :bankId)", nativeQuery = true)
	List<ConvenienceCharges> findConvenienceChargeOtherPayment(@Param("mid") String mid,
			@Param("paymentMode") String paymentMode, @Param("cardType") String cardType, @Param("bankId") Long bankId);
	
	

	@Query(value = "SELECT * FROM convenience_charges WHERE mid = :mid AND payment_mode = :paymentMode AND status = 1 AND (:cardType IS NULL OR card_type = :cardType)", nativeQuery = true)
	List<ConvenienceCharges> getChargesByMidPaymentModeAndCardType(@Param("mid") Long mid,
			@Param("paymentMode") String paymentMode, @Param("cardType") String cardType)
			throws NoResultException, NonUniqueResultException;

	@Query(value = "SELECT * FROM convenience_charges WHERE mid = :mid AND payment_mode = :paymentMode AND status = 1 "
			+ "AND (:cardType IS NULL OR card_type = :cardType)", nativeQuery = true)
	List<ConvenienceCharges> getChargesByMidPaymentModeAndCardTypeAll(@Param("mid") Long mid,
			@Param("paymentMode") String paymentMode, @Param("cardType") String cardType);
}
