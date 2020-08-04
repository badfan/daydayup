package com.ff.module1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.utils.LogUtils;
import com.ff.module2.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/module1/module1activity")
public class Module1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module1);
        ARouter.getInstance().inject(this);
        test();
    }

    public void test() {
//        Intent intent = new Intent();
//        intent.setClassName()
//        Looper.prepare();
//        EventBus.getDefault().post(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().register(Module1Activity.this);
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void callback(String msg) {
        LogUtils.log("收到eventbus消息callback()-" + msg);
    }
}
