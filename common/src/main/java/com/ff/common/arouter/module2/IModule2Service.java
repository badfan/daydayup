package com.ff.common.arouter.module2;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IModule2Service extends IProvider {
    String sayHello(String name);
}
