package com.nebo.nb_spider.start;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.StringUtils;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.IDownLoadService;
import com.nebo.nb_spider.service.IProcessService;
import com.nebo.nb_spider.service.IRepositoryService;
import com.nebo.nb_spider.service.IStoreService;
import com.nebo.nb_spider.service.impl.ConsoleStoreService;
import com.nebo.nb_spider.service.impl.HttpClientDownLoadService;
import com.nebo.nb_spider.service.impl.QueueRepositoryService;
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
	 
	//private Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
	private IRepositoryService repositoryService;
	
	public static void main(String[] args) {
		StartDSJCount dsj=new StartDSJCount();
		dsj.setDownLoadService(new HttpClientDownLoadService());
		dsj.setProcessService(new YOUKUProcessService());
		dsj.setStoreService(new ConsoleStoreService());
		dsj.setRepositoryService(new QueueRepositoryService());
		
		//String url="http://list.youku.com/show/id_zefbfbdefbfbdefbfbd68.html?spm=a2h0j.8191423.module_basic_title.5~A!2";
		String url ="http://list.youku.com/category/show/c_97.html?spm=a2htv.20009910.nav-second.5~1~3!12~A";	
	 
		//设置起始url 
		dsj.repositoryService.addHighLevel(url); 
		//开启爬虫
		dsj.startSpider();
		
	}
	
	/**
	 * 开启一个爬虫入口
	 */
	public void startSpider(){
		while(true){
			//从队列中提取需要解析的url
			String url=repositoryService.poll();
			//判断url是否为空
			if(StringUtils.isNotBlank(url)){
				//下载
				Page page=this.downloadPage(url);
				this.processPage(page);
				List<String> urlList=page.getUrlList();
				for(String eachUrl: urlList){
					//this.urlQueue.add(eachUrl);
					if(eachUrl.startsWith("http://list.youku.com/category/show/")){
						this.repositoryService.addHighLevel(eachUrl);
					}else{
						this.repositoryService.addLowLevel(eachUrl);
					}
				}
 			 if(page.getUrl().startsWith("http://list.youku.com/show/")){
				 //存储数据
 				 this.storePageInfo(page);
			 }	
				
			}else{
				System.out.println("队列中的电视剧url解析完毕！ 请等待 ");
			}

		}
		
		
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
	
	public IProcessService getProcessService() {
		return processService;
	}
	public void setProcessService(IProcessService processService) {
		this.processService = processService;
	}

	public IRepositoryService getRepositoryService() {
		return repositoryService;
	}

	public void setRepositoryService(IRepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
}
