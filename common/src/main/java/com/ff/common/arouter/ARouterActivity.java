package com.ff.common.arouter;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.BaseActivity;
import com.ff.common.BaseApplication;
import com.ff.common.R;
import com.ff.common.utils.LogUtil;
import com.ff.common.utils.LogUtils;
import com.ff.common.utils.StatusUtils;
import com.ff.common.widget.CustomerDialog;

import java.lang.reflect.Field;

import androidx.fragment.app.FragmentActivity;


public abstract class ARouterActivity extends BaseActivity {

    @Override
    protected void initConfigs() {
        super.initConfigs();
        ARouter.getInstance().inject(this);
    }
}
