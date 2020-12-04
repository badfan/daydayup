package com.ff.common.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.ff.common.utils.LogUtil;
import com.ff.common.utils.ScreenUtils;


/**
 * 通用标题栏
 * Created by hh on 2016/5/17.
 */
public class StatusBarView extends View{


    private   Context ct;

    public StatusBarView(Context context) {
        this(context, null, -1);
    }

    public StatusBarView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StatusBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ct=context;
        init();
    }

    private void init() {

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            height = ScreenUtils.getStatusHeight(ct);
            LogUtil.log("height==================" + height);
        }
        setMeasuredDimension(widthMeasureSpec, height);
    }
}
