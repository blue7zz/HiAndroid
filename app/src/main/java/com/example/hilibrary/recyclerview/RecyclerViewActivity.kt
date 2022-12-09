package com.example.hilibrary.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.hilibrary.databinding.ActivityRecyclerViewBinding
import com.example.hilibrary.recyclerview.adapter.MyRecyclerViewAdapter


@Route(path = "/recycler/recyclerViewActivity", group = "recycler")
class RecyclerViewActivity : AppCompatActivity() {
    var dataList = mockDataSets();

    lateinit var dataBinding: ActivityRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityRecyclerViewBinding.inflate(layoutInflater);
        setContentView(dataBinding.root)
        dataBinding.recyclerView.adapter = MyRecyclerViewAdapter(this, dataList)
        val gridLayoutManager = GridLayoutManager(this, 2);
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (dataList[position].itemType < ItemData.TYPE_VIDEO) 2 else 1
            }
        }
        dataBinding.recyclerView.layoutManager = gridLayoutManager;
    }


    private fun mockDataSets(): MutableList<ItemData> {
        val dataList: MutableList<ItemData> = mutableListOf()
        for (index in 0..19) {
            val itemData = ItemData()
            if (index < 6) {
                itemData.itemType = index
            } else {
                itemData.itemType = if (index % 2 == 0) ItemData.TYPE_VIDEO else ItemData.TYPE_IMAGE
            }
            dataList.add(itemData)
        }
        return dataList
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as RecyclerViewActivity
        if (dataList != other.dataList) return false
        if (dataBinding != other.dataBinding) return false
        return true
    }

    override fun hashCode(): Int {
        var result = dataList.hashCode()
        result = 31 * result + dataBinding.hashCode()
        return result
    }
}