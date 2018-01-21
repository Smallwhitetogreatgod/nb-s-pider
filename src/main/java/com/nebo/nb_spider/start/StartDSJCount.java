package com.nebo.nb_spider.start;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.IDownLoadService;
import com.nebo.nb_spider.service.IProcessService;
import com.nebo.nb_spider.service.IStoreService;
import com.nebo.nb_spider.service.impl.ConsoleStoreService;
import com.nebo.nb_spider.service.impl.HttpClientDownLoadService;
import com.nebo.nb_spider.service.impl.YOUKUProcessService;

/**
 * 爬虫执行入口
 * @author NeboFeng
 *
 */
public class StartDSJCount {
	private IDownLoadService downLoadService;
	private IProcessService processService;
	private IStoreService   storeService;
	//private ExecutorService 
	public IProcessService getProcessService() {
		return processService;
	}
	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}
	public static void main(String[] args) {
		StartDSJCount dsj=new StartDSJCount();
		dsj.setDownLoadService(new HttpClientDownLoadService());
		dsj.setProcessService(new YOUKUProcessService());
		dsj.setStoreService(new ConsoleStoreService());
		
		//String url="http://list.youku.com/show/id_zefbfbdefbfbdefbfbd68.html?spm=a2h0j.8191423.module_basic_title.5~A!2";
		String url ="http://list.youku.com/category/show/c_97.html?spm=a2htv.20009910.nav-second.5~1~3!12~A";	
		//下载页面
		Page page = dsj.downloadPage(url);
		
		dsj.processPage(page);
		//存储页面信息
		dsj.storePageInfo(page);
		
	}
	/**
	 * 下载页面
	 * @param url
	 * @return
	 */
	public Page downloadPage(String url){
		return this.downLoadService.downLoad(url);
	}
	/**
	 * 页面解析
	 * @param page
	 */
	public  void processPage(Page page){
		this.processService.process(page);
	}
	public void storePageInfo(Page page){
		this.storeService.store(page);
	}

	
	
	public IDownLoadService getDownLoadService() {
		return downLoadService;
	}
	
	public void setDownLoadService(IDownLoadService downLoadService) {
		this.downLoadService = downLoadService;
	}
	
	
	
	
	public IStoreService getStoreService() {
		return storeService;
	}
	public void setStoreService(IStoreService storeService) {
		this.storeService = storeService;
	}
	
}
