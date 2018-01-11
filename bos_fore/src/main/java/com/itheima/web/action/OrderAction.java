package com.itheima.web.action;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.domain.base.Area;
import com.itheima.domain.base.Customer;
import com.itheima.take_delivery.base.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

	//模型驱动
	private Order order = new Order();
	
	@Override
	public Order getModel() {
		return order;
	}
	
	/**
	 * 添加一个订单
	 * @return
	 */
	@Action(value="order_add",results={@Result(name="success",type="redirect",location="index.html")})
	public String addOrder(){
		//接收前台页面传输过来的省市区信心
//寄件人
		//创建一个Area的对象
		Area sendArea= new Area();
		String sendAreaInfo = ServletActionContext.getRequest().getParameter("sendAreaInfo");
		//对数据进行处理
		String[] sendSplit = sendAreaInfo.split("/");
		sendArea.setProvince(sendSplit[0]);
		sendArea.setCity(sendSplit[1]);;
		sendArea.setDistrict(sendSplit[2]);
		//将数据封装到模型中
		order.setSendArea(sendArea);
//收件人信息
		//穿件一个收件的Area对象
		Area recArea= new Area();
		String recAreaInfo = ServletActionContext.getRequest().getParameter("recAreaInfo");
		//对接受到的数据进行处理
		String[] recSplit = recAreaInfo.split("/");
		recArea.setProvince(recSplit[0]);
		recArea.setCity(recSplit[1]);;
		recArea.setDistrict(recSplit[2]);
		//将数据封装到Order模型中
		order.setRecArea(recArea);
		//将数据保存到bos_managerment系统中
		System.out.println("这里是BOS_FORE的前端系统：：：：：");
		
		WebClient.create("http://localhost:9999/bos_management/services/orderService/order").type(MediaType.APPLICATION_JSON).post(order);
		
		return SUCCESS;
	}
	
	
}
