package com.nebo.nb_spider.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
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
			//
			System.out.println("解析电视剧详情页");

			parseDetail(page, rootNode);

		} else {
			System.out.println("解析电视剧列表页");
			String nextUrl = HtmlUtil.getAttributeByName(rootNode, LoadPropertyUtil.getYOUKU("nextUrlRegex"), "href");
			if (nextUrl != null) {
				// 获取到了节目页，但是还需要获取节目简介。
				if (!nextUrl.startsWith("http")) {
					nextUrl = "http:" + nextUrl;
				}
				page.addUrl(nextUrl);
			}
			// System.out.println("urlList=" + nextUrl);
			// 获取详情页url
			try {

				Object[] evaluateXPath = rootNode.evaluateXPath(LoadPropertyUtil.getYOUKU("eachDetailUrlRegex"));
				if (evaluateXPath.length > 0) {
					for (Object object : evaluateXPath) {
						TagNode tagNode = (TagNode) object;

						// 获取节目增量
						String daynumber = HtmlUtil
								.getFiledByRegex(tagNode, LoadPropertyUtil.getYOUKU("eachDetailUrlRegex_zl"),
										LoadPropertyUtil.getYOUKU("commonRegex"))
								.replace("播放", "");
						String detailUrl = HtmlUtil.getAttributeByName(tagNode,
								LoadPropertyUtil.getYOUKU("eachDetailUrlRegex_a"), "href");

						// 获取到了节目页，但是还需要获取节目简介。
						if (!StringUtils.isBlank(detailUrl)) {

							if (!detailUrl.startsWith("http")) {
								detailUrl = "http:" + detailUrl;
							}

							// System.out.println("detailUrl=" + detailUrl);
							String descUrl = HtmlUtil.getDescUrl(detailUrl,	LoadPropertyUtil.getYOUKU("eachDescUrlRegex"));
							if (!StringUtils.isBlank(descUrl)) {
								if (!descUrl.startsWith("http")) {
									descUrl = "http:" + descUrl;
								}

							} else {
								descUrl = detailUrl;
							}
							// 将daynumber放入。daynummber为 xxxx万次 xxx次 。会不会产生乱码。
							page.addUrl(descUrl + "@" + daynumber);
							// System.out.println("descUrl=" + descUrl);

						}

					}

				}
			} catch (XPatherException e) {
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
		
		// 获取评论数
		String commentNumber = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseCommentNumber"),
				LoadPropertyUtil.getYOUKU("commentRegex"));
		
		// 获取赞
		String supportNumber = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseSupportNumber"),
				LoadPropertyUtil.getYOUKU("supportRegex"));
		

		// 获取TVname
		String tvNames = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseTvName"),

				LoadPropertyUtil.getYOUKU("commonRegex"));
		page.setAllnumber(allNumber);
		page.setSupportnumber(supportNumber);
		page.setCommentnumber(commentNumber);
		page.setTvname(tvNames);
		
		//TODO:
		page.setAgainstnumber("0");
		page.setCollectnumber("0");
		 

		// 获取优酷电视剧id
		Pattern pattern = Pattern.compile(LoadPropertyUtil.getYOUKU("idRegex"), Pattern.DOTALL);
		page.setTvId("youku_" + RegexUtil.getPageInfoByRegex(page.getUrl(), pattern, 1));

	}

}
