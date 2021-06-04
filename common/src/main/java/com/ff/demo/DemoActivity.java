package com.ff.demo;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.BR;
import com.ff.common.R;
import com.ff.common.databinding.ActivityDemoBinding;
import com.ff.commonconfig.CommonPath;
import com.ff.mvvm.base.MVVMActivity;

import androidx.lifecycle.Observer;

@Route(path = CommonPath.DEMOACTIVITY)
public class DemoActivity extends MVVMActivity<ActivityDemoBinding, DemoViewModel> {

    @Override
    protected int creatLayoutId() {
        return R.layout.activity_demo;
    }

    @Override
    protected int getViewModelId() {
        return BR.model;
    }

    @Override
    public void initData() {

    }


    @Override
    protected void initViewObserver() {
        getViewModel().getUIOB().getLoadWebEvent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });
    }

}
