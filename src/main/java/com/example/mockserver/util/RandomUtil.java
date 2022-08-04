package com.example.mockserver.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RandomUtil {
    public static String random(){
        String timeStr = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        String ranStr = RandomStringUtils.randomAlphabetic(17);
        return timeStr+"_"+ranStr;
    }

    public static String randomNum(int size){
        String ranStr = RandomStringUtils.randomNumeric(size);
        return ranStr;
    }

    public static String randomStr(int size){
        String ranStr = RandomStringUtils.randomAlphabetic(size);
        return ranStr;

    }
}
