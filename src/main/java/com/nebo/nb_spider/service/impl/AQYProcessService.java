package com.nebo.nb_spider.service.impl;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.service.IProcessService;
/**
 * 优酷解析界面实现类
 * @author NeboFeng
 *
 */
public class AQYProcessService implements IProcessService {

	public void process(Page page) {
		String content=page.getContent();
		HtmlCleaner htmlCleaner=new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);
		 
		try {                              
		Object[] evaluateXPath =  	rootNode.evaluateXPath("//*[@id=\"movie-score-show\"]/span[1]");
			if(evaluateXPath.length>0){
				TagNode node =(TagNode)evaluateXPath[0];
				System.out.println(node.getText().toString());
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
//		System.out.println(page.getContent());

	}

}
