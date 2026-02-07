package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.Invoice;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {

	Invoice findByToken(String token);
	
	Invoice findByTxnId(Long txnId);

}
