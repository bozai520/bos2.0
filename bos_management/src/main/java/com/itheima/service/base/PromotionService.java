package com.itheima.service.base;




import org.springframework.data.domain.Page;

import com.itheima.domain.base.Promotion;

public interface PromotionService {

	
	/**
	 * 添加一个宣传活动到数据库中
	 * @param promotion
	 */
	public void addPromotion(Promotion promotion);
	
	
	/**
	 * 分页进行数据查询
	 * @param pageable
	 * @return
	 */
	public Page<Promotion> findAllPromotion(int page, int rows);
//	public List<Promotion> findAllPromotion();s
	
}
