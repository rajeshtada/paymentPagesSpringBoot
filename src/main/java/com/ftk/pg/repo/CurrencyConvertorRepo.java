package com.ftk.pg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftk.pg.modal.CurrencyConvertor;
@Repository
public interface CurrencyConvertorRepo extends JpaRepository<CurrencyConvertor, Long>{

}
