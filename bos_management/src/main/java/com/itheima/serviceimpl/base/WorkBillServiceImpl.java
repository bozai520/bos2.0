package com.itheima.serviceimpl.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.dao.base.WorkBillDao;
import com.itheima.service.base.WorkBillService;
import com.itheima.take_delivery.base.WorkBill;
@Service
@Transactional
public class WorkBillServiceImpl implements WorkBillService {

	//注入一个WorkBillDao的对象
	@Autowired
	private WorkBillDao workBillDao;
	
	@Override
	public void saveWorkBill(WorkBill workBill) {
		workBillDao.save(workBill);
	}
}
