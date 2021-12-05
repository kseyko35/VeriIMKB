package com.kseyko.veriimkb.data.model.stock

import com.kseyko.veriimkb.data.model.Status

data class StockListResponse(
    val status: Status,
    val stocks: List<Stock>
)