package com.ff.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ff.common.R;
import com.ff.common.utils.CommonUtil;
import com.ff.common.utils.LogUtils;

import androidx.core.content.ContextCompat;


/**
 * 通用标题栏
 * Created by hh on 2016/5/17.
 */
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    public static int MODE_BLACK = 0;
    public static int MODE_WHITE = 1;
    private Context ct;
    // Content View Elements
    public ImageView iv_back;
    public TextView tv_title;
    public TextView tv_right;
    public ImageView iv_right;
    public RelativeLayout title_body;
    public StatusBarView statusBar;

    public TitleBar(Context context) {
        this(context, null, -1);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ct = context;
        init();
    }

    private void init() {
        LogUtils.log("init");
        View view = View.inflate(ct, R.layout.f_title, this);
//        setBackgroundResource(R.drawable.titlebar);
        setBackgroundResource(R.color.white);
        bindViews(view);
        statusBar.setVisibility(View.GONE);
        //沉浸式效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            int top = ScreenUtils.getStatusHeight(ct);
//            setPadding(0, top, 0, 0);
            statusBar.setVisibility(View.VISIBLE);
        }
    }

    // End Of Content View Elements

    private void bindViews(View view) {

        statusBar = view.findViewById(R.id.statusBar);
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        tv_right = view.findViewById(R.id.tv_right);
        iv_right = view.findViewById(R.id.iv_right);
        title_body = view.findViewById(R.id.title_body);
        iv_back.setOnClickListener(this);
        tv_title.setOnClickListener(this);

        tv_right.setVisibility(View.INVISIBLE);
        iv_right.setVisibility(View.INVISIBLE);
    }


    public void setStatusBarColor(String color) {
        int parseColor = Color.TRANSPARENT;
        try {
            parseColor = Color.parseColor(color);
        } catch (Exception e) {
            LogUtils.log("颜色格式错误");
        }
        statusBar.setBackgroundColor(parseColor);
    }

    public void setStatusBarColorGradient(boolean isVertical, String colorStart, String colorEnd) {
        int colors[] = {Color.parseColor(colorStart), Color.parseColor(colorEnd)};
        GradientDrawable bg = new GradientDrawable(isVertical ? GradientDrawable.Orientation.TOP_BOTTOM : GradientDrawable.Orientation.LEFT_RIGHT, colors);
        statusBar.setBackground(bg);
    }

    public void setBodyVisibility(int visibled) {
        title_body.setVisibility(visibled);
    }

    public void updateBackground(int color) {
        setBackgroundColor(color);
        statusBar.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_back) {
            doBack();

        } else if (i == R.id.tv_right) {
        }
    }

    private void doBack() {
        if (ct instanceof Activity)
            ((Activity) ct).finish();
        CommonUtil.closeSoftKeyboard(ct, iv_back);
    }

    public void setBack(boolean isShow) {
        if (isShow)
            iv_back.setVisibility(View.VISIBLE);
        else
            iv_back.setVisibility(View.GONE);

    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setTitle(int resId) {
        tv_title.setText(ct.getResources().getString(resId));
    }

    public void setTitleRight(String title) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(title);
    }

    public void setTitleRight(int resId) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(ct.getResources().getString(resId));
    }

    public void setOnRightclickListener(OnClickListener l) {
        iv_right.setOnClickListener(l);
    }

    public void setOnLeftclickListener(OnClickListener l) {
        iv_back.setOnClickListener(l);
    }

    public void setMode(int mode) {
        if (mode == MODE_WHITE) {
            tv_title.setTextColor(ContextCompat.getColor(ct, R.color.white));
            tv_right.setTextColor(ContextCompat.getColor(ct, R.color.white));
            iv_back.setImageResource(R.drawable.back_white);
        } else {
            tv_title.setTextColor(ContextCompat.getColor(ct, R.color.black));
            tv_right.setTextColor(ContextCompat.getColor(ct, R.color.black));
            iv_back.setImageResource(R.drawable.back);
        }
    }


}
