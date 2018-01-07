package com.itheima.serviceimpl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.dao.base.PromotionDao;
import com.itheima.domain.base.Promotion;
import com.itheima.service.base.PromotionService;
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

	//注入一个dao对象
	@Autowired
	private PromotionDao pd;
	
	@Override
	public void addPromotion(Promotion promotion) {
		pd.save(promotion);
	}

//	@Override
//	public List<Promotion> findAllPromotion() {
//		List<Promotion> list = pd.findAll();
//		return list;
//	}

	@Override
	public Page<Promotion> findAllPromotion(int page,int rows) {
		
		//数据封装
		Page<Promotion> pg = null;
		if(page-1>=0){
			Pageable pageable = new PageRequest(page-1,rows);
		    pg = pd.findAll(pageable);
		}
		return pg;
	}
}
