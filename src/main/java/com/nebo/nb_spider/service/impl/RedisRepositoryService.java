package com.nebo.nb_spider.service.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.StringUtils;

import com.nebo.nb_spider.service.IRepositoryService;
import com.nebo.nb_spider.util.RedisUtil;
/**
 * url仓库实现类Redis
 * @author NeboFeng
 *
 */
public class RedisRepositoryService implements IRepositoryService {
	RedisUtil redisUtil=new RedisUtil();
	public String poll() {
		String url= redisUtil.poll(RedisUtil.highkey);
		if(StringUtils.isBlank(url)){
			url= redisUtil.poll(RedisUtil.lowkey);
		}
		return url;
	}
	public void addLowLevel(String url) {
		System.out.println(url+"addlowkey==================");
		redisUtil.add(RedisUtil.lowkey,url);
	}

	public void addHighLevel(String url) {
		System.out.println(url+"addHighkey==================");
		redisUtil.add(RedisUtil.highkey,url);

	}


}
