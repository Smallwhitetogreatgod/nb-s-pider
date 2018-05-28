package com.nebo.nb_spider.util;

import com.nebo.nb_spider.entity.Page;
import com.nebo.nb_spider.entity.SolrPage;

/**
 * page的工具类
 */
public class PageUtil {
    public static SolrPage  trandPageToSolrPage(Page page){
        SolrPage newPage = new SolrPage();
        newPage.setAgainstnumber(page.getAgainstnumber());
        newPage.setTvname(page.getTvname());
        newPage.setAllnumber(page.getAllnumber());
        newPage.setCollectnumber(page.getCollectnumber());
        newPage.setCommentnumber(page.getCommentnumber());
        newPage.setContent(page.getContent());
        newPage.setTvId(page.getTvId());
        newPage.setSupportnumber(page.getSupportnumber());
        newPage.setEpisodenumber(page.getEpisodenumber());
        newPage.setUrl(page.getUrl());
        newPage.setDaynumber(Long.parseLong(page.getDaynumber().replace(",","")));

        return newPage;
    }
}
