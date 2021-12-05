package com.kseyko.veriimkb.data.model.detail

import com.kseyko.veriimkb.data.model.Status

data class StockDetailResponse(
    val bid: Double,
    val channge: Double,
    val count: Int,
    val difference: Double,
    val graphicData: List<Graphic>,
    val highest: Double,
    val isDown: Boolean,
    val isUp: Boolean,
    val lowest: Double,
    val maximum: Double,
    val minimum: Double,
    val offer: Double,
    val price: Double,
    val status: Status,
    var symbol: String,
    val volume: Double
)