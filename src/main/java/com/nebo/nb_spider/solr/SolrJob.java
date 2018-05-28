package com.nebo.nb_spider.solr;


import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.entity.SolrPage;
import com.nebo.nb_spider.util.*;
import org.apache.commons.lang.StringUtils;

/**
 * 建立solr索引的工具类
 * Created by nebo
 *
 */
public class SolrJob {
	 private static final String SOLR_TV_INDEX = "solr_tv_index";
	static RedisUtil redis = new RedisUtil();
	
	
	public static void buildIndex(){
		String tvId = "";
		try {
			System.out.println("开始简历索引！！！");
			HbaseUtil hbaseUtil = new HbaseUtil();
			tvId = redis.poll(SOLR_TV_INDEX);
			while (!Thread.currentThread().isInterrupted()) {
				if(StringUtils.isNotBlank(tvId)){
					Page page = hbaseUtil.get(HbaseUtil.TABLE_NAME, tvId);

					if(page !=null){
						SolrPage newPage= PageUtil.trandPageToSolrPage(page);
					 	SolrUtil.addIndex(newPage);
					}
					tvId = redis.poll(SOLR_TV_INDEX);
				}else{
					System.out.println("目前没有需要索引的数据，休息一会再处理！");
					ThreadUtil.sleep(5000);
				}
			}
		} catch (Exception e) {
			redis.add(SOLR_TV_INDEX, tvId);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		buildIndex();
	}
}
