package com.ftk.pg.pi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftk.pg.pi.modal.CurrencyConverter;
@Repository
public interface CurrencyConverterRepo extends JpaRepository<CurrencyConverter, Long>{

	@Query(value = "SELECT * FROM currency_converter WHERE from_currency =:currency AND value_time <= CURRENT_TIMESTAMP ORDER BY value_time DESC LIMIT 1",nativeQuery = true)
    CurrencyConverter findLatestData(String currency);

}
