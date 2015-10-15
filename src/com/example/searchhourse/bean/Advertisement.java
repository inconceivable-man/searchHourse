package com.example.searchhourse.bean;

public class Advertisement {

	
	private String type;
	private String picurl;
	private String title;
	private String houseid;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	@Override
	public String toString() {
		return "Advertisement [title=" + title + ", houseid=" + houseid + "]";
	}
	
	
}
