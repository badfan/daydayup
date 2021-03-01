package com.ff.commonconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonPath {

    public static List<ActivityDesc> actList = new ArrayList<>();
    public static final String MAINACTIVITY = "/app/mainactivity";
    public static final String WEBACTIVITY = "/web/webactivity";
    public static final String MODULE1ACTIVITY = "/module1/module1activity";
    public static final String MODULE2ACTIVITY = "/module2/module2activity";
    public static final String DEMOACTIVITY = "/common/demoactivity";


    static {
        actList.add(new ActivityDesc(0, "MainActivity", MAINACTIVITY, "项目主页", "列表展示所有项目功能"));
        actList.add(new ActivityDesc(1, "Module1Activity", MODULE1ACTIVITY, "module1", "module1相关功能"));
        actList.add(new ActivityDesc(2, "Module2Activity", MODULE2ACTIVITY, "module2", "module2相关功能"));
        actList.add(new ActivityDesc(3, "WebActivity", WEBACTIVITY, "web,h5", "android下h5的功能实践"));
        actList.add(new ActivityDesc(4, "DemoActivity", DEMOACTIVITY, "mvvm框架实践", "mvvm框架实践"));
    }


}
