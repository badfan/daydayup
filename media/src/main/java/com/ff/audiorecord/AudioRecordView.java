package com.ff.audiorecord;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AudioRecordView extends RelativeLayout {
    private static final String TAG = "AudioRecordView";
    private Context ct;
    TextView startRecord;
    Chronometer timmer;
    ImageView close;
    private MediaRecorder mRecorder;
    private boolean isDownTimeRecording = false;
    private boolean isUpToClose = false;
    AudioRecorder  audioRecord;

    public AudioRecordView(Context context) {
        this(context, null, -1);
    }

    public AudioRecordView(Context context, AttributeSet attrs) {

        this(context, attrs, -1);
    }

    public AudioRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        this.ct = context;
        View view = View.inflate(context, R.layout.audio_record, this);
        startRecord = view.findViewById(R.id.startRecord);
        timmer = view.findViewById(R.id.timer);
        close = view.findViewById(R.id.close);
        audioRecord = new AudioRecorder (ct);
    }



    private void cancelSend() {

    }

    /**
     * down时间在view上的时间
     */
    private void startDownTimeRecord() {
        isDownTimeRecording = true;
        getHandler().postDelayed(downTimeRecordRunnable, 1000);
    }

    private void stopTimeRecord() {
        isDownTimeRecording = false;
        getHandler().removeCallbacks(downTimeRecordRunnable);
        isDownTimeRecording = false;
    }

    Runnable downTimeRecordRunnable = new Runnable() {
        @Override
        public void run() {
            isDownTimeRecording = false;
            Log.e(TAG, "2s触发长按");
            Toast.makeText(ct, "开始录音", Toast.LENGTH_SHORT).show();
            startAudioRecord();
        }
    };

    private boolean checkPointOnView(View view, PointF downPoint) {
        float x = view.getX();
        float y = view.getY();
        int width = view.getWidth();
        int height = view.getHeight();
        RectF rect = new RectF(x, y, x + width, y + height);
        Log.e(TAG, "checkPointOnView-" + "view=" + rect.toShortString() + ",point=" + downPoint.toString());
        boolean contains = rect.contains(downPoint.x, downPoint.y);
        return contains;
    }

    private void sendRecordResult() {
        Toast.makeText(ct, "发送录音", Toast.LENGTH_SHORT).show();
    }

    /**
     * luyin
     */
    private void startAudioRecord() {
        Log.e(TAG, "startAudioRecord");
        audioRecord.startRecord();
    }

    public void stopAudioRecord() {
        Log.e(TAG, "stopAudioRecord");
        Toast.makeText(ct, "停止录音", Toast.LENGTH_SHORT).show();
        audioRecord.stopRecordWidthResult();

    }

    public String newFileName() {
        String fileName = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/audio";
//        String fileName = ct.getCacheDir().getAbsolutePath() + "/audio";
        String s = new SimpleDateFormat("yyyy-MM-dd hhmmss")
                .format(new Date());
        return fileName + ("/rcd_" + s + ".mp3");
    }
}
