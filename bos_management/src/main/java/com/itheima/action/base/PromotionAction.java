package com.itheima.action.base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.itheima.domain.base.Promotion;
import com.itheima.service.base.PromotionService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends ActionSupport implements ModelDriven<Promotion>{

	
	//注入一个Service的对象
	@Autowired
	private PromotionService ps;
	
	//模型驱动
	private Promotion promotion = new Promotion();
	
	@Override
	public Promotion getModel() {
		// TODO Auto-generated method stub
		return promotion;
	}
	
	//上传文件的属性
	private File titleImgFile;
	private String titleImgFileFileName;
	
	
	/**
	 * 添加一个宣传活动
	 * @return
	 * @throws IOException 
	 */
	@Action(value="promotion_add",results={@Result(name="success",type="redirect",location="./pages/take_delivery/promotion.html")})
	public String addPormotion() throws IOException{
		
		String realPath = ServletActionContext.getServletContext().getRealPath("/upload");
		System.out.println("我是绝对路径：：：："+realPath);
		String contextPath = ServletActionContext.getRequest().getContextPath()+"/upload/";		
		System.out.println("我是相对路径"+contextPath);
		
		//生成一个随机的图片名称
		//生成随机的文件名称
		String uuid = UUID.randomUUID().toString();
		System.out.println("titleImgFileFileName"+titleImgFileFileName);
		String ext = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
		String filename = uuid+ext;
		//将文件上传
		File file = new File(realPath, filename);
		
		//文件拷贝
		FileUtils.copyFile(titleImgFile, file);
		
		//保存相对路径到数据库中
		
		promotion.setTitleImg(contextPath+filename);
		ps.addPromotion(promotion);
		return "success";
	}
	/**
	 * 宣传活动的全查询
	 * @return
	 */
	private int page;
	private int rows;
	
	@Action(value="promotion_findAll",results={@Result(name="success",type="json")})
	public String findPromotion(){
		System.out.println("PAGE:::::"+page);
		System.out.println("rows::::"+rows);
//		List<Promotion> list = ps.findAllPromotion();
		Page<Promotion> pg = ps.findAllPromotion(page, rows);
		//封装数据按照EasyUi的分页数据要求
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", pg.getTotalElements());
		map.put("rows", pg.getContent());
		//将数据进行压栈操作
		ActionContext.getContext().getValueStack().push(map);
		return "success";
	}


	
	//getter和setter
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getRows() {
		return rows;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}



	public File getTitleImgFile() {
		return titleImgFile;
	}



	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}



	public String getTitleImgFileFileName() {
		return titleImgFileFileName;
	}



	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}



	
	
	
	
	
}
