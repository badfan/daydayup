package com.ff.common.arouter.module1;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IModule1Service extends IProvider {
    String sayHello(String name);
}
