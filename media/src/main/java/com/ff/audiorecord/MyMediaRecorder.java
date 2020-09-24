package com.ff.audiorecord;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Environment;
import android.util.Log;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyMediaRecorder {
    private static final String TAG = "AudioRecordView";
    private Context ct;

    private android.media.MediaRecorder mRecorder;
    private String fileName;
    private boolean isAudioRecording;
    static MyMediaRecorder defaultAudioRecord;

    public static MyMediaRecorder getDefault(Context ct) {
        if (defaultAudioRecord == null) {
            synchronized (AudioRecord.class) {
                if (defaultAudioRecord == null) {
                    defaultAudioRecord = new MyMediaRecorder(ct);
                }
            }
        }
        return defaultAudioRecord;
    }

    public MyMediaRecorder(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.ct = context;
        mRecorder = new android.media.MediaRecorder();
        mRecorder.setAudioSamplingRate(16000);  //设置录制的音频采样率
        mRecorder.setAudioEncodingBitRate(7950);  //音频编码比特率
        mRecorder.setAudioChannels(AudioFormat.CHANNEL_IN_DEFAULT);
        mRecorder.setAudioSource(android.media.MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(android.media.MediaRecorder.OutputFormat.AMR_WB);//注意AMR_NB是8000采样率
        mRecorder.setAudioEncoder(AudioFormat.ENCODING_PCM_16BIT);
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
            fileName = newFileName();
            int bps = 7950;
            mRecorder.setOutputFile(fileName);
            mRecorder.setOnInfoListener(new android.media.MediaRecorder.OnInfoListener() {
                @Override
                public void onInfo(android.media.MediaRecorder mr, int what, int extra) {

                }
            });

            // 准备好开始录音
            mRecorder.prepare();
            mRecorder.start();
            isAudioRecording = true;
        } catch (IllegalStateException e) {
            Log.e(TAG, "录音异常", e);
            if (getRecordResultListener() != null) {
                getRecordResultListener().recordFailed(e);
            }
        } catch (IOException e) {
            Log.e(TAG, "录音异常", e);
            if (getRecordResultListener() != null) {
                getRecordResultListener().recordFailed(e);
            }
        } catch (Exception e) {
            Log.e(TAG, "录音异常", e);
            if (getRecordResultListener() != null) {
                getRecordResultListener().recordFailed(e);
            }
        }
    }


    public void stopRecord() {
        try {
            if (mRecorder != null) {
                mRecorder.stop();
            }
            isAudioRecording = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecordWidthResult() {
        stopRecord();
        if (getRecordResultListener() != null) {
            getRecordResultListener().recordResult(fileName);
        }
    }


    public String newFileName() {
        String dir = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/audio";
//        String fileName = ct.getCacheDir().getAbsolutePath() + "/audio";
        String s = new SimpleDateFormat("yyyyMMdd_hhmmss")
                .format(new Date());
        String fileName = dir + ("/rcd_" + s + ".pcm");
        File dirs = new File(dir);
        File file = new File(fileName);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }


    private boolean isCheckPermisson() {
        return AndPermission.hasPermissions(ct, Permission.RECORD_AUDIO);
    }

    private void requestPermission() {
        AndPermission.with(ct)
                .runtime()
                .permission(Permission.RECORD_AUDIO)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // Storage permission are allowed.
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        // Storage permission are not allowed.
                    }
                })
                .start();
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
