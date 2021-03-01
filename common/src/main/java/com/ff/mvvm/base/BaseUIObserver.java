package com.ff.mvvm.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BaseUIObserver {


    MutableLiveData<String> showDialogEvent;
    MutableLiveData<Void> dismissDialogEvent;
    MutableLiveData<String> toastEvent;
    MutableLiveData<String> finishEvent;


    public  <T> MutableLiveData<T> createLiveData(MutableLiveData<T> liveData) {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public MutableLiveData<String> getShowDialogEvent() {
        return showDialogEvent=createLiveData(showDialogEvent);
    }

    public MutableLiveData<Void> getDismissDialogEvent() {
        return dismissDialogEvent=createLiveData(dismissDialogEvent);
    }

    public MutableLiveData<String> getToastLiveData() {
        return toastEvent=createLiveData(toastEvent);
    }

    public MutableLiveData<String> getFinishEvent() {
        return finishEvent=createLiveData(finishEvent);
    }
}
