package com.ff.common.arouter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.BaseActivity;


public abstract class ARouterActivity extends BaseActivity {

    @Override
    protected void initView() {
        super.initView();
        ARouter.getInstance().inject(this);
    }
}
