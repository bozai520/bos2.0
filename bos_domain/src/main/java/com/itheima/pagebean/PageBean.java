package com.itheima.pagebean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.itheima.take_delivery.base.Promotion;

@XmlRootElement(name="PageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {
	
	private long totalCount;
	
	private List<T> pageList;
	
	
	public PageBean() {
		// TODO Auto-generated constructor stub
	}


	public long getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}


	public List<T> getPageList() {
		return pageList;
	}


	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	
	
	
	
}
