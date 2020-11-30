package com.ff.module2;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/module2/module2homeactivity")
public class Module2HomeActivity extends AppCompatActivity {

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

