package com.ff.module1;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.ff.common.arouter.module1.IModule1Path;
import com.ff.common.arouter.module1.IModule1Service;
import com.ff.common.arouter.module2.IModule2Service;
import com.ff.common.utils.LogUtils;

@Route(path = IModule1Path.IMODULE1SERVICE, name = "module1接口实现")
public class Module1Service implements IModule1Service {


    @Override
    public void init(Context context) {

    }

    @Override
    public String sayHello(String name) {
        LogUtils.log(name);
        return name;
    }
}
