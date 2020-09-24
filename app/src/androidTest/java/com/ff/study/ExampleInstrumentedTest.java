package com.ff.study;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.fcjr.study", appContext.getPackageName());
    }

    @Test
    public void thread() {
        final CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            final Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                   System.out.print( Thread.currentThread().getName() + "," + "开始");
                    countDownLatch.countDown();
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print( Thread.currentThread().getName() + "," + "结束");
                }
            });
            thread1.start();
        }
        assertEquals("com.fcjr.study", "com.fcjr.study" );
    }
}
