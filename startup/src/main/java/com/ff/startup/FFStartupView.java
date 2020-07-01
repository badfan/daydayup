package com.ff.startup;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ff.common.utils.LogUtils;


public class FFStartupView extends LinearLayout {

    private Context ct;
    private View view;
    Handler handler = new Handler();
    final int TIME_MAX = 3;
    int countDown = TIME_MAX;
    private StartupOverCallback mCallBack;
    private TextView tv_time;
    private ImageView image;
    private boolean isOver = false;

    public FFStartupView(Context context) {
        this(context, null, -1);
    }

    public FFStartupView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FFStartupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.ct = context;
        view = LayoutInflater.from(context).inflate(R.layout.startup, this, true);
        image = view.findViewById(R.id.image);
        tv_time = view.findViewById(R.id.tv_time);
        tv_time.setVisibility(View.GONE);
        tv_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FFStartupView.this.timmerOver();
            }
        });
    }

    private void startTimmer() {
        handler.postDelayed(timmerTask, 1000);
    }

    public void stopTimmer() {
        if (handler != null && timmerTask != null) {
            handler.removeCallbacks(timmerTask);
        }
    }

    Runnable timmerTask = new Runnable() {
        @Override
        public void run() {
            countDown--;
            if (countDown <= 0) {
                timmerOver();
                return;
            }
            updateTime();
            startTimmer();
        }
    };

    private void updateTime() {
        LogUtils.log("countDown=" + countDown);
        tv_time.setText("跳过 " + countDown);
    }

    private void timmerOver() {
        LogUtils.log("倒计时结束");
        isOver = true;
        if (mCallBack != null) {
            mCallBack.callBack();
        }
        stopTimmer();
    }


    public boolean isOver() {
        return isOver;
    }

    public FFStartupView setCountDown(int time) {
        countDown = time;
        updateTime();
        return this;
    }

    public FFStartupView setImgEngine(ImgEngine engine) {
        if (engine != null) {
            engine.loadImg(image, isNeedTimeCount -> {
                LogUtils.log("开始倒计时");
                if (isNeedTimeCount) {
                    tv_time.setVisibility(View.VISIBLE);
                }
                startTimmer();
            });
        }
        return this;
    }


    public void setCallBack(StartupOverCallback callBack) {
        mCallBack = callBack;
    }

    public interface StartupOverCallback {
        void callBack();
    }

    public interface ImgEngine {
        void loadImg(ImageView view, ImgLoadFinished callback);
    }

    public interface ImgLoadFinished {
        void loadImgFinished(boolean isNeedTimeCount);
    }
}
