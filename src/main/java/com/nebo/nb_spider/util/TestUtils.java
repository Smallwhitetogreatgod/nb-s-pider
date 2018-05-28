package com.nebo.nb_spider.util;

import com.nebo.nb_spider.entity.Page;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TestUtils {
    public static void main(String[] args) {
       // getNetWorkFile("");
        getPageNum();
        System.out.println("请输入一个三位整数");
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        String s= String.valueOf(p) ;
        if(s.length()!=3){
            System.out.println("整数位不符合要求！");
        }else{
            for(int i=100;i>=1;i=i/10){
                switch (i){
                    case 100:
                        System.out.println("百位是："+ p/100);
                        break;
                    case 10:
                        System.out.println("十位是："+ p%100 /10 );
                        break;
                    case 1:
                        System.out.println("个位是："+ p%10 );
                        break;


                }
            }

        }


    }

    static void  getPageNum(){
        String url = "http://list.youku.com/category/show/c_97_s_1_d_1_p_3.html?spm=a2h1n.8251845.0.0";
        Page page =new Page();
        page.setContent(PageDownLoadUtil.getPageContent(url));
        String content = page.getContent();
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        TagNode rootNode = htmlCleaner.clean(content);
        String currPageNum= HtmlUtil.getFiledByRegex(rootNode, LoadPropertyUtil.getYOUKU("currentPage"), LoadPropertyUtil.getYOUKU("commonRegex"));

        System.out.println("===================>"+currPageNum);


    }
    static  int getNetWorkFile(String urlString){
        urlString="https://img-blog.csdn.net/20180323154952670?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0p1c3RDbGltYmluZw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70";
        int length=0;
        URL url;
        try {
            url = new  URL(urlString);
            HttpURLConnection urlcon=(HttpURLConnection)url.openConnection();//打开连接
            //根据响应获取文件大小
            length=urlcon.getContentLength();
            urlcon.disconnect();//关闭连接
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println(length);
        return length;


    }
}
