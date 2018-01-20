package com.nebo.nb_spider.entity;
/**
 * 存储页面信息实体类
 * @author NeboFeng
 *
 */
public class Page {
  //页面内容 
	private String content;
	//总播放量
	private String allnumber;
	//每日播放增量
	private String daynumber;
	//评论数
	private String commentnumber;
	
	//收藏数
	private String collectnumber;
	//赞
	private  String supportnumber;
	//踩
	private String againsnumber ;
	
	//电视剧名称
	private String tvname;
	
	//页面url
	private String url;
	
	//子集数据
	private String episodenumber;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAllnumber() {
		return allnumber;
	}

	public void setAllnumber(String allnumber) {
		this.allnumber = allnumber;
	}

	public String getDaynumber() {
		return daynumber;
	}

	public void setDaynumber(String daynumber) {
		this.daynumber = daynumber;
	}

	public String getCommentnumber() {
		return commentnumber;
	}

	public void setCommentnumber(String commentnumber) {
		this.commentnumber = commentnumber;
	}

	public String getCollectnumber() {
		return collectnumber;
	}

	public void setCollectnumber(String collectnumber) {
		this.collectnumber = collectnumber;
	}

	public String getSupportnumber() {
		return supportnumber;
	}

	public void setSupportnumber(String supportnumber) {
		this.supportnumber = supportnumber;
	}

	public String getAgainsnumber() {
		return againsnumber;
	}

	public void setAgainsnumber(String againsnumber) {
		this.againsnumber = againsnumber;
	}

	public String getTvname() {
		return tvname;
	}

	public void setTvname(String tvname) {
		this.tvname = tvname;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEpisodenumber() {
		return episodenumber;
	}

	public void setEpisodenumber(String episodenumber) {
		this.episodenumber = episodenumber;
	}
	
	
	
}
