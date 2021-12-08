package com.kseyko.veriimkb.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kseyko.veriimkb.R
import com.kseyko.veriimkb.data.model.stock.Stock


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      26,November,2021      ║
╚════════════════════════════╝
 */
@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<Stock>?) {
    recyclerView.adapter?.let {
        it as StockListAdapter
        it.updateStockList(list ?: listOf())
    }
}

@BindingAdapter("stockUp", "stockDown", requireAll = false)
fun ImageView.setImageChanges(stockUp: Boolean, stockDown: Boolean) {
    when {
        stockUp -> setImageResource(R.mipmap.ic_up)
        stockDown -> setImageResource(R.mipmap.ic_down)
        else -> setImageResource(R.mipmap.ic_stable)
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

