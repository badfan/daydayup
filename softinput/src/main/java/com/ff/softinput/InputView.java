package com.ff.softinput;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ff.audiorecord.AudioRecorder2;


public class InputView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "InputView";
    private static final long TOUCH_AUDIO_STARTTIME = 1000;
    private static final int MAX_TIME = 45;
    Context ct;
    EditText editText;
    TextView tvReply;
    TextView tvPicture;
    TextView tvMedicalRecord;
    TextView tvPrescription;
    TextView tvAudio;
    TextView tvReply1;
    TextView tvCancel;
    TextView tvAudioStart;
    View layoutInputNormal;
    View layoutInputAudio;
    private ICallBack callBack;
    private boolean isDownTimeRecording;
    AudioRecorder2 audioRecord;

    int maxRecordTime = MAX_TIME;
    private LayoutType layoutType = LayoutType.NORMAL;

    enum LayoutType {
        NORMAL, AUDIO
    }

    public InputView(Context ct) {
        this(ct, null, -1);
    }

    public InputView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.ct = context;
        View view = View.inflate(ct, R.layout.layout_input, this);
        audioRecord = new AudioRecorder2(ct);
        viewIds();
        audioButton();
        editText.setHorizontallyScrolling(false);
        editText.setMaxLines(5);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Log.e(TAG,"onEditorAction=" + actionId);
                    String msg = v.getText().toString().trim();
                    sendMsg(msg);
                }
                return false;
            }
        });
        AndroidBug5497Workaround.setOnKeyboardListener(new AndroidBug5497Workaround.OnKeyboardListener() {
            @Override
            public void status(boolean isShow) {
                Log.e(TAG,"键盘弹出状态=" + isShow);
                if (isShow) {
                    layoutInputAudio.setVisibility(View.GONE);
                    tvReply.setVisibility(View.VISIBLE);
                } else {
                    if (layoutType == LayoutType.AUDIO) {
                        InputView.this.showLayout(LayoutType.AUDIO);
                    }
                }
            }
        });
    }

    private void viewIds() {
        editText = findViewById(R.id.editText);
        tvReply = findViewById(R.id.tv_reply);
        tvPicture = findViewById(R.id.tv_picture);
        tvMedicalRecord = findViewById(R.id.tv_medicalRecord);
        tvPrescription = findViewById(R.id.tv_prescription);
        tvAudio = findViewById(R.id.tv_audio);
        tvReply1 = findViewById(R.id.tv_reply1);
        tvCancel = findViewById(R.id.tv_cancel);
        tvAudioStart = findViewById(R.id.tv_audioStart);
        layoutInputNormal = findViewById(R.id.layout_input_normal);
        layoutInputAudio = findViewById(R.id.layout_input_audio);
        tvReply.setOnClickListener(this);
        tvPicture.setOnClickListener(this);
        tvMedicalRecord.setOnClickListener(this);
        tvPrescription.setOnClickListener(this);
        tvAudio.setOnClickListener(this);
        tvReply1.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    private void showLayout(LayoutType type) {
        if (type == LayoutType.AUDIO) {
            layoutInputAudio.setVisibility(View.VISIBLE);
            layoutInputNormal.setVisibility(View.GONE);
            tvReply.setVisibility(View.GONE);
        } else {
            layoutInputAudio.setVisibility(View.GONE);
            layoutInputNormal.setVisibility(View.VISIBLE);
            tvReply.setVisibility(View.VISIBLE);
        }
    }

    public void reset() {
        editText.setText("");
        layoutType = LayoutType.NORMAL;
        showLayout(layoutType);
//        CommonUtil.closeSoftKeyboard(ct, editText);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_reply) {
            sendMsg(editText.getText().toString().trim());

        } else if (i == R.id.tv_reply1) {
            sendMsg(editText.getText().toString().trim());

        } else if (i == R.id.tv_picture) {
            if (callBack != null) {
                callBack.onPictureClick();
            }

        } else if (i == R.id.tv_medicalRecord) {
            if (callBack != null) {
                callBack.onMedicalRecordClick();
            }

        } else if (i == R.id.tv_prescription) {
            if (callBack != null) {
                callBack.onPrescriptionClick();
            }

        } else if (i == R.id.tv_audio) {
            layoutType = LayoutType.AUDIO;
            showLayout(layoutType);
//            CommonUtil.closeSoftKeyboard(ct, editText);

        } else if (i == R.id.tv_cancel) {
            layoutType = LayoutType.NORMAL;
            showLayout(layoutType);

        }
    }

    PointF downPoint = new PointF();
    PointF movePoint = new PointF();

    private void audioButton() {
        tvAudioStart.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "ACTION_DOWN");
                        downPoint.set(event.getX(), event.getY());
                        InputView.this.startDownTimeRecord();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "ACTION_MOVE");
                        movePoint.set(event.getX(), event.getY());
                        Log.e(TAG, "movePoint=" + movePoint.toString() + ",downPoint=" + downPoint.toString());
                        if (isDownTimeRecording) {
                            if (Math.abs(movePoint.x - downPoint.x) > 20 || Math.abs(movePoint.y - downPoint.y) > 20) {
                                Log.e(TAG, "移动了,移除长按事件");
                                InputView.this.stopTimeRecord();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "ACTION_UP");
                        InputView.this.stopTimeRecord();
                        InputView.this.stopAudioRecord();
                        downPoint = new PointF();
                        movePoint = new PointF();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.e(TAG, "ACTION_CANCEL");
                        InputView.this.stopTimeRecord();
                        InputView.this.stopAudioRecord();
                        movePoint = new PointF();
                        break;
                }

                return true;
            }
        });
    }

    /**
     * down时间在view上的时间
     */
    private void startDownTimeRecord() {
        isDownTimeRecording = true;
        getHandler().postDelayed(downTimeRecordRunnable, TOUCH_AUDIO_STARTTIME);
    }

    private void stopTimeRecord() {
        isDownTimeRecording = false;
        getHandler().removeCallbacks(downTimeRecordRunnable);
    }

    Runnable downTimeRecordRunnable = new Runnable() {
        @Override
        public void run() {
            isDownTimeRecording = false;
            Log.e(TAG, "1s触发长按");
//            Toast.makeText(ct, "开始录音", Toast.LENGTH_SHORT).show();
            startAudioRecord();
        }
    };

    private void startAudioRecord() {
        audioRecord.startRecord();
        audioRecord.setRecordResultListener(new AudioRecorder2.IRecordResultListener() {
            @Override
            public void recordFailed(Exception e) {

            }

            @Override
            public void recordResult(String path) {
//                RetrofitUtils.getInstance().recognizeAudio(path).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new RetrofitCallBack<RecorgnizeResponse>() {
//                            @Override
//                            public void onPostFail(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onSuccess(RecorgnizeResponse response) {
//                                String audioData = response.result;
//                                if (!TextUtils.isEmpty(audioData)) {
//                                    int index = editText.getSelectionStart();
//                                    Editable editable = editText.getText();
//                                    editable.insert(index, audioData);
//                                }
//                            }
//
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                        });
            }
        });
        startRecordTimmer();
        if (callBack != null) {
            callBack.onAudioStart();
        }
    }

    public void stopAudioRecord() {
        Log.e(TAG, "stopAudioRecord");
//        Toast.makeText(ct, "停止录音", Toast.LENGTH_SHORT).show();
        maxRecordTime = MAX_TIME;
        if (callBack != null) {
            //重置显示ui的时间
            callBack.recordTimmer(maxRecordTime);
        }
        audioRecord.stopRecordWidthResult();
        stopRecordTimmer();
        if (callBack != null) {
            callBack.onAudioStop();
        }
    }


    private void startRecordTimmer() {
        getHandler().postDelayed(timmerRecordRunnable, 1000);
    }

    private Runnable timmerRecordRunnable = new Runnable() {
        @Override
        public void run() {
            maxRecordTime--;
            if (maxRecordTime <= 0) {
                return;
            }
            startRecordTimmer();
            if (callBack != null) {
                callBack.recordTimmer(maxRecordTime);
            }
        }
    };

    private void stopRecordTimmer() {
        if (timmerRecordRunnable != null) {
            getHandler().removeCallbacks(timmerRecordRunnable);
        }
    }


    private void sendRecordResult() {
        Toast.makeText(ct, "发送录音", Toast.LENGTH_SHORT).show();
    }

    private void sendMsg(String msg) {
        Log.e( "inputView", "msg=" + msg);
        editText.setText("");
        if (callBack != null) {
            callBack.onSendMsg(msg);
        }
    }


    public void setOnCallBack(ICallBack callBack) {
        this.callBack = callBack;
    }

    public interface ICallBack {
        void onSendMsg(String msg);

        void onPictureClick();

        void onMedicalRecordClick();

        void onPrescriptionClick();

        void onAudioStart();

        void onAudioStop();

        void recordTimmer(int timmer);
    }
}
