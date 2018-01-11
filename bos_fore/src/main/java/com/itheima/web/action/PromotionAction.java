package com.itheima.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.constant.base.Constant;
import com.itheima.pagebean.PageBean;
import com.itheima.take_delivery.base.Promotion;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends ActionSupport implements ModelDriven<Promotion> {

	//模型驱动
	private Promotion promotion = new Promotion();
	
	@Override
	public Promotion getModel() {
		// TODO Auto-generated method stub
		return promotion;
	}
	
	
	//接受前台传输过来的分页参数
	private int page;
	private int pageSize;
	/**
	 * 首页的宣传活动的数据查询
	 * @return
	 */
	@Action(value="promotion_pageQuery",results={@Result(name="success",type="json")})
	public String promotionPageQuery(){
		//按照页面显示插叙数据的前四条数据
	PageBean<Promotion> pageBean = WebClient.create("http://localhost:9999/bos_management/services/promotionService/promotion/pageQuery?page="+page+"&pageSize="+pageSize).accept(MediaType.APPLICATION_JSON).get(PageBean.class);
	
		//获取到的数据总记录数
			System.out.println("获取到的总记录数：：：："+pageBean.getTotalCount());
		//获取到的数据
			System.out.println("获取到的数据");
			List<Promotion> pageList = pageBean.getPageList();
			
			for (Promotion promotion : pageList) {
				System.out.println("我是Promotion::::"+promotion);
			}
			//将数据压栈
			ActionContext.getContext().getValueStack().push(pageBean);
		return "success";
	}
	
	
	/**
	 * 页面详情显示
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Action(value="promotion_showDetail")
	public String showDetail() throws Exception{
		//获取模板文件存放的路径
		String realPath = ServletActionContext.getServletContext().getRealPath("/freemark");
		
		//生成一个文件的名称
		String filename = promotion.getId()+".html";
		
		//拼接全部的路径
		String absolutePath = realPath+"/"+filename;
		
		//创建一个文件对象
		File htmlFile = new File(absolutePath);
		
		//去模板库里面查找该模板是否存在，如果存在直接获取，如果不存在去数据库查询，则需要结合模板去生成
		if(!htmlFile.exists()){
			
			//创建配置对象
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
			
			//将页面的模板信息封装到Configuration配置对象中
			configuration.setDirectoryForTemplateLoading(new File(
					ServletActionContext.getServletContext().getRealPath(
							"/WEB-INF/template")));
			// 获取模板对象
			Template template = configuration
					.getTemplate("promotion_detail.ftl");
			
			//访问数据库查询数据
			Promotion pro = WebClient.create("http://localhost:9999/bos_management/services/promotionService/promotion/findByID?id="+promotion.getId()).accept(MediaType.APPLICATION_JSON).get(Promotion.class);
			
			
			System.out.println("获取到查询到的数据：：：："+pro);
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("promotion", pro);
			
			
			//将查询到的数据与创建出来的模板文件合并并输出
			template.process(parameterMap, new OutputStreamWriter(
					new FileOutputStream(htmlFile), "utf-8"));
		}
		// 如果文件存在 ，直接将文件返回
				ServletActionContext.getResponse().setContentType(
						"text/html;charset=utf-8");
				FileUtils.copyFile(htmlFile, ServletActionContext.getResponse()
						.getOutputStream());
		return NONE;
	}
	
	
	//提供getter和setter方法
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	

}
