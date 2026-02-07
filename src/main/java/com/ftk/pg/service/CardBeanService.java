package com.ftk.pg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftk.pg.dao.CardBeanDao;
import com.ftk.pg.modal.CardBean;
import com.ftk.pg.repo.CardBeanRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardBeanService {


	private final CardBeanDao cardBeanDao;

	private final  CardBeanRepo cardBeanRepo;
	
	public CardBean getBeanDetailByValue(int value) {
		return cardBeanDao.getBeanDetailbyBeanValue(value);
	}
	
	public List<CardBean> getBeanDetailsByValue(int value) {
	List<CardBean> listCardBean = cardBeanRepo.getBeanDetailbyBeanValue(value);
	return listCardBean;
	}
}
