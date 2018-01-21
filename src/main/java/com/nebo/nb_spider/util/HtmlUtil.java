package com.nebo.nb_spider.util;

import java.util.regex.Pattern;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

/**
 * 页面解析工具类
 * 
 * @author NeboFeng
 *
 */
public class HtmlUtil {
	/**
	 * 获取标签属性值
	 * 
	 * @param rootNode
	 * @param xpath
	 * @param att
	 * @return
	 */

	public static String getAttributeByName(TagNode rootNode, String xpath, String att) {
		String result = null;
		Object[] evaluateXPath;
		try {
			evaluateXPath = rootNode.evaluateXPath(xpath);
			System.out.println(xpath);
			if (evaluateXPath.length > 0) {
				TagNode node = (TagNode) evaluateXPath[0];
				result = node.getAttributeByName(att);
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 通过 xpah regex 获取字段
	 * 
	 * @param rootNode
	 * @param xpath
	 * @param regex
	 * @return
	 */
	public static String getFiledByRegex(TagNode rootNode, String xpath, String regex) {
		String number = "0";
		Object[] evaluateXPath = null;
		try {
			evaluateXPath = rootNode.evaluateXPath(xpath);
			if (evaluateXPath.length > 0) {
				TagNode node = (TagNode) evaluateXPath[0];
				Pattern numberPattern = Pattern.compile(regex, Pattern.DOTALL);
				number = RegexUtil.getPageInfoByRegex(node.getText().toString(), numberPattern, 0);

			}

		} catch (XPatherException e) {

			e.printStackTrace();
		}
		return number;

	}

	public static String getDescUrl(String detailUrl, String regex) {
		String descUrl = null;
		String content = PageDownLoadUtil.getPageContent(detailUrl);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);

		Object[] evaluateXPath;
		try {
			evaluateXPath = rootNode.evaluateXPath(regex);
			if (evaluateXPath.length > 0) {
				TagNode desc = (TagNode) evaluateXPath[0];
				descUrl = desc.getAttributeByName("href");

			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return descUrl;
	}

}
