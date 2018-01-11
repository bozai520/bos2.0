package com.itheima.service.base;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.itheima.take_delivery.base.Order;


@Produces("/")
public interface OrderService {
	
	/**
	 * 保存订单信息
	 * @param order
	 */
	@Path("/order")
	@POST
	@Consumes("application/json")
	public void addOrder(Order order);
}
