package com.adxm.blog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Adxm
 * @Date: 2022/10/9
 * @Description:
 */
public class FieldFormat {

    // 驼峰转下划线命名
    public static String humpToUnderline(String str) {
        Pattern compile = Pattern.compile("[A-Z]");
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb,  "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // 下划线转驼峰命名
    public static String underlineToHump(String str) {
        str = str.toLowerCase();
        Pattern compile = Pattern.compile("_[a-z]");
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb,  matcher.group(0).toUpperCase().replace("_",""));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "-createdTime";
        System.out.println(FieldFormat.humpToUnderline(str));
    }
}
