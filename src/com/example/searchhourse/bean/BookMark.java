package com.example.searchhourse.bean;

public class BookMark {
	
	private String tag;
	private int type;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "BookMark [tag=" + tag + ", type=" + type + "]";
	}
	

}
