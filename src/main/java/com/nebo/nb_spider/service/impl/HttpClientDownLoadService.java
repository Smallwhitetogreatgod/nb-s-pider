package com.nebo.nb_spider.service.impl;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.IDownLoadService;
import com.nebo.nb_spider.util.PageDownLoadUtil;
/**
 * HttpClient页面下载实现类 
 * @author NeboFeng
 *
 */
public class HttpClientDownLoadService  implements IDownLoadService{

	public Page downLoad(String url) {
		Page page =new Page();
		page.setContent(PageDownLoadUtil.getPageContent(url));
		page.setUrl(url);
		return page;
	}

}
