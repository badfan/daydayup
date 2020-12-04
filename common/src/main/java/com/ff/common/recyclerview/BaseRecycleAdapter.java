package com.ff.common.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ff.common.utils.LogUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {
    public Context ct;
    protected List<T> mInfos;
    protected OnRecyclerViewItemClickListener mOnItemClickListener = null;
    protected OnRecyclerViewItemClickListener mOnItemLongClickListener = null;
    private BaseHolder<T> mHolder;

    public BaseRecycleAdapter(Context ct) {
        this.ct = ct;
    }

    public void setItemList(List<T> infos) {
        this.mInfos = infos;
    }

    /**
     * 创建Hodler
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        mHolder = createHolder(view, viewType);

//        mHolder.setOnHolderViewClickListener(new BaseHolder.OnHolderViewClickListener() {
//            @Override
//            public void onHolderViewClick(View view, int layoutPosition) {
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClick(view, getItem(layoutPosition), layoutPosition);
//                }
//            }
//        });
        return mHolder;
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(BaseHolder<T> holder, final int position) {
        LogUtil.log("position===" + position);
        holder.setData(getItem(position), position);
        holder.setOnHolderViewClickListener(new BaseHolder.OnHolderViewClickListener() {
            @Override
            public void onHolderViewClick(View view, int layoutPosition) {
                if (mOnItemClickListener != null) {
                    LogUtil.log("position==" + position);
                    mOnItemClickListener.onItemClick(view, getItem(position), position);
                }
            }
        });
        holder.setOnHolderViewLongClickListener(new BaseHolder.OnHolderViewClickListener() {
            @Override
            public void onHolderViewClick(View view, int layoutPosition) {
                if (mOnItemLongClickListener != null) {
                    LogUtil.log("position==" + position);
                    mOnItemLongClickListener.onItemClick(view, getItem(position), position);
                }
            }
        });
    }

    /**
     * 数据的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mInfos == null ? 0 : mInfos.size();
    }


    public List<T> getItemList() {
        return mInfos;
    }


    /**
     * 获得item的数据
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        return mInfos == null ? null : mInfos.get(position);
    }

    /**
     * 子类实现提供holder
     *
     * @param v
     * @param viewType
     * @return
     */
    public abstract BaseHolder<T> createHolder(View v, int viewType);

    /**
     * 提供Item的布局
     *
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(int viewType);


    public interface OnRecyclerViewItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }
}
