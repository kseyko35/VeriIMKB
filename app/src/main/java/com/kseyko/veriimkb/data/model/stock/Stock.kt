package com.kseyko.veriimkb.data.model.stock


data class Stock(
    val bid: Double,
    val difference: Double,
    val id: Int,
    val isDown: Boolean,
    val isUp: Boolean,
    val offer: Double,
    val price: Double,
    var symbol: String,
    val volume: Double
)