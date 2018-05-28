package com.nebo.nb_spider.service.impl;

import java.io.IOException;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.IStoreService;
import com.nebo.nb_spider.util.HbaseUtil;
import com.nebo.nb_spider.util.RedisUtil;

/**
 * 数据存储实现类HBase
 * @author dajiangtai
 *
 */
public class HBaseStoreService implements IStoreService {
	HbaseUtil hbaseUtil = new HbaseUtil();
	RedisUtil redisUtil = new RedisUtil();
	public static void main(String[] args) {
		Page page=new Page();
		page.setTvId("youku_test");
		page.setAgainstnumber("1");
		page.setUrl("http");
		page.setAllnumber("1");
		page.setCollectnumber("1");
		page.setCommentnumber("1");
		page.setContent("11");
		page.setDaynumber("0");
		page.setSupportnumber("1");
		page.setTvname("12");
		HBaseStoreService hs =new HBaseStoreService();
		hs.store(page);
	}
	public void store(Page page) {

		String tvId = page.getTvId();
		//将索引rowkey 方法redis中
		redisUtil.add("solr_tv_index", tvId);
		try {
			System.out.println("调用put:这个在的页数为："+page.getCurrentPageNum());

			hbaseUtil.put(HbaseUtil.TABLE_NAME, tvId, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_TVNAME, page.getTvname());
			hbaseUtil.put(HbaseUtil.TABLE_NAME, tvId, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_URL, page.getUrl());
			hbaseUtil.put(HbaseUtil.TABLE_NAME, tvId, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_ALLNUMBER, page.getAllnumber());
			hbaseUtil.put(HbaseUtil.TABLE_NAME, tvId, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_COMMENTNUMBER, page.getCommentnumber());
			hbaseUtil.put(HbaseUtil.TABLE_NAME, tvId, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_SUPPORTNUMBER, page.getSupportnumber());
			hbaseUtil.put(HbaseUtil.TABLE_NAME, tvId, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_AGAINSTNUMBER, page.getAgainstnumber());
			hbaseUtil.put(HbaseUtil.TABLE_NAME, tvId, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_DAYNUMBER, page.getDaynumber());
			hbaseUtil.put(HbaseUtil.TABLE_NAME, tvId, HbaseUtil.COLUMNFAMILY_1, HbaseUtil.COLUMNFAMILY_1_COLLECTNUMBER, page.getCollectnumber());		
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	

}
