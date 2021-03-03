package com.ff;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.BaseApplication;
import com.ff.common.arouter.ArouterManager;
import com.ff.common.screenadapter.ScreenAdapter;
import com.ff.common.utils.LogUtils;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import androidx.multidex.MultiDex;

public class SoftApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        Beta.installTinker();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        ScreenAdapter.setup(this);
        initARouter();
        Bugly.init(this, "bdd0e6b982", true);
    }


    private void initARouter() {
        ArouterManager.init(this);
    }
}
