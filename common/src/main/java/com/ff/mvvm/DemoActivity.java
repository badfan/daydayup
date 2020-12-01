package com.ff.mvvm;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.R;
import com.ff.mvvm.base.MVVMActivity;

@Route(path = "/common/demoactivity")
public class DemoActivity extends MVVMActivity {
    @Override
    public void setContentLayout() {
        setContentView(R.layout.activity_demo);
        ARouter.getInstance().inject(this);
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
    }
}
