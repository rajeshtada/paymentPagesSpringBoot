package com.ftk.pg.pi.modal;


import java.io.Serializable;

import jakarta.persistence.*;


@Embeddable
public class TransactionMappingPk implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Column(name = "transaction_id")
    private Long transactionId;
    @Column(name = "processor_id")
    private Long processorId;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getProcessorId() {
        return processorId;
    }

    public void setProcessorId(Long processorId) {
        this.processorId = processorId;
    }

	@Override
	public String toString() {
		return "TransactionMappingPk [transactionId=" + transactionId + ", processorId=" + processorId + "]";
	}
    
	 
}