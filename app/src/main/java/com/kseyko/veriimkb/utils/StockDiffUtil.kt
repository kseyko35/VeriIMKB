package com.kseyko.veriimkb.utils

import androidx.recyclerview.widget.DiffUtil
import com.kseyko.veriimkb.data.model.stock.Stock

class StockDiffUtil(
    private val oldStockList: List<Stock>,
    private val newStockList: List<Stock>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldStockList.size
    }

    override fun getNewListSize(): Int {
        return newStockList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldStockList[oldItemPosition] === newStockList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldStockList[oldItemPosition].id != newStockList[newItemPosition].id
    }
}