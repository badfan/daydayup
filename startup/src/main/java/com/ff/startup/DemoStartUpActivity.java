package com.ff.startup;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import javax.sql.DataSource;

public class DemoStartUpActivity extends AppCompatActivity {


    private FFStartupView image_startup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_startup);
        image_startup = findViewById(R.id.image_startup);
        image_startup.setCountDown(3).setImgEngine(new FFStartupView.ImgEngine() {
            @Override
            public void loadImg(ImageView view, FFStartupView.ImgLoadFinished callback) {
                DemoStartUpActivity.this.loadImg(view, callback);
            }
        }).setCallBack(new FFStartupView.StartupOverCallback() {
            @Override
            public void callBack() {
//            LogUtils.log("跳转mainactivity");
                DemoStartUpActivity.this.turntoMain();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (image_startup.isOver()) {
            turntoMain();
        }
    }


    private void turntoMain() {
//        UIManager.turnToAct(getActivity(), MainActivity.class);
        finish();
    }

    private void loadImg(ImageView imageView, FFStartupView.ImgLoadFinished callback) {
//        Glide.with(getActivity()).load(STARTUP_URL).listener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
////                turntoMain();
//                loadImg(imageView, callback);
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                callback.loadImgFinished();
//                return false;
//            }
//        }).dontAnimate().into(imageView);

    }

}
