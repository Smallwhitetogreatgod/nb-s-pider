package com.nebo.nb_spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式匹配工具
 * @author NeboFeng
 *
 */
public class RegexUtil {
	
	public static String getPageInfoByRegex(String content,Pattern pattern ,int groupNo){
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()){
			int i = matcher.groupCount();
			System.out.println(i);
			for(int j = 0;j<=i;j++){
				System.out.println(j);
				System.out.println(matcher.group(j).trim());
			}
			return matcher.group(groupNo).trim();
		}
		return "0";
		
	}
	 

}
