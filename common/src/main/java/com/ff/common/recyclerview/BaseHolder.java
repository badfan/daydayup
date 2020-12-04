package com.ff.common.recyclerview;

import android.view.View;

import com.ff.common.utils.LogUtil;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hh on 2017/9/1.
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

    private OnHolderViewClickListener onItemClickListener;
    private OnHolderViewClickListener onItemLongClickListener;
    public View itemView;

    public BaseHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    LogUtil.log("getLayoutPosition==" + getLayoutPosition());
                    onItemClickListener.onHolderViewClick(view, getLayoutPosition());
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null) {
                    LogUtil.log("getLayoutPosition==" + getLayoutPosition());
                    onItemLongClickListener.onHolderViewClick(view, getLayoutPosition());
                }
                return false;
            }
//            @Override
//            public void onLongClick(View view) {
//                if (onItemLongClickListener != null) {
//                    LogUtil.log("getLayoutPosition==" + getLayoutPosition());
//                    onItemLongClickListener.onHolderViewClick(view, getLayoutPosition());
//                }
//            }
        });
    }

    public abstract void setData(T t, int position);


    public interface OnHolderViewClickListener<T> {

        void onHolderViewClick(View view, int layoutPosition);
    }

    public void setOnHolderViewClickListener(OnHolderViewClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnHolderViewLongClickListener(OnHolderViewClickListener listener) {
        this.onItemLongClickListener = listener;
    }

}
