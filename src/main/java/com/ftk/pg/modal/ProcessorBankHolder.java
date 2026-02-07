package com.ftk.pg.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "processor_bank_holder")
public class ProcessorBankHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long id;
	
	@Column(name = "bank_id")
	private long bankId;
	
	@Column(name = "processor_bank_id")
	private String processorBankId;
	
	@Column(name = "processor_bank_name")
	private String processorBankName;
	
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
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	public String getProcessorBankId() {
		return processorBankId;
	}
	public void setProcessorBankId(String processorBankId) {
		this.processorBankId = processorBankId;
	}
	public String getProcessorBankName() {
		return processorBankName;
	}
	public void setProcessorBankName(String processorBankName) {
		this.processorBankName = processorBankName;
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
