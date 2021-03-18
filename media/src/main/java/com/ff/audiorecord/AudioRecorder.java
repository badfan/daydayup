package com.ff.audiorecord;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自定义录音pcm
 */
public class AudioRecorder {
    private static final String TAG = "AudioRecordView";

    private final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;//音频输入-麦克风
    private final static int AUDIO_SAMPLE_RATE = 16000;//采用频率
    private final static int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;//声道 单声道
    private final static int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;//编码
    private Context ct;

    private AudioRecord mRecorder;
    private String fileName;
    private boolean isAudioRecording;
    static AudioRecorder defaultAudioRecord;
    private int bufferSizeInBytes;
    ExecutorService executorService = Executors.newCachedThreadPool();

    public static AudioRecorder getDefault(Context ct) {
        if (defaultAudioRecord == null) {
            synchronized (AudioRecorder.class) {
                if (defaultAudioRecord == null) {
                    defaultAudioRecord = new AudioRecorder(ct);
                }
            }
        }
        return defaultAudioRecord;
    }

    public AudioRecorder(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.ct = context;
        bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING);
//        mRecorder.setAudioSamplingRate(16000);  //设置录制的音频采样率
//        mRecorder.setAudioEncodingBitRate(7950);  //音频编码比特率
//        mRecorder.setAudioChannels(AudioFormat.CHANNEL_IN_DEFAULT);
//        mRecorder.setAudioSource(MyMediaRecorder.AudioSource.MIC);
//        mRecorder.setOutputFormat(MyMediaRecorder.OutputFormat.AMR_WB);//注意AMR_NB是8000采样率
//        mRecorder.setAudioEncoder(AudioFormat.ENCODING_PCM_16BIT);
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
            //每次都要初始化,不然会有问题
            mRecorder = new AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeInBytes);
            mRecorder.startRecording();
            writeToFile();

        } catch (Exception e) {
            Log.e(TAG, "录音异常", e);
            if (getRecordResultListener() != null) {
                getRecordResultListener().recordFailed(e);
            }
        }
    }

    private void writeToFile() {

        // new一个byte数组用来存一些字节数据，大小为缓冲区大小
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] audiodata = new byte[bufferSizeInBytes];
                    FileOutputStream fos = null;
                    int readsize = 0;

                    File file = newFileName();
                    fos = new FileOutputStream(file);// 建立一个可存取字节的文件

                    //将录音状态设置成正在录音状态
                    while (isAudioRecording) {
                        readsize = mRecorder.read(audiodata, 0, bufferSizeInBytes);
                        Log.e(TAG, "readsize=" + readsize + ",,," + "bufferSizeInBytes=" + bufferSizeInBytes + ",,," + isAudioRecording() + ",,thread=" + Thread.currentThread().getName());
                        if (AudioRecord.ERROR_INVALID_OPERATION != readsize && fos != null) {
                            try {
                                Log.e(TAG, "audiodata=" + audiodata.length);
                                fos.write(audiodata, 0, readsize);
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }

                    if (fos != null) {
                        fos.flush();//刷新缓存区
                        fos.close();// 关闭写入流
                    }
                    Log.e(TAG, "线程结束");

                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                    if (getRecordResultListener() != null) {
                        getRecordResultListener().recordFailed(e);
                    }
                }

            }
        }).start();
    }


    public void stopRecord() {
        try {
            isAudioRecording = false;
            if (mRecorder != null) {
                mRecorder.stop();
//                mRecorder.release();
                Log.e(TAG, "停止录制");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecordWidthResult() {

        Log.e(TAG, "stopRecordWidthResult");
        if(isAudioRecording()) {
            stopRecord();
            if (getRecordResultListener() != null) {
                getRecordResultListener().recordResult(fileName);
            }
        }
    }


    public File newFileName() {
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
        return file;
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
