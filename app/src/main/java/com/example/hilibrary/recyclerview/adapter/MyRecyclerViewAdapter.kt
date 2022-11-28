package com.example.hilibrary.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hilibrary.R
import com.example.hilibrary.recyclerview.ItemData

class MyRecyclerViewAdapter : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    lateinit var mInflater: LayoutInflater
    lateinit var context: Context
    lateinit var dataSets: MutableList<ItemData>;


    constructor(context: Context, dataSets: MutableList<ItemData>) {
        this.context = context
        this.dataSets = dataSets;
        mInflater = LayoutInflater.from(context);

    }

    override fun getItemViewType(position: Int): Int {
        return dataSets[position].itemType
    }

    /**
     * 创建View
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRecyclerViewAdapter.MyViewHolder {
        var layoutRes = 0
        when (viewType) {
            ItemData.TYPE_TOP_TAB -> layoutRes = R.layout.layout_list_item_top_tab
            ItemData.TYPE_BANNER -> layoutRes = R.layout.layout_list_item_banner
            ItemData.TYPE_GRID_ITEM -> layoutRes = R.layout.layout_list_item_grid
            ItemData.TYPE_ACTIVITY -> layoutRes = R.layout.layout_list_item_activity
            ItemData.TYPE_ITEM_TAB -> layoutRes = R.layout.layout_list_item_tab
            ItemData.TYPE_VIDEO -> layoutRes = R.layout.layout_list_item_video
            ItemData.TYPE_IMAGE -> layoutRes = R.layout.layout_list_item_image
        }

        var view: View
        if (layoutRes != 0) {
            view = mInflater.inflate(layoutRes, parent, false)
        } else {
            //如果该item的类型不在已知类型内，则创建个兜底View。
            view = TextView(context)
            view.setVisibility(View.GONE)
        }
        return MyViewHolder(view)
    }


    /**
     * 绑定数据
     */
    override fun onBindViewHolder(holder: MyRecyclerViewAdapter.MyViewHolder, position: Int) {

        var itemData = dataSets[position]
        when (itemData.itemType) {
            ItemData.TYPE_TOP_TAB -> holder.imageView?.setImageResource(R.drawable.item_top_tab)
            ItemData.TYPE_BANNER -> holder.imageView?.setImageResource(R.drawable.item_banner)
            ItemData.TYPE_GRID_ITEM -> holder.imageView?.setImageResource(R.drawable.item_grid)
            ItemData.TYPE_ACTIVITY -> holder.imageView?.setImageResource(R.drawable.item_activity)
            ItemData.TYPE_ITEM_TAB -> holder.imageView?.setImageResource(R.drawable.item_tab)
            ItemData.TYPE_VIDEO -> holder.imageView?.setImageResource(R.drawable.item_video)
            ItemData.TYPE_IMAGE -> holder.imageView?.setImageResource(R.drawable.item_image)
        }

    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    //声明继承并且里面参数，有两种
    //1：构造方法 constructor(itemView:View) :super(itemView)
    //2: 在类上 class MyViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView)
    class MyViewHolder : RecyclerView.ViewHolder {
        var imageView: ImageView? = null

        constructor(itemView: View) : super(itemView) {
            imageView = itemView.findViewById(R.id.item_image)
        }


    }


}