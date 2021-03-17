package com.ff.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import com.ff.study.R

class ActivityAdapter(private var ct: Context) : BaseAdapter() {


    private var mItemList: List<String>? = null

    public fun setItemList(list: List<String>) {
        mItemList = list
    }

    override fun getCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View? {
        var view = View.inflate(ct, R.layout.act_adapter, null)
        var holder: ViewHolder
        if (convertView == null) {
            holder = ViewHolder()
            holder.tv_name = view.findViewById(R.id.tv_name)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.tv_name.text = "你好"
        return view
    }

    class ViewHolder {
        lateinit var tv_name: TextView

        init {

        }
    }

}
