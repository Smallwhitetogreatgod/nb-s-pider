package com.nebo.nb_spider.service.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.StringUtils;

import com.nebo.nb_spider.service.IRepositoryService;
/**
 * url仓库实现类 Queue
 * @author NeboFeng
 *
 */
public class QueueRepositoryService implements IRepositoryService {
	//高优先级队列
	private Queue<String> highLevelQueue = new ConcurrentLinkedQueue<String>();
	//低优先级队列
	private Queue<String> lowLevelQueue = new ConcurrentLinkedQueue<String>();
	public String poll() {
		String url= highLevelQueue.poll();
		if(StringUtils.isBlank(url)){
			url= lowLevelQueue.poll();
		}
		return url;
	}

	public void addHighLevel(String url) {
		highLevelQueue.add(url);

	}

	public void addLowLevel(String url) {
		lowLevelQueue.add(url);
	}
 
}
