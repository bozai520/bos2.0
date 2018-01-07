package com.itheima.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.domain.base.Promotion;

public interface PromotionDao extends JpaRepository<Promotion, Integer> {

	
}
