package com.nebo.nb_spider.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nebo.nb_spider.util.StringUtil;
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
			String currPageNum= HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("currentPage"), LoadPropertyUtil.getYOUKU("commonRegex"));
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
                   //节目增量与tvname要在列表页获取。以为详情页-》简介页的信息太乱，获取起来更难；
						// 获取节目增量
						String daynumber = HtmlUtil
								.getFiledByRegex(tagNode, LoadPropertyUtil.getYOUKU("eachDetailUrlRegex_zl"),
										LoadPropertyUtil.getYOUKU("commonRegex"))
								        .replace("播放", "");

						//节目增量准确的数字。而是 xxxx万次 ，1.7万次播放
						//1.正则截取数字部分， 2.判断有无小数点  3. 判断后面的单位
						//数据格式 ： xxxx， . 亿 ， 千万 ，百万  万次播放 。  或者直接是 xxxxx次播放。 现在都是 xxx万次播放

						String newDayNumber = StringUtil.transDayNumber(daynumber) ;


						//获取tvname
						String tvName  = HtmlUtil
								.getFiledByRegex(tagNode, LoadPropertyUtil.getYOUKU("eachDetailUrlRegex_tvname"),
										LoadPropertyUtil.getYOUKU("commonRegex")) ;

						String detailUrl = HtmlUtil.getAttributeByName(tagNode,
								LoadPropertyUtil.getYOUKU("eachDetailUrlRegex_a"), "href");

						// 获取到了节目页，但是还需要获取节目简介。赞等信息在节目简介页面
						if (!StringUtils.isBlank(detailUrl)) {

							if (!detailUrl.startsWith("http")) {
								detailUrl = "http:" + detailUrl;
							}
							//根据列表页打开之后是直接播放的页面。需要点击进入信息展示页 找到 收藏。赞等信息 。
							System.out.println("detailUrl=" + detailUrl);
							String descUrl = HtmlUtil.getDescUrl(detailUrl,	LoadPropertyUtil.getYOUKU("eachDescUrlRegex"));
							if (!StringUtils.isBlank(descUrl)) {
								if (!descUrl.startsWith("http")) {
									descUrl = "http:" + descUrl;
								}

							} else {
								descUrl = detailUrl;
							}
							// 将daynumber放入。daynummber为 xxxx万次 xxx次 。会不会产生乱码。
							page.addUrl(descUrl + "@"+tvName+"@"+newDayNumber+"@"+currPageNum);
							page.setCurrentPageNum(currPageNum);
						    System.out.println("descUrl= 加上tvname与播放增量" + descUrl + "@"+tvName+"@"+newDayNumber+"@"+currPageNum);

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
		

		// 获取TVname 更改为在列表页面获取。
		//String tvNames = HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("parseTvName"),LoadPropertyUtil.getYOUKU("commonRegex"));
		page.setAllnumber(allNumber);
		page.setSupportnumber(supportNumber);
		page.setCommentnumber(commentNumber);
		//page.setTvname(tvNames);
		
		//TODO:
		page.setAgainstnumber("0");
		page.setCollectnumber("0");
		 

		// 获取优酷电视剧id
		Pattern pattern = Pattern.compile(LoadPropertyUtil.getYOUKU("idRegex"), Pattern.DOTALL);
		page.setTvId("youku_" + RegexUtil.getPageInfoByRegex(page.getUrl(), pattern, 1));

	}

}
