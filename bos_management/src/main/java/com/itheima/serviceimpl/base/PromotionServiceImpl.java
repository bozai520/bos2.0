package com.itheima.serviceimpl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.dao.base.PromotionDao;
import com.itheima.pagebean.PageBean;
import com.itheima.service.base.PromotionService;
import com.itheima.take_delivery.base.Promotion;
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
	/**
	 * 获取到活动首页的前四个宣传活动
	 */
	@Override
	public PageBean<Promotion> pageQuery(int page,int pageSize) {
		//封装参数
		Pageable pageable = new PageRequest(page-1,pageSize);
		
		//调用业务逻辑查询数据
		Page<Promotion> pg = pd.findAll(pageable);
		long totalCount = pg.getTotalElements();
		List<Promotion> pageList = pg.getContent();
		
		//封装结果集
		PageBean<Promotion> pageBean = new PageBean<Promotion>();
		pageBean.setTotalCount(totalCount);
		pageBean.setPageList(pageList);
		return pageBean;
	}
	/**
	 * 根据ID查询宣传活动的详情
	 */
	@Override
	public Promotion findOne(int id) {
		Promotion promotion = pd.findOne(id);
		return promotion;
	}
}
