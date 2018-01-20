package com.nebo.nb_spider.util;

import java.util.Locale;

import java.util.MissingFormatArgumentException;
import java.util.ResourceBundle;

/**
 * 加载配置文件类
 * @author NeboFeng
 *
 */
public class LoadPropertyUtil {
	//读取优酷配置文件
	public static String getYOUKU(String key){
		String value="";
		Locale locale=Locale.getDefault();
		try{
			ResourceBundle localResource=ResourceBundle.getBundle("youku", locale);
			value=localResource.getString(key);
			
		}catch(MissingFormatArgumentException e){
			value="";
		}
		return value;
	}

}
