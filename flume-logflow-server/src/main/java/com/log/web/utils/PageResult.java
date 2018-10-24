package com.log.web.utils;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable{
	private static final long serialVersionUID = 955024201690343888L;
	private long total;
	private List<T> rows;
	
	public PageResult(long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	

}
