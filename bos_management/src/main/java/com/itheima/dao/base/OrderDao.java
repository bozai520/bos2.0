package com.itheima.dao.base;


import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.take_delivery.base.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {

}
