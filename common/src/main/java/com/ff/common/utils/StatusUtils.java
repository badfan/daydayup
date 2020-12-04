package com.ff.common.utils;

import android.app.Activity;

import com.gyf.immersionbar.ImmersionBar;



/**
 * 获得屏幕相关的辅助类
 *
 * @author zhy
 */
public class StatusUtils {

    public static void setTransparent(Activity act) {
//        StatusBarCompat.setTranslucent(act.getWindow(), true);
//        //设置为亮色,即字体图标等为深色
//        StatusBarCompat.setLightStatusBar(act.getWindow(), true);

        ImmersionBar.with(act)
//                .statusBarView(view)
                .init();
        setLightStatusBar(act,true);
    }

    public static void setLightStatusBar(Activity act, boolean isLight) {
        //设置为亮色,即字体图标等为深色
//        StatusBarCompat.setLightStatusBar(act.getWindow(), isLight);

        ImmersionBar.with(act)
                .statusBarDarkFont(isLight, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    public static void setTitleColor(Activity act) {
//        StatusBarCompat.setStatusBarColor(act, ContextCompat.getColor(act, R.color.title_color));

        ImmersionBar.with(act)
//                .barColor(R.color.title_color)
                .init();
    }

    public static void setColor(Activity act, int colorRes) {
//        StatusBarCompat.setStatusBarColor(act, ContextCompat.getColor(act, colorRes));
    }



}
