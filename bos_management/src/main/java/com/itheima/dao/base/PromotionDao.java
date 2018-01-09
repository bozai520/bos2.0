package com.itheima.dao.base;


import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.take_delivery.base.Promotion;


public interface PromotionDao extends JpaRepository<Promotion, Integer> {
	
/*	@Query(value="select * from T_PROMOTION where rownum<=4",nativeQuery=true)
	public List<Promotion> pageQuery();*/
}
