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
			return matcher.group(groupNo).trim();
		}
		return "0";
		
	}
	public static String autoGenericCode(String code, int num) {
		String result = "";
		// 保留num的位数
       // 0 代表前面补充0
		// num 代表长度为4
		// d 代表参数为正数型
		result = String.format("%0" + num + "d", Integer.parseInt(code) + 1);

		return result;
	}


	 

}
