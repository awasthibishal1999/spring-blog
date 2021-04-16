package com.awasthi.bishal.Springblog.dto;

public class PostDto {
	
	private Long id;
	private String content;
	private String title;
	private String  username;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "PostDto [id=" + id + ",content=" + content + ", title=" + title + ", username=" + username + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
