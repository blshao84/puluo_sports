package com.puluo.entity.impl;

import java.sql.Date;
import java.sql.Time;

import com.puluo.entity.PuluoTimelinePost;


public class PuluoPostImpl implements PuluoTimelinePost {

	private String idpost;
	private String textcontent;
	private String imgcontent;
	private Date post_date;
	private Time post_time;
	private int type;
	private String[] post_commentid;
	
	
	public PuluoPostImpl() {}
	
	public PuluoPostImpl(String idpost, String textcontent, String imgcontent,
			Date post_date, Time post_time, int type, String[] post_commentid) {
		this.idpost = idpost;
		this.textcontent = textcontent;
		this.imgcontent = imgcontent;
		this.post_date = post_date;
		this.post_time = post_time;
		this.type = type;
		this.post_commentid = post_commentid;
	}
	
	@Override
	public String idPost() {
		// TODO Auto-generated method stub
		return idpost;
	}
	
	@Override
	public String textContent() {
		// TODO Auto-generated method stub
		return textcontent;
	}

	@Override
	public String imgContent() {
		// TODO Auto-generated method stub
		return imgcontent;
	}

	@Override
	public Date postDate() {
		// TODO Auto-generated method stub
		return post_date;
	}

	@Override
	public Time postTime() {
		// TODO Auto-generated method stub
		return post_time;
	}

	@Override
	public int type() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String[] postCommentId() {
		// TODO Auto-generated method stub
		return post_commentid;
	}

	protected String getIdPost() {
		return idpost;
	}

	public void setIdPost(String idpost) {
		this.idpost = idpost;
	}

	protected String getTextContent() {
		return textcontent;
	}

	public void setTextContent(String textcontent) {
		this.textcontent = textcontent;
	}

	protected String getImgContent() {
		return imgcontent;
	}

	public void setImgContent(String imgcontent) {
		this.imgcontent = imgcontent;
	}

	protected Date getPostDate() {
		return post_date;
	}

	public void setPostDate(Date post_date) {
		this.post_date = post_date;
	}

	protected Time getPostTime() {
		return post_time;
	}

	public void setPostTime(Time post_time) {
		this.post_time = post_time;
	}

	protected int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	protected String[] getPostCommentId() {
		return post_commentid;
	}

	public void setPostCommentId(String[] post_commentid) {
		this.post_commentid = post_commentid;
	}
}
