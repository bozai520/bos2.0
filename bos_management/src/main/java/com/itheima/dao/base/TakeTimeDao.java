package com.itheima.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.domain.base.TakeTime;

public interface TakeTimeDao extends JpaRepository<TakeTime, Integer>{

	
}
