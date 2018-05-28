package com.nebo.nb_spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 转换字符串工具类
 */
public class StringUtil {

    public static String transDayNumber(String dayNumber){

        String regex = "[\u4e00-\u9fa5]";

        Pattern p =Pattern.compile(regex);
        Matcher m = p.matcher(dayNumber);
        String  newDayNumber = m.replaceAll("");//将所有的中文去掉。

        if(dayNumber.contains("万")){

            if(newDayNumber.contains(",")){
                newDayNumber=newDayNumber.replace(",","")+"0000"; //将逗号替换
            }

            if(newDayNumber.contains(".")){
                newDayNumber=newDayNumber.replace(".","")+"000";
            }

            //将适当的位置加上逗号//加上逗号之前首先补充前导0方便排序。
            StringBuffer sb = new StringBuffer().append(newDayNumber);

            for(int i=newDayNumber.length()-3;i>0;i=i-3) {
                sb.insert(i,",");
            }

            newDayNumber=String.valueOf(sb);
        }else if (dayNumber.contains("亿")){

            if(newDayNumber.contains(",")){
                newDayNumber=newDayNumber.replace(",","")+"00000000"; //将逗号替换
            }

            if(newDayNumber.contains(".")){
                newDayNumber=newDayNumber.replace(".","")+"0000000";
            }

            //将适当的位置加上逗号//加上逗号之前首先补充前导0方便排序。
            StringBuffer sb = new StringBuffer().append(RegexUtil.autoGenericCode(newDayNumber,10));


            for(int i=newDayNumber.length()-3;i>0;i=i-3) {
                sb.insert(i,",");
            }

            newDayNumber=String.valueOf(sb);

        }
        return newDayNumber;
    }
}
