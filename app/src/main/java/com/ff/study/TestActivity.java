package com.ff.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.arouter.ARouterService;
import com.ff.module1.Module1Activity;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/test/testactivity")
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ARouter.getInstance().inject(this);
        findViewById(R.id.bt_module1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build("/module1/module1activity").navigation();
                ARouter.getInstance().build("/module1/module1activity").navigation();
                String sayHello = ARouterService.getModule1Provider().sayHello("你好啊");
                Toast.makeText(TestActivity.this, sayHello, Toast.LENGTH_SHORT).show();

                EventBus.getDefault().post("msg1");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post("msg2");
                    }
                }).start();
            }
        });
        findViewById(R.id.bt_module2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/module2/module2activity").navigation();
            }
        });
        ARouterService.getModule1Provider().sayHello("hello");
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
