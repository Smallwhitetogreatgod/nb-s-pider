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
	public static String getPageContent(String url){
		HttpClientBuilder  builder= HttpClients.custom();
		CloseableHttpClient client=builder.build();
		
		HttpGet request = new HttpGet(url);
		String content = null;
		try {
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
