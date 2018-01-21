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
 * 
 * @author NeboFeng
 *
 */
public class YOUKUProcessService implements IProcessService {

	public void process(Page page) {
		String content = page.getContent();
		HtmlCleaner htmlCleaner = new HtmlCleaner();
		TagNode rootNode = htmlCleaner.clean(content);

		if (page.getUrl().startsWith("http://list.youku.com/show/")) {
			// 解析电视剧详情页
			parseDetail(page, rootNode);

		} else {
			System.out.println(page.getContent());
			String nextUrl = HtmlUtil.getAttributeByName(rootNode, LoadPropertyUtil.getYOUKU("nextUrlRegex"), "href");
			if (nextUrl != null) {
				page.addUrl(nextUrl);
			}
			System.out.println("urlList=" + nextUrl);
			// 获取详情页url
			try {

				Object[] evaluateXPath = rootNode.evaluateXPath(LoadPropertyUtil.getYOUKU("eachDetailUrlRegex"));
				if (evaluateXPath.length > 0) {
					for (Object object : evaluateXPath) {
						TagNode tagNode = (TagNode) object;
						String detailUrl = tagNode.getAttributeByName("href");
						// 获取到了节目页，但是还需要获取节目简介。
						if(! detailUrl.startsWith("http")){
							detailUrl ="http:"+detailUrl;
						}
						String descUrl = HtmlUtil.getDescUrl(detailUrl, LoadPropertyUtil.getYOUKU("eachDescUrlRegex"));
						if(! descUrl.startsWith("http")){
							descUrl ="http:"+descUrl;
						}
						page.addUrl(descUrl);
						System.out.println("detailUrl=" + descUrl);

					}

				}
			} catch (XPatherException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 解析电视剧详情页
	 * 
	 * @param page
	 * @param rootNode
	 */
	public void parseDetail(Page page, TagNode rootNode) {
		// 获取播放量
		String allNumber = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseAllNumber"),
				LoadPropertyUtil.getYOUKU("allnumberRegex"));
		page.setAllnumber(allNumber);
		// 获取评论数
		String commentNumber = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseCommentNumber"),
				LoadPropertyUtil.getYOUKU("commentRegex"));
		page.setCommentnumber(commentNumber);
		// 获取赞
		String supportNumber = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseSupportNumber"),
				LoadPropertyUtil.getYOUKU("supportRegex"));
		page.setSupportnumber(supportNumber);

		// 获取优酷电视剧id
		Pattern pattern = Pattern.compile(LoadPropertyUtil.getYOUKU("idRegex"), Pattern.DOTALL);
		page.setTvId("youku_" + RegexUtil.getPageInfoByRegex(page.getUrl(), pattern, 1));

	}

}
