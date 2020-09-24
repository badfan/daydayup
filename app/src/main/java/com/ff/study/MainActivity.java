package com.ff.study;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.annotations.FFView;
import com.ff.common.annotations.FFWork;
import com.ff.common.arouter.ARouterService;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/test/testactivity")
public class MainActivity extends AppCompatActivity {


    @FFView(R.id.textView)
    static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        FFWork.inject(this);
        ARouter.getInstance().inject(this);
        findViewById(R.id.bt_module1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ARouter.getInstance().build("/module1/module1activity").navigation();
                ARouter.getInstance().build("/module1/module1activity").navigation();
                String sayHello = ARouterService.getModule1Provider().sayHello("你好啊");
                Toast.makeText(MainActivity.this, sayHello, Toast.LENGTH_SHORT).show();
                textView.setText("module1:长度9");
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
//                ARouter.getInstance().build("/module2/module2activity").navigation();
                textView.setText("module2:长度9999999");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "postDelay", Toast.LENGTH_SHORT).show();

                    }
                }, 20000);

                handler.sendMessageDelayed(new Message(), 10000);

                new AlertDialog.Builder(MainActivity.this, R.style.dialog_style).setTitle("生命周期").setMessage("ssssssss").show();
            }
        });
        ARouterService.getModule1Provider().sayHello("hello");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("test", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("test", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("test", "onPause");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("test", "onStart");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MainActivity.this, "postDelay11", Toast.LENGTH_SHORT).show();
        }
    };

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
