package com.example.searchhourse.bean;

public class News {

	private String id;
	private String type;
	private String title;
	private String summary;
	private String thumbnail;
	private String groupthumbnail;
	private String commentcount;
	private String imagecount;
	private String commentid;

	public static final int TYPE_small_image = 0;
	public static final int TYPE_big_image = 1;

	public static int getTypeCount() { // 返回数据类型的个数
		return 2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getGroupthumbnail() {
		return groupthumbnail;
	}

	public void setGroupthumbnail(String groupthumbnail) {
		this.groupthumbnail = groupthumbnail;
	}

	public String getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}

	public String getImagecount() {
		return imagecount;
	}

	public void setImagecount(String imagecount) {
		this.imagecount = imagecount;
	}

	public String getCommentid() {
		return commentid;
	}

	public void setCommentid(String commentid) {
		this.commentid = commentid;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + "]";
	}

}
