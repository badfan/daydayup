package com.ff.demo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.R;
import com.ff.common.databinding.ActivityDemoBinding;
import com.ff.common.utils.LogUtil;
import com.ff.mvvm.base.MVVMActivity;

import androidx.lifecycle.Observer;

@Route(path = "/common/demoactivity")
public class DemoActivity extends MVVMActivity<ActivityDemoBinding, DemoViewModel> {

    @Override
    protected int creatLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    public void initView() {
        getViewModel().showDialog();
        getMainHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getViewModel().dismissDialog();
            }
        }, 2000);

        getViewModel().doTest();

        getViewModel().getUIOB().getLoadWebEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast("");
            }
        });
    }


}
