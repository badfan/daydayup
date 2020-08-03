package com.ff.module1;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.module2.R;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/module1/module1activity")
public class Module1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module1);
        ARouter.getInstance().inject(this);
    }

    public void test() {
//        Intent intent = new Intent();
//        intent.setClassName()
    }
}
