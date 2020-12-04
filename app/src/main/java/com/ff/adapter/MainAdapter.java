package com.ff.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ff.common.recyclerview.BaseHolder;
import com.ff.common.recyclerview.BaseRecycleAdapter;
import com.ff.commonconfig.ActivityDesc;
import com.ff.study.R;

public class MainAdapter extends BaseRecycleAdapter<ActivityDesc> {
    public MainAdapter(Context ct) {
        super(ct);
    }

    @Override
    public BaseHolder<ActivityDesc> createHolder(View v, int viewType) {
        return new MainHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.app_main_item;
    }

    public class MainHolder extends BaseHolder<ActivityDesc> {

        TextView tv_title;
        TextView tv_desc;
        public MainHolder(View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_desc=itemView.findViewById(R.id.tv_desc);
        }

        @Override
        public void setData(ActivityDesc activityDesc, int position) {
            tv_desc.setText(activityDesc.desc);
            tv_title.setText(activityDesc.title);
        }
    }
}
