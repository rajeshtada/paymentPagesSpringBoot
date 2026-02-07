package com.ftk.pg.pi.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ftk.pg.pi.modal.TransactionMapping;
import com.ftk.pg.pi.repo.TransactionMappingDao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TransactionMappingService {

	
	static Logger logger = LogManager.getLogger(TransactionMappingService.class);

	private final TransactionMappingDao transactionMappingDao;
	
	
    public void saveTransactionMapping(TransactionMapping transactionMapping) {
    	transactionMappingDao.save(transactionMapping);
    }
	
}
