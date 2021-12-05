package com.kseyko.veriimkb.data.model.handshake

data class HandshakeRequest(
    val deviceId: String,
    val deviceModel: String,
    val manifacturer: String,
    val platformName: String,
    val systemVersion: String
)