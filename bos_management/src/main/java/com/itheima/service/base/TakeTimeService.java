package com.itheima.service.base;

import java.util.List;

import com.itheima.domain.base.TakeTime;

public interface TakeTimeService {
	
	/**
	 * 获取到所有的时间
	 * @return
	 */
	public List<TakeTime> findAll();
}
