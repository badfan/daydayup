package com.ff.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setPluginState(WebSettings.PluginState.ON);

        webView.loadUrl("https://dev2.verify.fc18.com.cn/snsh5/issue/home/homeIndex.html");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 5000);

//        webView.loadUrl("https://www.baidu.com");
    }

    private void test() {
        try {
            Class<? extends ClassLoader> aClass = getClassLoader().getClass();
            Field pathList = aClass.getDeclaredField("pathList");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        int processors = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(processors + 1, 20, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });


        final CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            final Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("Thread", Thread.currentThread().getName() + "," + "开始");
                    countDownLatch.countDown();
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("Thread", Thread.currentThread().getName() + "," + "结束");
                }
            });
            thread1.start();
        }


        ThreadPoolExecutor executor1 = new ThreadPoolExecutor(2, 10,
                30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    }
}
