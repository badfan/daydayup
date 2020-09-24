package com.ff;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.BaseApplication;
import com.ff.common.screenadapter.ScreenAdapter;
import com.ff.common.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.lib.tinker.Tinker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SoftApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Beta.installTinker();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        ScreenAdapter.setup(this);
        initARouter();
        Bugly.init(this, "bdd0e6b982", false);
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
