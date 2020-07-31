package com.ff;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.BaseApplication;
import com.ff.common.screenadapter.ScreenAdapter;
import com.ff.common.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;

public class SoftApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        ScreenAdapter.setup(this);
        initARouter();
    }


    private void initARouter() {
        if (LogUtils.isLog) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
        }
        ARouter.init(this);
    }
}
