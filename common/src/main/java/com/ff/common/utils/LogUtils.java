package com.ff.common.utils;

import android.util.Log;

public class LogUtils {

    static String TAG = "callback";

    private static boolean isLog = true;
    private static int LOG_MAXLENGTH = 50000;


//    public static void log(String tag, String msg) {
//        if (isLog) {
//            int strLength = msg.length();
//            int start = 0;
//            int end = LOG_MAXLENGTH;
//            for (int i = 0; i < 100; i++) {
//                if (strLength > end) {
//                    Log.e(tag + i, msg.substring(start, end));
//                    start = end;
//                    end = end + LOG_MAXLENGTH;
//                } else {
//                    Log.e(tag + i, msg.substring(start, strLength));
//                    break;
//                }
//            }
//        }
//    }

    public static void log(String msg) {
        if (isLog) {
            log(TAG, msg);
        }
    }
    public static void log(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.e(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.e(tag, msg);
    }
    public static void info(String msg) {
        if (isLog) {
            log(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isLog) {
            d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.d(tag, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.d(tag, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
}
