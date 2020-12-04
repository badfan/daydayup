package com.ff.web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.commonconfig.CommonPath;

@Route(path= CommonPath.WEBACTIVITY)
public class WebActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webhome_activity);
        ARouter.getInstance().inject(this);
        webView = findViewById(R.id.webView);

        webView.loadUrl("file:///android_asset/issue/html/index.html");
    }
}
