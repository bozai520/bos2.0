package com.itheima.service.base;




import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.data.domain.Page;

import com.itheima.pagebean.PageBean;
import com.itheima.take_delivery.base.Promotion;

@Produces("/")
public interface PromotionService {

	
	/**
	 * 添加一个宣传活动到数据库中
	 * @param promotion
	 */
	public void addPromotion(Promotion promotion);
	
	
	/**
	 * 分页进行数据查询
	 * @param pageable
	 * @return
	 */
	public Page<Promotion> findAllPromotion(int page, int rows);
	
	/**
	 * 获取到宣传活动中的前四个活动
	 * @return
	 */
	@Path("/promotion/pageQuery")
	@GET
	@Produces("application/json")
	public PageBean<Promotion> pageQuery(@QueryParam("page") int page,@QueryParam("pageSize") int pageSize);
	
	@Path("/promotion/findByID")
	@GET
	@Produces("application/json")
	public Promotion findOne(@QueryParam("id") int id);
}
