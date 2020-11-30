package com.ff.common;

import android.app.Activity;
import android.app.Application;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.widget.Toast;


import com.ff.common.utils.LogUtils;
import com.ff.common.utils.StatusUtils;
import com.widget.CustomerDialog;

import java.lang.reflect.Field;

import androidx.fragment.app.FragmentActivity;


public abstract class BaseActivity extends FragmentActivity {
    protected Application softApplication;
    public boolean isAllowFullScreen;// 是否允许全屏
    public boolean isAllowOneScreen = true;// 是否允许一體化
    public boolean hasMenu;// 是否有菜单显示
    private CustomerDialog progressDialog;
    protected Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.log("onCreateStart-" + getClass().getName());
        super.onCreate(savedInstanceState);
        LogUtils.log("onCreate-" + getClass().getName());
        resources = getResources();
        softApplication = BaseApplication.instance;
//        softApplication.unDestroyActivityList.add(this);
//        NetChangeManager.newInstance(softApplication).addMinitor(this);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (isAllowFullScreen) {
            setFullScreen(true);
        } else {
            setFullScreen(false);
        }
        setStatusBar();

        setContentLayout();
//        ButterKnife.bind(this);
//        AndroidBug5497Workaround.assistActivity(this);
        initBinding();
        initView();
        LogUtils.log("onCreateEnd-" + getClass().getName());
    }




    public void setStatusBar() {
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.title_color), true);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        changeStatusBackColor();
        StatusUtils.setTransparent(this);
    }

    public void changeStatusBackColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT); //改为透明
            } catch (Exception e) {
            }
        }
    }

    /**
     * 设置布局文件
     */

    public abstract void setContentLayout();

    /**
     * 初始化binding,viewmodel
     */
    protected abstract void initBinding();

    /**
     * 实例化布局文件/组件
     */
    public abstract void initView();

    public Activity getActivity() {
        return this;
    }


    /**
     * 是否全屏和显示标题，true为全屏和无标题，false为无标题，请在setContentView()方法前调用
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }


    /**
     * 短时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param info 显示的内容
     */
    public void showToastLong(String info) {
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
    }

    /**
     * 短时间显示Toast
     * <p>
     * 显示的内容
     */
    public void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     * <p>
     * 显示的内容
     */
    public void showToastLong(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        SoftApplication.unDestroyActivityList.remove(this);
//        NetChangeManager.newInstance(softApplication).removeMinitor(this);
    }

    /**
     * 显示正在加载的进度条
     */
    public void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new CustomerDialog(this, R.style.dialog_style);
        progressDialog.setMessage("加载中...");
        try {
            progressDialog.show();
        } catch (BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new CustomerDialog(this, R.style.dialog_style);
        progressDialog.setMessage(msg);
        try {
            progressDialog.show();
        } catch (BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 隐藏正在加载的进度条
     */
    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing() == true) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


}
