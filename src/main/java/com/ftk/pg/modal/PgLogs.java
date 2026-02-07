package com.ftk.pg.modal;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pg_logs")
@Data
public class PgLogs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String level;
	private String message;
	private String logger;
	private String thread;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date timestamp; 

	@CreationTimestamp
	private LocalDateTime timestamp;

	private String messageId;
	private String rrn;
}
