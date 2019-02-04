package com.example.newsapplication.base

import android.support.v7.widget.RecyclerView

/**
 * Created by abhishek on 28/08/17.
 */

abstract class BaseRecyclerViewAdapter<T, V : RecyclerView.ViewHolder>(
    protected var mList: List<T>?
) : RecyclerView.Adapter<V>() {

    override fun getItemCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }

    fun setData(mList: List<T>) {
        this.mList = mList
        notifyDataSetChanged()
    }
}
