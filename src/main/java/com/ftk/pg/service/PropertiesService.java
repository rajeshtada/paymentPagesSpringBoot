package com.ftk.pg.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftk.pg.modal.Properties;
import com.ftk.pg.repo.PropertiesRepo;
import com.ftk.pg.requestvo.PropertiesVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PropertiesService {

	static Logger logger = LogManager.getLogger(PropertiesService.class);

	public static String certsPath = "/opt/certs";
	public static String updatedCertsPath = "/mnt/efs/lambda/pg/opt/certs";

	
	private final PropertiesRepo propertiesRepos;
	
//	@PersistenceContext
//	private EntityManager entityManager;

	public PropertiesVo findByPropertykeyWithUpdatedCerts(String propertyKey) {

		Properties properties = propertiesRepos.findByPropertykey(propertyKey);
		PropertiesVo vo = null;
		if (properties != null) {
//			entityManager.detach(byPropertykey);
			vo = new PropertiesVo();
			vo.setPropertyKey(properties.getPropertykey());
			vo.setPropertyValue(properties.getPropertyValue());

			String propertyValue = vo.getPropertyValue();
//			if (propertyValue != null && propertyValue.startsWith("/opt/certs")) {
//				propertyValue = propertyValue.replace("/opt/certs", "/media/shared/opt/certs");
//			} 
//			else if (propertyValue != null && propertyValue.startsWith("/media/shared")) {
//				propertyValue = propertyValue.replace("/media/shared", "/mnt/efs");
//			}
			vo.setPropertyValue(propertyValue);
		}
		return vo;
	}

	public List<PropertiesVo> findByPropertykeyWithUpdatedCertsLike(String string) {
		List<Properties> listProperties = propertiesRepos.findByPropertykeyLike("%"+string+"%");
		List<PropertiesVo> listProperties2 = new ArrayList<>();
		PropertiesVo vo = null;
		if (listProperties != null && listProperties.size() > 0) {
			for (Properties properties : listProperties) {
				vo = new PropertiesVo();
				vo.setPropertyKey(properties.getPropertykey());
				vo.setPropertyValue(properties.getPropertyValue());

				String propertyValue = vo.getPropertyValue();
//				if (propertyValue != null && propertyValue.startsWith("/opt/certs")) {
//					propertyValue = propertyValue.replace("/opt/certs", "/media/shared/opt/certs");
//				} 
//				else if (propertyValue != null && propertyValue.startsWith("/media/shared")) {
//					propertyValue = propertyValue.replace("/media/shared", "/mnt/efs");
//				}
				vo.setPropertyValue(propertyValue);
				listProperties2.add(vo);
			}
		}
		return listProperties2;
	}

	public List<PropertiesVo> findAllPropertyWithUpdatedCerts() {
		List<Properties> listProperties = propertiesRepos.findAll();
		List<PropertiesVo> listProperties2 = new ArrayList<>();
		PropertiesVo vo = null;

		if (listProperties != null && listProperties.size() > 0) {
			for (Properties properties : listProperties) {
				vo = new PropertiesVo();
				vo.setPropertyKey(properties.getPropertykey());
				vo.setPropertyValue(properties.getPropertyValue());

				String propertyValue = vo.getPropertyValue();
//				if (propertyValue != null && propertyValue.startsWith("/opt/certs")) {
//					propertyValue = propertyValue.replace("/opt/certs", "/media/shared/opt/certs");
//				} 
//				else if (propertyValue != null && propertyValue.startsWith("/media/shared")) {
//					propertyValue = propertyValue.replace("/media/shared", "/mnt/efs");
//				}
				vo.setPropertyValue(propertyValue);
				listProperties2.add(vo);
			}
		}
		return listProperties2;
	}

	public String findPropertyValueByKey(String propertyKey) {
		String propValue = null;
		Properties properties = propertiesRepos.findByPropertykey(propertyKey);
		if (properties != null ) {
			propValue =  properties.getPropertyValue();
		}
		return propValue;
	}
	
}
