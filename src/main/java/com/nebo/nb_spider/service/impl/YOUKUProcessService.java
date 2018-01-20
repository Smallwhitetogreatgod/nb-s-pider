package com.nebo.nb_spider.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.IProcessService;
import com.nebo.nb_spider.util.HtmlUtil;
import com.nebo.nb_spider.util.LoadPropertyUtil;
import com.nebo.nb_spider.util.RegexUtil;
/**
 * 优酷解析界面实现类
 * @author NeboFeng
 *
 */
public class YOUKUProcessService implements IProcessService {
	 
 
	public void process(Page page) {
		String content=page.getContent();
		HtmlCleaner htmlCleaner=new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);
		 
  
		//获取播放量
		String allNumber=HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseAllNumber"),  LoadPropertyUtil.getYOUKU("allnumberRegex"));
 		page.setAllnumber(allNumber);
		//获取评论数
	    String commentNumber = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseCommentNumber"), LoadPropertyUtil.getYOUKU("commentRegex"));
		page.setCommentnumber(commentNumber);
	    //获取赞
	    String supportNumber = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseSupportNumber"), LoadPropertyUtil.getYOUKU("supportRegex"));
		page.setSupportnumber(supportNumber);	
//		System.out.println("播放量： "+allNumber +"\n"
//				           +"评论数："+commentNumber +"\n"
//				           +"顶："+supportNumber);
 
 
	}

}
