package com.ftk.pg.dao;

import com.ftk.pg.modal.CurrencyConvertor;

public interface CurrencyConvertorDao {

	CurrencyConvertor findByCurrency(String fromCurrency, String toCurrency);

}
