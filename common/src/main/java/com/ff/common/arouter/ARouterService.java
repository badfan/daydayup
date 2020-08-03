package com.ff.common.arouter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ff.common.arouter.module1.IModule1Path;
import com.ff.common.arouter.module1.IModule1Service;
import com.ff.common.arouter.module2.IModule2Path;
import com.ff.common.arouter.module2.IModule2Service;

public class ARouterService {
    public static IModule1Service getModule1Provider() {
        return (IModule1Service) ARouter.getInstance().build(IModule1Path.IMODULE1SERVICE).navigation();
    }

    public static IModule2Service getModule2Provider() {
        return (IModule2Service) ARouter.getInstance().build(IModule2Path.IMODULE2SERVICE).navigation();
    }
}
