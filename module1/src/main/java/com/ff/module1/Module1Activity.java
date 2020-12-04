package com.ff.module1;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.TimeUtils;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.thread.DefaultThreadFactory;
import com.ff.common.utils.LogUtils;
import com.ff.commonconfig.CommonPath;
import com.ff.module2.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import androidx.appcompat.app.AppCompatActivity;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;

@Route(path = CommonPath.MODULE1ACTIVITY)
public class Module1Activity extends AppCompatActivity {


    EditText editText;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module1);
        ARouter.getInstance().inject(this);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        test();
    }

    public void test() {
//        Intent intent = new Intent();
//        intent.setClassName()
//        Looper.prepare();
//        EventBus.getDefault().post(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().register(Module1Activity.this);
            }
        }).start();
        Class<?> superclass = getClass().getSuperclass();

        Executors.newFixedThreadPool(5);
        Executors.newSingleThreadExecutor();

        new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                new DefaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            }
        });




//        editText.setFocusable(true);
//        editText.setFocusableInTouchMode(true);
//        editText.findFocus();
//        editText.requestFocus();//edittext是一个EditText控件
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                EditText editText = new EditText(Module1Activity.this);
//                editText.setImeOptions(EditorInfo.IME_ACTION_SEND);
//                editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//                editText.setSingleLine(false);
//                editText.setMaxLines(40);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

//                editText.requestFocus();
            }
        }, 1000);


        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        lock.unlock();
        Handler handler = new Handler(Looper.getMainLooper());

        //内存
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void callback(String msg) {
        LogUtils.log("收到eventbus消息callback()-" + msg);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
