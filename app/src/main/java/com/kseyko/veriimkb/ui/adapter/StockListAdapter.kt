package com.kseyko.veriimkb.ui.adapter

import android.graphics.Color
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kseyko.veriimkb.R
import com.kseyko.veriimkb.data.model.stock.Stock
import com.kseyko.veriimkb.databinding.ItemStockListBinding
import com.kseyko.veriimkb.ui.base.toBinding
import com.kseyko.veriimkb.utils.AESFunction.decrypt
import com.kseyko.veriimkb.utils.StockDiffUtil
import java.util.*
import kotlin.collections.ArrayList


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,November,2021      ║
╚════════════════════════════╝
 */
class StockListAdapter(
    private val stockListener: StockListener
) : RecyclerView.Adapter<StockListAdapter.StockViewHolder>(), Filterable {
    private var stockList = ArrayList<Stock>()
    private var tempStockList = ArrayList<Stock>()

    fun setStockList(stocks: List<Stock>, aesKey: String, aesIV: String) {
        stockList.clear()
        tempStockList.clear()

        stocks.forEach {
            stockList.add(
                Stock(
                    it.bid,
                    it.difference,
                    it.id,
                    it.isDown,
                    it.isUp,
                    it.offer,
                    it.price,
                    decrypt(
                        it.symbol,
                        aesKey,
                        aesIV
                    ),
                    it.volume
                )
            )
        }
        tempStockList.addAll(stockList)
    }
    class StockViewHolder(private val binding: ItemStockListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Stock, listener: StockListener
        ) {
            binding.apply {
                stockItem = item
                stockListener = listener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder =
        StockViewHolder(parent.toBinding())


    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.WHITE)
        } else holder.itemView.setBackgroundResource(R.color.rowBckColor)
        holder.bind(tempStockList[position], stockListener)
    }

    override fun getItemCount(): Int = tempStockList.size

    fun updateStockList(newStockList: List<Stock>) {
        val diffUtil = StockDiffUtil(stockList, newStockList)
        val difResults = DiffUtil.calculateDiff(diffUtil)
        difResults.dispatchUpdatesTo(this)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isNotEmpty()) {
                    val resultList = ArrayList<Stock>()
                    for (row in stockList) {
                        if (row.symbol.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    tempStockList = resultList
                } else {
                    tempStockList = stockList
                }
                val filterResults = FilterResults()
                filterResults.values = tempStockList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                tempStockList = results?.values as ArrayList<Stock>
                updateStockList(tempStockList)
            }

        }
    }

}

interface StockListener {
    fun onStockClick(stock: Stock)
}

