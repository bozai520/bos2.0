package com.itheima.serviceimpl.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.dao.base.TakeTimeDao;
import com.itheima.domain.base.TakeTime;
import com.itheima.service.base.TakeTimeService;
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {

	//注入一个Dao层的对象
	@Autowired
	private TakeTimeDao takeTimeDao;
	
	@Override
	public List<TakeTime> findAll() {
		List<TakeTime> findAll = takeTimeDao.findAll();
		for (TakeTime takeTime : findAll) {
			System.out.println(takeTime);
		}
		return findAll;
	}

}	
