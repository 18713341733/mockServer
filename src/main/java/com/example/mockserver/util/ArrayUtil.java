package com.example.mockserver.util;

public class ArrayUtil {
    // 数组取第一个值，封装了一个方法，做了异常处理。
    public static String getFirst(String[] arr){
        if(arr.length==0 || arr == null){
            return "";
        }
        return arr[0];

    }
}
