package com.ff.module2;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.commonconfig.CommonPath;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = CommonPath.MODULE2ACTIVITY)
public class Module2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module2);
        ARouter.getInstance().inject(this);
    }

    public void test(){
//        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("adsa");
//        Method[] methods = clazz.getMethods();
//        methods[0].invoke(clazz, );
//        HashSet<String> set = new HashSet<>();
//        set.add("asd");
//
//        LinkedHashMap<String,String> map = new LinkedHashMap();
//        map.;
//        LinkedList<String> linkedList = new LinkedList<>();
//        linkedList.
//
//                Iterator<String> iterator = set.iterator();
//        String next = iterator.next();
//
//        ArrayList list = new ArrayList();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                lock
//            }
//        }).start();
//
//        Bitmap bitmap = new Bitmap();
//        ReferenceQueue referenceQueue = new ReferenceQueue<>();
//        WeakReference<Bitmap> reference = new WeakReference<>(bitmap, referenceQueue);
//        try {
//            Reference<Bitmap> bitmap1 = referenceQueue.remove();
//            Bitmap bitmap2 = bitmap1.get();
//            bitmap2.recycle();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}

