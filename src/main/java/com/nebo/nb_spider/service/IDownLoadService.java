package com.nebo.nb_spider.service;

import com.nebo.nb_spider.entity.Page;

/**
 * 页面下载接口
 * @author NeboFeng
 *
 */
public interface  IDownLoadService {
	public Page downLoad(String url);

}
