package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "processor_wallet_holder")
public class ProcessorWalletHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;
	
	@Column(name = "wallet_id")
	private long walletId;
	
	@Column(name = "processor_bank_id")
	private String processorWalletId;
	
	@Column(name = "processor_bank_name")
	private String processorWalletName;
	
	@Column(name = "processor_id")
	private long processorId;
	
	@Column(name = "processor_name")
	private String processor;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getWalletId() {
		return walletId;
	}

	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}

	public String getProcessorWalletId() {
		return processorWalletId;
	}

	public void setProcessorWalletId(String processorWalletId) {
		this.processorWalletId = processorWalletId;
	}

	public String getProcessorWalletName() {
		return processorWalletName;
	}

	public void setProcessorWalletName(String processorWalletName) {
		this.processorWalletName = processorWalletName;
	}

	public long getProcessorId() {
		return processorId;
	}

	public void setProcessorId(long processorId) {
		this.processorId = processorId;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	
	
	
	
	
	
	
}
