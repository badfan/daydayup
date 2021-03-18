package com.ff.audiorecord;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zlw.main.recorderlib.RecordManager;
import com.zlw.main.recorderlib.recorder.RecordConfig;
import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.RequiresApi;

/**
 * @author Victor
 * @desc 调用三方库implementation 'com.github.zhaolewei:ZlwAudioRecorder:v1.07'
 * 录音pcm文件
 * @time 2021/3/18
 */
public class AudioRecorder2 {
    private static final String TAG = "AudioRecordView";

    private Context ct;

    private boolean isAudioRecording;
    static AudioRecorder2 defaultAudioRecord;

    public static AudioRecorder2 getDefault(Context ct) {
        if (defaultAudioRecord == null) {
            synchronized (AudioRecorder2.class) {
                if (defaultAudioRecord == null) {
                    defaultAudioRecord = new AudioRecorder2(ct);
                }
            }
        }
        return defaultAudioRecord;
    }

    public AudioRecorder2(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.ct = context;
        RecordManager.getInstance().changeRecordConfig(RecordManager.getInstance().getRecordConfig().setSampleRate(16000));
        RecordManager.getInstance().changeRecordConfig(RecordManager.getInstance().getRecordConfig().setEncodingConfig(AudioFormat.ENCODING_PCM_8BIT));
        RecordManager.getInstance().changeFormat(RecordConfig.RecordFormat.MP3);
    }

    public boolean isAudioRecording() {
        return isAudioRecording;
    }

    /**
     * luyin
     */
    public void startRecord() {
        if (!isCheckPermisson()) {
            requestPermission();
            return;
        }

        Log.e(TAG, "开始录音");
        try {
            isAudioRecording = true;
            RecordManager.getInstance().changeRecordDir(ct.getCacheDir() + "/audio/");
            RecordManager.getInstance().start();
            RecordManager.getInstance().setRecordResultListener(new RecordResultListener() {
                @Override
                public void onResult(File result) {
                    if (getRecordResultListener() != null) {
                        getRecordResultListener().recordResult(result.getAbsolutePath());
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "录音异常", e);
            if (getRecordResultListener() != null) {
                getRecordResultListener().recordFailed(e);
            }
        }
    }


    public void stopRecord() {
        try {
            isAudioRecording = false;
            RecordManager.getInstance().stop();
            Log.e(TAG, "停止录制");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecordWidthResult() {

        Log.e(TAG, "stopRecordWidthResult:");
        if (isAudioRecording()) {
            stopRecord();
        }
    }


    private boolean isCheckPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkPermission = ct.checkPermission(Permission.RECORD_AUDIO, Process.myPid(), Process.myUid());
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                return true;
            }

            return false;
        } else {
            return true;
        }
    }

    private void requestPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ct instanceof Activity) {
                Activity act = (Activity) ct;
                act.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 100);
            } else {
                try {
                    throw new Exception("requestPermission中context需要Activity类型,而接受的是" + ct.getClass().getTypeName());
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        }

    }

    IRecordResultListener recordResultListener;

    public IRecordResultListener getRecordResultListener() {
        return recordResultListener;
    }

    public void setRecordResultListener(IRecordResultListener iRecordResult) {
        this.recordResultListener = iRecordResult;
    }

    public interface IRecordResultListener {
        void recordFailed(Exception e);

        void recordResult(String path);
    }
}
