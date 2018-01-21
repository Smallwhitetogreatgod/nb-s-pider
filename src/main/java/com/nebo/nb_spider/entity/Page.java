package com.nebo.nb_spider.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储页面信息实体类
 * 
 * @author NeboFeng
 *
 */
public class Page {
	// 页面内容
	private String content;
	// 总播放量
	private String allnumber;
	// 每日播放增量
	private String daynumber;
	// 评论数
	private String commentnumber;

	// 收藏数
	private String collectnumber;
	// 赞
	private String supportnumber;
	// 踩
	private String againstnumber;

	// 电视剧名称
	private String tvname;

	// 页面url
	private String url;

	// 子集数据
	private String episodenumber;
	// 电视剧id
	private String tvId;

	// 电视剧Url 包含列表url 和详情页 url
	private List<String> urlList = new ArrayList<String>();

	public List<String> getUrlList() {
		return urlList;
	}

	public void addUrl(String url) {
		this.urlList.add(url);
	}

	public String getAgainstnumber() {
		return againstnumber;
	}

	public void setAgainstnumber(String againstnumber) {
		this.againstnumber = againstnumber;
	}

	public String getTvId() {
		return tvId;
	}

	public void setTvId(String tvId) {
		this.tvId = tvId;
	}

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
