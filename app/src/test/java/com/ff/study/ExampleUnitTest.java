package com.ff.study;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void thread() {
        final CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            final Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.print( Thread.currentThread().getName()  + "开始"+ ",");
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
    }
}