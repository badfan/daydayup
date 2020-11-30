package com.ff.mvvm.base;

import android.app.Application;

import com.ff.common.utils.LogUtil;
import com.ff.common.utils.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class BaseViewModel<U extends BaseUIObserver> extends AndroidViewModel {

    private U mUIOB;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        createUIOB();
    }


    public U getUIOB() {
        if (mUIOB == null) {
            mUIOB = createUIOB();
            LogUtils.log("UIObserver初始化成功:" + mUIOB.getClass().getName());
        }
        return mUIOB;
    }

    private U createUIOB() {
        Type type = getClass().getGenericSuperclass();
        U u = (U) new BaseUIObserver();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                Class<U> uiobClass = (Class) actualTypeArguments[0];
                try {
                    u = uiobClass.newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return u;
    }

    public void showDialog() {
        getUIOB().getShowDialogEvent().postValue("加载中...");
    }

    public void dismissDialog() {
        getUIOB().getDismissDialogEvent().postValue(null);
    }
}
