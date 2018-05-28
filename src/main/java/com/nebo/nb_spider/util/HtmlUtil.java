package com.nebo.nb_spider.util;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
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
			//System.out.println(xpath);
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
				 System.out.println();
				Pattern numberPattern = Pattern.compile(regex, Pattern.DOTALL);
				number = RegexUtil.getPageInfoByRegex(node.getText().toString(), numberPattern, 0);


			}

		} catch (XPatherException e) {

			e.printStackTrace();
		}
		return number;

	}

	public static String getDescUrl(String url, String xpath) {
		String html= null;
		try {
			html = JsUtil.getParseredHtml2(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//String content = PageDownLoadUtil.getPageContent(detailUrl);
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(html);
		String descUrl =null;
		Object[] evaluateXPath;
		try {
			evaluateXPath = rootNode.evaluateXPath(xpath);
			if (evaluateXPath.length > 0) {
				TagNode node = (TagNode) evaluateXPath[0];
				descUrl =  node.getAttributeByName("href");
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}


		return StringUtils.isBlank(descUrl)?null:descUrl.replace("//","http://");
	}

	//*[@id="bpmodule-playpage-righttitle"]/div/h2/a
	public static void main(String[] args) {

//		try {
//
//		//	String result = getDescUrl(html,LoadPropertyUtil.getYOUKU("eachDescUrlRegex"));
//	//		System.out.println(result);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		//*[@id="bpmodule-playpage-righttitle"]/div/h2/a
//        //*[@id="bpmodule-playpage-righttitle"]/div/p/em
//		//System.out.println(getDescUrl("",LoadPropertyUtil.getYOUKU("eachDescUrlRegex")));


	}
}
