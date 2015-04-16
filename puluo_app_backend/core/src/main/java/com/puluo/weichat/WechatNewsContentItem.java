package com.puluo.weichat;

public class WechatNewsContentItem {
	public String title;
	public String thumb_media_id;
	public String show_cover_pic;
	public String author;
	public String digest;
	public String content;
	public String content_source_url;
	public String url;
	public WechatNewsContentItem(String title, String thumb_media_id,
			String show_cover_pic, String author, String digest,
			String content, String content_source_url,String url) {
		super();
		this.title = title;
		this.thumb_media_id = thumb_media_id;
		this.show_cover_pic = show_cover_pic;
		this.author = author;
		this.digest = digest;
		this.content = content;
		this.content_source_url = content_source_url;
		this.url = url;
	}
	
	public WechatNewsContentItem() {}

	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public String getShow_cover_pic() {
		return show_cover_pic;
	}

	public void setShow_cover_pic(String show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	@Override
	public String toString() {
		return "WechatNewsContentItem [title=" + title + ", thumb_media_id="
				+ thumb_media_id + ", show_cover_pic=" + show_cover_pic
				+ ", author=" + author + ", digest=" + digest + ", content="
				+ content + ", content_source_url=" + content_source_url + "]";
	}
	
	
}
