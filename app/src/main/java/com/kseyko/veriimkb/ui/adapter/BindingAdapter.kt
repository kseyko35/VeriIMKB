package com.kseyko.veriimkb.ui.adapter

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kseyko.veriimkb.R
import com.kseyko.veriimkb.data.model.stock.Stock
import java.text.SimpleDateFormat


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
        val adapter = it as StockListAdapter
        adapter.setStockList(list ?: listOf())
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

@BindingAdapter("com.kseyko.veriimkb.ui.adapter.setTextWithHtml")
fun setTextWithHtml(textView: TextView, text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        textView.text = Html.fromHtml(text)
    }
}

@BindingAdapter("com.kseyko.veriimkb.ui.adapter.formatDate")
fun formatDate(textView: TextView, firstFlight: String?) {
    firstFlight?.let {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(firstFlight)
        textView.text = SimpleDateFormat("dd.MM.yyyy").format(date)
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

