package com.ff.demo;

import com.ff.mvvm.base.BaseUIObserver;

import androidx.lifecycle.MutableLiveData;

public class DemoUIObserver extends BaseUIObserver {


    MutableLiveData<String> testEvent;
    MutableLiveData<String> loadWebEvent;


    public MutableLiveData<String> getTestEvent() {
        return testEvent = createLiveData(testEvent);
    }

    public MutableLiveData<String> getLoadWebEvent() {
        return loadWebEvent = createLiveData(loadWebEvent);
    }

}
