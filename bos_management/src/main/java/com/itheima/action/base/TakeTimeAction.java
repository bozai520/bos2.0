package com.itheima.action.base;

import java.util.List;

import javax.ws.rs.Consumes;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.dao.base.TakeTimeDao;
import com.itheima.domain.base.TakeTime;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class TakeTimeAction extends ActionSupport implements ModelDriven<TakeTime> {

	
		
	//模型驱动
	private TakeTime takeTime = new TakeTime();
	
	@Override
	public TakeTime getModel() {
		// TODO Auto-generated method stub
		return takeTime;
	}
	
	//注入一个dao
	@Autowired
	private TakeTimeDao takeTimeDao;

	@Action(value="find_takeTime",results={@Result(name="success",type="json")})
	public String findAll(){
		
		System.out.println("我是TakeTime");
		
		//获取到所有的时间
		List<TakeTime> list = takeTimeDao.findAll();
		
		//将数据进行压栈
		ActionContext.getContext().getValueStack().push(list);
		
		return SUCCESS;
	}
	
	
}
