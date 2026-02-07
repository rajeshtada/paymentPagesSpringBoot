package com.ftk.pg.dao;

import java.util.List;

import com.ftk.pg.modal.EmailConfiguration;

public interface EmailConfigurationDao {

	EmailConfiguration findEmailConfigByMid(EmailConfiguration emailConfiguration);

	List<EmailConfiguration> findConfigByMid(EmailConfiguration configuration);

	EmailConfiguration findEmailConfigByTriggerCode(EmailConfiguration emailConfiguration);

}
