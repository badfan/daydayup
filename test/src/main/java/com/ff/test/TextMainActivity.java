package com.ff.test;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Route(path="/test/TextMainActivity")
public class TextMainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ARouter.getInstance().inject(this);
    }
}
