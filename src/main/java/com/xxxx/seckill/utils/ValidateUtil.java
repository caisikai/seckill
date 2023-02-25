package com.xxxx.seckill.utils;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
手机号码校验
 */
public class ValidateUtil {
    private static final Pattern mobile_pattern = Pattern.compile("[1]([3-9])[0-9]{9}$");

    public static boolean isMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher mathcer = mobile_pattern.matcher(mobile);
        return mathcer.matches();
    }
}
