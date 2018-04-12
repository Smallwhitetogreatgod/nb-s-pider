package com.nebo.nb_spider.start;

import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.nebo.nb_spider.zookeeper.ZKUtil;
import org.apache.commons.lang.StringUtils;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.IDownLoadService;
import com.nebo.nb_spider.service.IProcessService;
import com.nebo.nb_spider.service.IRepositoryService;
import com.nebo.nb_spider.service.IStoreService;
import com.nebo.nb_spider.service.impl.ConsoleStoreService;
import com.nebo.nb_spider.service.impl.HBaseStoreService;
import com.nebo.nb_spider.service.impl.HttpClientDownLoadService;
import com.nebo.nb_spider.service.impl.QueueRepositoryService;
import com.nebo.nb_spider.service.impl.RedisRepositoryService;
import com.nebo.nb_spider.service.impl.YOUKUProcessService;
import com.nebo.nb_spider.util.LoadPropertyUtil;
import com.nebo.nb_spider.util.ThreadUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import redis.clients.jedis.exceptions.JedisConnectionException;

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

    //构造函数  建立连接
    public StartDSJCount() {
        //重试策略:重试3次，每次间隔时间指数增长(有具体增长公式)
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        //zk地址
        String hosts = ZKUtil.ZOOKEEPER_HOSTS;
        CuratorFramework client = CuratorFrameworkFactory.newClient(hosts, retryPolicy);
        //建立连接
        client.start();
        try {
            //获取本地ip地址
            InetAddress localHost = InetAddress.getLocalHost();
            String ip = localHost.getHostAddress();
            //每启动一个爬虫应用，创建一个临时节点，子节点名称为当前ip
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(ZKUtil.PATH+"/"+ip);
        } catch (Exception e) {
        }

    }

	public static void main(String[] args) {
		StartDSJCount dsj = new StartDSJCount();
		dsj.setDownLoadService(new HttpClientDownLoadService());
		 dsj.setProcessService(new YOUKUProcessService());
		//dsj.setStoreService(new ConsoleStoreService());
		dsj.setStoreService(new HBaseStoreService());
		 
	  	 dsj.setRepositoryService(new RedisRepositoryService());
		//String  url="http://list.youku.com/category/show/c_97.html?spm=a2htv.20009910.nav-second.5~1~3!12~A";
	   //	String url = "http://list.youku.com/category/show/c_97.html?spm=a2htv.20009910.nav-second.5~1~3!12~A";
		// 设置起始url
		// dsj.repositoryService.addHighLevel(url);
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

			// 判断url是否为空
			if (StringUtils.isNotBlank(url)) {
				// 下载
				newFixedThreadPool.execute(new Runnable() {

					public void run() {
						//首先判断是剧集列表页。还是详情页
						String[] descUrl = url.split("@");
						System.out.println("downloadPage_url"+descUrl[0]);
						Page page = StartDSJCount.this.downloadPage(descUrl[0]);
						if(descUrl.length==4){
							page.setDaynumber(descUrl[2]);
							page.setTvname(descUrl[1]);

							page.setCurrentPageNum(descUrl[3]);
							System.out.println(descUrl[1]+"=====descUrl=============<");
						}
						
						StartDSJCount.this.processPage(page);


						List<String> urlList = page.getUrlList();
						for (String eachUrl : urlList) {
							// 列表显示页;
							if (eachUrl.startsWith("http://list.youku.com/category/show/")) {
								try {
									StartDSJCount.this.repositoryService.addHighLevel(eachUrl);
								}catch (JedisConnectionException e){
									StartDSJCount.this.repositoryService.addHighLevel(eachUrl);
								}
							} else {
								try{
								    StartDSJCount.this.repositoryService.addLowLevel(eachUrl);
								}catch(JedisConnectionException e){
									StartDSJCount.this.repositoryService.addLowLevel(eachUrl);
								}
							}
						}
						if (page.getUrl().startsWith("http://list.youku.com/show/")) {							
							// 存储数据
							StartDSJCount.this.storePageInfo(page);
						}
						//由固定的休眠时间。改为随机休眠时间
						ThreadUtil.sleep((long) (Math.random()* Long.parseLong(LoadPropertyUtil.getConfig("sleep_millions_5"))));

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
