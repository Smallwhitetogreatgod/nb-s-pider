package com.nebo.nb_spider.service;
/**
 * 存储url仓库
 * @author NeboFeng
 *
 */
public interface IRepositoryService {
	public String poll();
	public void  addHighLevel(String url);
	public void  addLowLevel(String url);

}
