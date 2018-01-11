package com.itheima.serviceimpl.base;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.dao.base.OrderDao;
import com.itheima.domain.base.Area;
import com.itheima.domain.base.Courier;
import com.itheima.domain.base.Customer;
import com.itheima.domain.base.FixedArea;
import com.itheima.domain.base.SubArea;
import com.itheima.service.base.AreaService;
import com.itheima.service.base.FixedAreaService;
import com.itheima.service.base.OrderService;
import com.itheima.service.base.WorkBillService;
import com.itheima.take_delivery.base.Order;
import com.itheima.take_delivery.base.WorkBill;
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	//注入一个OrderService的对象
	@Autowired
	private OrderDao od;
	
	//注入一个FixedAreaService的对象
	@Autowired
	private FixedAreaService fas;
	
	//注入一个AreaService的对象
	@Autowired
	private AreaService areaService;
	
	//注入一个workBillDao的对象
	@Autowired
	private WorkBillService workBillService;
	
	//注入一个模板JpaTemplate对象
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsQueueTemplate;
	
	@Override
	public void addOrder(Order order) {
		
		
		//获取到区域的对象
		Area sarea = order.getSendArea();
		//通过区域地质去查询该区查询该区域是否存在
		sarea = areaService.findByProvinceAndCityAndDistrict(sarea.getProvince(), sarea.getCity(), sarea.getDistrict());
		//将获取到的持久态对象封装到order对象
		order.setSendArea(sarea);
		
		//获取到区域的对象
		Area rarea = order.getSendArea();
		//通过区域地质去查询该区查询该区域是否存在
		rarea = areaService.findByProvinceAndCityAndDistrict(rarea.getProvince(), rarea.getCity(), rarea.getDistrict());
		//将获取到的持久态对象封装到order对象
		order.setRecArea(rarea);
		System.out.println("这里是BOS_management系统：：：：：");
		//判断该地址是否在CRM系统中存在，如果存在，查询出fixedArea，并找到对应的 客户信息
				Customer customer = WebClient.create("http://localhost:9998/crm_management/service/customerService/customer/findByAddress?sendAddress="+order.getSendAddress()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
				
				System.out.println("Customer:::::"+customer);
				if(customer!=null){
					//获取到fixedAreaId
					String fixedAreaId = customer.getFixedAreaId();
					if(fixedAreaId!=null){
						//根据fixedAreaId获取到定区
						FixedArea fixedArea = fas.findByfixedAreaId(fixedAreaId);
						Iterator<Courier> iterator = fixedArea.getCouriers().iterator();
						
						
						if(iterator.hasNext()){
							Courier courier = iterator.next();
							System.out.println("快递员"+courier);
							System.out.println("自动分单成功");
							order.setCourier(courier);
							order.setOrderType("1");
							//保存订单
							order.setOrderNum(UUID.randomUUID().toString());
							od.save(order);
							//生成工单，发送短信
							return;
						}
					}
				}
				
				//当用户表中不存在该对象的时候，需要通过使用用户的地址进行区域的匹配
				
				
				if(sarea!=null){
					Set<SubArea> subareas = sarea.getSubareas();
					for (SubArea subArea : subareas) {
						if(order.getSendAddress().contains(subArea.getKeyWords())){
							FixedArea fixedArea = subArea.getFixedArea();
							Iterator<Courier> iterator = fixedArea.getCouriers().iterator();
							if(iterator.hasNext()){
								Courier courier = iterator.next();
								System.out.println("快递员"+courier);
								System.out.println("自动分单成功");
								order.setCourier(courier);
								//保存订单
								order.setOrderNum(UUID.randomUUID().toString());
								order.setOrderType("2");
								od.save(order);
								//生成工单，发送短信
								return;
							}
						}
					}
				}
				
				//如果前面两种都没能实现自动分单,那么就必须进行人为的手工分单
				order.setOrderType("3");
				od.save(order);
	}
	
	/**
	 * 生成工单
	 */
	public void ProductorWorkBill(final Order order){
		
		/*
		private Integer id; // 主键
		private String type; // 工单类型 新,追,销
		 * 新单:没有确认货物状态的 已通知:自动下单下发短信 已确认:接到短信,回复收信确认信息 已取件:已经取件成功,发回确认信息 生成工作单
		 * 已取消:销单
		private String pickstate; // 取件状态
		private Date buildtime; // 工单生成时间
		private Integer attachbilltimes; // 追单次数
		private String remark; // 订单备注
		private String smsNumber; // 短信序号
		private Courier courier;// 快递员
		private Order order; // 订单
*/		
		//创建一个工单对象
		WorkBill workBill = new WorkBill();
		workBill.setType("新");
		workBill.setPickstate("新单");
		workBill.setBuildtime(new Date());
		workBill.setRemark(order.getRemark());
		final String smsNumber = RandomStringUtils.randomNumeric(4);
		workBill.setSmsNumber(smsNumber);
		workBill.setOrder(order);
		workBill.setCourier(order.getCourier());
		//保存工单
		workBillService.saveWorkBill(workBill);
		
		//发送短信，使用MQ发送一个消息给MQ服务器
		jmsQueueTemplate.send("bos_sms", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				String telephone = order.getCourier().getTelephone();
				
				String context = "短信序列"+smsNumber+",取件地址:"+order.getRecAddress()+",联系人"+order.getSendName()+",联系电话："+order.getSendMobile()+",快递员捎话："+order.getSendMobileMsg();
				
				mapMessage.setString("telephone", telephone);
				mapMessage.setString("context", context);
				return mapMessage;
			}
		});
		workBill.setType("已通知");		
		
	}

}
