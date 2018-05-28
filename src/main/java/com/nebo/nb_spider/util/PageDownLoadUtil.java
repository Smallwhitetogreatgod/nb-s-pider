package com.nebo.nb_spider.util;

import java.io.IOException;


import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.impl.HttpClientDownLoadService;

/*
 * 页面下载工具类
 * @author nebo
 * create by 
 */
public class PageDownLoadUtil {
	private final static String USER_AGENT =
			"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.61 Safari/537.36";

	public static String getPageContent(String url){
		HttpClientBuilder  builder= HttpClients.custom();
		CloseableHttpClient client=builder.build();
		/*
		设置动态ip的代码
		String ip_port=redisUtil.getSet("");
		if(StringUtils.isNotBlank(ip_port)){
			String[] arr=ip_port.split(":");
			String proxy_ip=arr[0];
			int proxy_port=Integer.parseInt(arr[1]);
			//设置代理
			HttpHost proxy=new HttpHost(proxy_ip,proxy_port);
			client=builder.setProxy().build();
			//其余代码不变
			catch httpconnectionexception 如果发现连接异常。就说明ip不可用。从redis中移出
		}

		 */
		HttpGet request = new HttpGet(url);
		String content = null;
		try {
			request.setHeader("User-Agent",USER_AGENT);
		 
		
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return content ;
	}
	
	
	public static void main(String[] args) {
		String url="http://list.youku.com/show/id_zefbfbdefbfbdefbfbd68.html?spm=a2h0j.8191423.module_basic_title.5~A!2";
		HttpClientDownLoadService down =  new HttpClientDownLoadService();
		Page page=down.downLoad(url);
		
		System.out.println(page.getContent());
		
	}

}
