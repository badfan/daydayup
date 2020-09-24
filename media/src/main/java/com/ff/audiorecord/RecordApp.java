package com.ff.audiorecord;

import android.app.Application;
import android.content.Context;

import com.zlw.main.recorderlib.RecordManager;

public class RecordApp {
    public static void init(Application ct) {
        RecordManager.getInstance().init(ct, false);
    }
}
