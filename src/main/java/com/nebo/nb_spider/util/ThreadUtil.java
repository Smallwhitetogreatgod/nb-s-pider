package com.nebo.nb_spider.util;
/**
 * 线程工具类
 * @author NeboFeng
 *
 */
public class ThreadUtil {
	public static void sleep(long millions){
		try {
			Thread.currentThread().sleep(millions);
		} catch (InterruptedException e) {
			 
			e.printStackTrace();
		}
	}

}
