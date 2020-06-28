package com.ff;

import android.app.Application;

import com.ff.screenadapter.ScreenAdapter;
import com.squareup.leakcanary.LeakCanary;

public class SoftApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        ScreenAdapter.setup(this);
    }
}
