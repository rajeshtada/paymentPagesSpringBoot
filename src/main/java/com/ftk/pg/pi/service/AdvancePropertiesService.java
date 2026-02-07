package com.ftk.pg.pi.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftk.pg.pi.modal.AdvanceProperties;
import com.ftk.pg.pi.repo.AdvancePropertiesRepo;
import com.ftk.pg.requestvo.PropertiesVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdvancePropertiesService {

	static Logger logger = LogManager.getLogger(AdvancePropertiesService.class);


	private final AdvancePropertiesRepo advancePropertiesRepo;

	public List<PropertiesVo> findByPropertyKeyLike(String string) {
		List<AdvanceProperties> byPropertyKeyLike = advancePropertiesRepo.findByPropertyKeyLike(string);
		List<PropertiesVo> listProperties2 = new ArrayList<>();
		PropertiesVo vo = null;
		if (byPropertyKeyLike != null && byPropertyKeyLike.size() > 0) {

			for (AdvanceProperties properties : byPropertyKeyLike) {
				vo = new PropertiesVo();
				String propKey = properties.getPropertyKey();
				String propVal = properties.getPropertyValue();
				vo.setPropertyKey(propKey);
				vo.setPropertyValue(propVal);

				listProperties2.add(vo);
			}
		}
		return listProperties2;
	}

	public PropertiesVo findByPropertyKey(String string) {
		AdvanceProperties byPropertyKey = advancePropertiesRepo.findByPropertyKey(string);
		PropertiesVo vo = null;
		if (byPropertyKey != null) {

			vo = new PropertiesVo();
			String propKey = byPropertyKey.getPropertyKey();
			String propVal = byPropertyKey.getPropertyValue();
			vo.setPropertyKey(propKey);
			vo.setPropertyValue(propVal);

		}
		return vo;
	}

}
