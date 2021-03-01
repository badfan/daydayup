package com.ff.study;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.adapter.MainAdapter;
import com.ff.common.BaseActivity;
import com.ff.common.annotations.FFView;
import com.ff.common.annotations.FFWork;
import com.ff.common.arouter.ARouterService;
import com.ff.common.recyclerview.BaseRecycleAdapter;
import com.ff.common.widget.TitleBar;
import com.ff.commonconfig.ActivityDesc;
import com.ff.commonconfig.CommonPath;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@Route(path = "/test/mainactivity")
public class MainActivity extends BaseActivity {


    @FFView(R.id.recyclerView)
    RecyclerView recyclerView;
    @FFView(R.id.textView)
    TextView textView;
    @FFView(R.id.titleBar)
    TitleBar titleBar;
    private MainAdapter mAdapter;

    @Override
    public void setContentLayout() {
        DataBindingUtil.setContentView(this, R.layout.app_main);
        FFWork.inject(this);
        ARouter.getInstance().inject(this);
        ARouterService.getModule1Provider().sayHello("hello");
        hotFix();
    }

    @Override
    public void initView() {
        titleBar.setTitle("首页");
        mAdapter = new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter.setItemList(CommonPath.actList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnRecyclerViewItemClickListener<ActivityDesc>() {
            @Override
            public void onItemClick(View view, ActivityDesc data, int position) {
                ARouter.getInstance().build(data.path).navigation();
                String msg1 = ARouterService.getModule1Provider().sayHello(data.title);
            }
        });
    }


    public void hotFix() {
        textView.setText("我是热更新,显示了代表成功1111111");
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
