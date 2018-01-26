package com.nebo.nb_spider.start;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.IDownLoadService;
import com.nebo.nb_spider.service.IProcessService;
import com.nebo.nb_spider.service.IRepositoryService;
import com.nebo.nb_spider.service.IStoreService;
import com.nebo.nb_spider.service.impl.ConsoleStoreService;
import com.nebo.nb_spider.service.impl.HttpClientDownLoadService;
import com.nebo.nb_spider.service.impl.QueueRepositoryService;
import com.nebo.nb_spider.service.impl.RedisRepositoryService;
import com.nebo.nb_spider.service.impl.YOUKUProcessService;
import com.nebo.nb_spider.util.LoadPropertyUtil;
import com.nebo.nb_spider.util.ThreadUtil;

/**
 * 爬虫执行入口
 * 
 * @author NeboFeng
 *
 */
public class StartDSJCount {
	private IDownLoadService downLoadService;
	private IProcessService processService;
	private IStoreService storeService;

	// private Queue<String> urlQueue = new ConcurrentLinkedQueue<String>();
	private IRepositoryService repositoryService;
	// 固定线程池
	private ExecutorService newFixedThreadPool = Executors
			.newFixedThreadPool(Integer.parseInt(LoadPropertyUtil.getConfig("threadNum")));

	public static void main(String[] args) {
		StartDSJCount dsj = new StartDSJCount();
		dsj.setDownLoadService(new HttpClientDownLoadService());
		dsj.setProcessService(new YOUKUProcessService());
		dsj.setStoreService(new ConsoleStoreService());
		dsj.setRepositoryService(new RedisRepositoryService());

		// String
		// url="http://list.youku.com/show/id_zefbfbdefbfbdefbfbd68.html?spm=a2h0j.8191423.module_basic_title.5~A!2";
		//String url = "http://list.youku.com/category/show/c_97.html?spm=a2htv.20009910.nav-second.5~1~3!12~A";

		// 设置起始url
		//dsj.repositoryService.addHighLevel(url);
		// 开启爬虫
		dsj.startSpider();

	}

	/**
	 * 开启一个爬虫入口
	 */
	public void startSpider() {
		while (true) {
			// 从队列中提取需要解析的url
			final String url = repositoryService.poll();
			System.out.println(url+"==================<");
			// 判断url是否为空
			if (StringUtils.isNotBlank(url)) {
				// 下载
				newFixedThreadPool.execute(new Runnable() {

					public void run() {
						String[] descUrl = url.split("@");
						Page page = StartDSJCount.this.downloadPage(descUrl[0]);
						if(descUrl.length==2){
							page.setDaynumber(descUrl[1]);
							
						}
						System.out.println(descUrl+"=====descUrl=============<");
						StartDSJCount.this.processPage(page);
						List<String> urlList = page.getUrlList();
						for (String eachUrl : urlList) {
							// this.urlQueue.add(eachUrl);
							if (eachUrl.startsWith("http://list.youku.com/category/show/")) {
								StartDSJCount.this.repositoryService.addHighLevel(eachUrl);
							} else {
								System.out.println("before add"+eachUrl);
								StartDSJCount.this.repositoryService.addLowLevel(eachUrl);
								System.out.println("after  add"+eachUrl);
							}
						}
						if (page.getUrl().startsWith("http://list.youku.com/show/")) {
							
							// 存储数据
							StartDSJCount.this.storePageInfo(page);
						}
						ThreadUtil.sleep(Long.parseLong(LoadPropertyUtil.getConfig("sleep_millions")));

					}
				});

			} else {
				System.out.println("队列中的电视剧url解析完毕！ 请等待 ");
				ThreadUtil.sleep(Long.parseLong(LoadPropertyUtil.getConfig("sleep_millions_5")));
			}

		}

	}

	/**
	 * 下载页面
	 * 
	 * @param url
	 * @return
	 */
	public Page downloadPage(String url) {
		return this.downLoadService.downLoad(url);
	}

	/**
	 * 页面解析
	 * 
	 * @param page
	 */
	public void processPage(Page page) {
		this.processService.process(page);
	}

	public void storePageInfo(Page page) {
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
