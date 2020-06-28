package com.ff.common.arouter;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

public class ArouterManager {

    //是否开启调试
    private static boolean isDebug =true;

    public static void init(Application application){
        //必须在初始化之前写入这两行
        if (isDebug) {
            //打印日志
            ARouter.openLog();
            //开始调试
            ARouter.openDebug();
        }
        //ARouter的实例化
        ARouter.init(application);
    }
}
