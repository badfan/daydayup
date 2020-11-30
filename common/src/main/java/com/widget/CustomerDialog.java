package com.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ff.common.R;


public class CustomerDialog extends Dialog {

    ProgressBar dialog_pb;
    TextView tv_msg;

    private Context ct;

    public CustomerDialog(Context context, int theme) {
        super(context, theme);
        this.ct = context;
        initView();
    }

    private void initView() {

        View view = View.inflate(ct, R.layout.f_customerdialog, null);
        setContentView(view);

        dialog_pb = (ProgressBar) view.findViewById(R.id.dialog_pb);
        tv_msg = (TextView) view.findViewById(R.id.tv_msg);

//		getWindow().setWindowAnimations(R.style.AnimationDialog);
    }

    public void setMessage(String msg) {
        tv_msg.setText(msg);
        tv_msg.setVisibility(View.VISIBLE);
    }
}
