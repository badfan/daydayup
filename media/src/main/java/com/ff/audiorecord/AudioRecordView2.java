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

public class AudioRecordView2 extends RelativeLayout {
    private static final String TAG = "AudioRecordView";
    private Context ct;
    TextView startRecord;
    Chronometer timmer;
    ImageView close;
    private MediaRecorder mRecorder;
    private boolean isDownTimeRecording = false;
    private boolean isUpToClose = false;
    AudioRecorder  audioRecord;

    public AudioRecordView2(Context context) {
        this(context, null, -1);
    }

    public AudioRecordView2(Context context, AttributeSet attrs) {

        this(context, attrs, -1);
    }

    public AudioRecordView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        this.ct = context;
        View view = View.inflate(context, R.layout.audio_record2, this);
        startRecord = view.findViewById(R.id.startRecord);
        timmer = view.findViewById(R.id.timer);
        close = view.findViewById(R.id.close);
        audioRecord = new AudioRecorder (ct);
    }

    PointF downPoint = new PointF();
    PointF movePoint = new PointF();

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
                downPoint.set(event.getX(), event.getY());
                if (checkPointOnView(startRecord, downPoint)) {
                    startDownTimeRecord();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "ACTION_MOVE");
                movePoint.set(event.getX(), event.getY());
                if (isDownTimeRecording) {
                    if (movePoint.x - downPoint.x > 10 || movePoint.y - downPoint.y > 10) {
                        Log.e(TAG, "移动了,移除长按事件");
                        stopTimeRecord();
                    }
                }
                if (audioRecord.isAudioRecording()) {
                    //监听移动位置
                    if (checkPointOnView(close, movePoint)) {
                        if(!isUpToClose) {//只弹一次toast
                            Toast.makeText(ct, "松开取消录音", Toast.LENGTH_SHORT).show();
                        }
                        isUpToClose = true;
                    } else {
                        isUpToClose = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                stopTimeRecord();
                if (audioRecord.isAudioRecording()) {
                    stopAudioRecord();
                    if (isUpToClose) {
                        cancelSend();
                        Toast.makeText(ct, "取消录音", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "sendRecordResult");
                        sendRecordResult();
                    }
                }
                downPoint = new PointF();
                movePoint = new PointF();
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "ACTION_CANCEL");
                stopTimeRecord();
                downPoint = new PointF();
                movePoint = new PointF();
                break;
        }
        return true;
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
//        Toast.makeText(ct, "停止录音", Toast.LENGTH_SHORT).show();
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
