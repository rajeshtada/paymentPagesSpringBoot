package com.ftk.pg.dao;

import java.util.List;

import com.ftk.pg.modal.SMSConfiguration;

public interface SMSConfigurationDao {

	SMSConfiguration findAllSmsConfigByNameAndTriggerName(SMSConfiguration smsConfiguration);

	List<SMSConfiguration> findAllSmsConfigByName(SMSConfiguration smsConfiguration);

}
