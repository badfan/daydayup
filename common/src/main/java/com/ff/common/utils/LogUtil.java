package com.ff.common.utils;


import android.util.Log;

public class LogUtil {
    private static final boolean isLog = true;
    private static final String DEFAULT_TAG = "callBack";

    public static void log(String tag, int level, String msg, Throwable tr) {
        if (isLog) {

            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.i(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            switch (level) {
                case Log.VERBOSE:
                    if (tr == null) {
                        Log.v(tag, msg);
                    } else {
                        Log.v(tag, msg, tr);
                    }
                    break;
                case Log.INFO:
                    if (tr == null) {
                        Log.i(tag, msg);
                    } else {
                        Log.i(tag, msg, tr);
                    }
                    break;
                case Log.DEBUG:
                    if (tr == null) {
                        Log.d(tag, msg);
                    } else {
                        Log.d(tag, msg, tr);
                    }
                    break;
                case Log.WARN:
                    if (tr == null) {
                        Log.w(tag, msg);
                    } else {
                        Log.w(tag, msg, tr);
                    }
                    break;
                case Log.ERROR:
                    if (tr == null) {
                        Log.e(tag, msg, tr);
                    } else {
                        Log.e(tag, msg, tr);
                    }

                    break;
            }
        }

    }

    public static void log(String tag, int level, String msg) {
        if (isLog) {
            log(tag, level, msg, null);
        }

    }

    public static void log(String tag, String msg) {
        if (isLog) {
            log(tag, 0, msg, null);
        }

    }

    public static void log(String msg) {
        if (isLog) {
            log(DEFAULT_TAG, Log.INFO, msg, null);
        }
    }

    public static void logError(String msg) {
        if (isLog) {
            log(DEFAULT_TAG, Log.ERROR, msg, null);
        }
    }


    public static void logDebug(String msg) {
        if (isLog) {
            log(DEFAULT_TAG, Log.DEBUG, msg, null);
        }
    }


}
