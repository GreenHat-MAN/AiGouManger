package com.bigdata.store.domain;

import java.util.List;

public class PageBean<T>{
	//首页 上一页，下一页  尾页 
	private int pageIndex;//第几页
	private int pageSize;//每页大小 10
	private long totalSize;//总条数 199
	private List<T> data;//每页的数据

	private int pageCount;//总页数
	private int startPage;//开始页码
	private int endPage;//结束页码
	
	
	public PageBean(int pageIndex, int pageSize, long totalSize, List<T> data) {
	
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.data = data;
		//总页数 20
		pageCount=(int)(totalSize%pageSize==0?totalSize/pageSize:totalSize/pageSize+1);
		
		//开始页码结束页码
		//正常情况
		startPage=pageIndex-4;//1
		endPage=pageIndex+5;//10
		if(pageIndex<5) {
			startPage=1;
			endPage=10;
			
		}
		// 当前页面大于总页码-5
		//16>20-5
		if(pageIndex>pageCount-5){
			startPage=pageCount-9;
			endPage=pageCount;	
		}
		//总页数小于等于10
		if(pageCount<=10){
			startPage=1;
			endPage=pageCount;	
		}
		
	 }


	public int getPageIndex() {
		return pageIndex;
	}


	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public long getTotalSize() {
		return totalSize;
	}


	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}


	public List<T> getData() {
		return data;
	}


	public void setData(List<T> data) {
		this.data = data;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public int getStartPage() {
		return startPage;
	}


	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}


	public int getEndPage() {
		return endPage;
	}


	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	
	
	
	
	
	
}
