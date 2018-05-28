package com.nebo.nb_spider.util;

import java.util.Locale;

import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 加载配置文件类
 * @author NeboFeng
 *
 */
public class LoadPropertyUtil {

	// 读取邮件配置文件
	public static String getEmail(String key) {
		String value = "";
		Locale locale = Locale.getDefault();
		try {
			ResourceBundle localResource = ResourceBundle.getBundle("mail",
					locale);
			value = localResource.getString(key);
		} catch (MissingResourceException mre) {
			value = "";
		}
		return value;
	}
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
	//读取公共配置文件
 	public static String getConfig(String key){
		String value="";
		Locale locale=Locale.getDefault();
		try{
			ResourceBundle localResource=ResourceBundle.getBundle("config", locale);
			value=localResource.getString(key);
			
		}catch(MissingFormatArgumentException e){
			value="";
		}
		return value;
	}
 	public static void main(String[] args) {
		System.out.println(getConfig("threadNum"));
	}

}
