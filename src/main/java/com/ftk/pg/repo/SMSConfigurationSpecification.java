package com.ftk.pg.repo;

import org.springframework.data.jpa.domain.Specification;

import com.ftk.pg.modal.SMSConfiguration;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class SMSConfigurationSpecification implements Specification<SMSConfiguration>{

	@Override
	public Predicate toPredicate(Root<SMSConfiguration> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
