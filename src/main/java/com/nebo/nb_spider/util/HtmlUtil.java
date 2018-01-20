package com.nebo.nb_spider.util;

import java.util.regex.Pattern;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
/**
 * 页面解析工具类
 * @author NeboFeng
 *
 */
public class HtmlUtil {
	
	public static String getFiledByRegex(TagNode rootNode,String xpath ,String regex ){
		String number = "0";
		Object[] evaluateXPath =null;
		 try {
			evaluateXPath =  	rootNode.evaluateXPath(xpath);
			if(evaluateXPath.length>0){
				TagNode node =(TagNode)evaluateXPath[0];
			 		Pattern numberPattern = Pattern.compile(regex, Pattern.DOTALL);
			   number=RegexUtil.getPageInfoByRegex(node.getText().toString(), numberPattern, 0);
			 
				
			}	

		} catch (XPatherException e) {
		 
			e.printStackTrace();
		}
		 return number;
			
	}
	

}
