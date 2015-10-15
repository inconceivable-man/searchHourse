package com.example.searchhourse.bean;

public class NewsComment {

	private String id;
	private String time;
	private String timestamp;
	private String content;
	private String nick;
	private String head;
	private String region;
	private String isreply;
	private String replynick;
	private String replycontent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getIsreply() {
		return isreply;
	}
	public void setIsreply(String isreply) {
		this.isreply = isreply;
	}
	public String getReplynick() {
		return replynick;
	}
	public void setReplynick(String replynick) {
		this.replynick = replynick;
	}
	public String getReplycontent() {
		return replycontent;
	}
	public void setReplycontent(String replycontent) {
		this.replycontent = replycontent;
	}
	@Override
	public String toString() {
		return "NewsComment [content=" + content + ", nick=" + nick + "]";
	}
	
	
	
}
