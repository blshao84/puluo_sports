package com.puluo.entity.impl;

import java.sql.Date;
import java.sql.Time;

import com.puluo.entity.PuluoPostComment;


public class PuluoPostCommentImpl implements PuluoPostComment {

	private String idcomment;
	private Date comment_date;
	private Time comment_time;
	private String content;
	private String idpost;
	private String from;
	private String to;
	

	public PuluoPostCommentImpl() {}
	
	public PuluoPostCommentImpl(String idcomment, Date comment_date,
			Time comment_time, String content, String idpost, String from,
			String to) {
		this.idcomment = idcomment;
		this.comment_date = comment_date;
		this.comment_time = comment_time;
		this.content = content;
		this.idpost = idpost;
		this.from = from;
		this.to = to;
	}
	
	@Override
	public String idComment() {
		// TODO Auto-generated method stub
		return idcomment;
	}

	@Override
	public Date commentDate() {
		// TODO Auto-generated method stub
		return comment_date;
	}

	@Override
	public Time commentTime() {
		// TODO Auto-generated method stub
		return comment_time;
	}

	@Override
	public String content() {
		// TODO Auto-generated method stub
		return content;
	}

	@Override
	public String idPost() {
		// TODO Auto-generated method stub
		return idpost;
	}

	@Override
	public String from() {
		// TODO Auto-generated method stub
		return from;
	}

	@Override
	public String to() {
		// TODO Auto-generated method stub
		return to;
	}

	protected String getIdComment() {
		return idcomment;
	}

	public void setIdComment(String idcomment) {
		this.idcomment = idcomment;
	}

	protected Date getCommentDate() {
		return comment_date;
	}

	public void setCommentDate(Date comment_date) {
		this.comment_date = comment_date;
	}

	protected Time getCommentTime() {
		return comment_time;
	}

	public void setCommentTime(Time comment_time) {
		this.comment_time = comment_time;
	}

	protected String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	protected String getIdPost() {
		return idpost;
	}

	public void setIdPost(String idpost) {
		this.idpost = idpost;
	}

	protected String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	protected String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}