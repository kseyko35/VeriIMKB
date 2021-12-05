package com.kseyko.veriimkb.data.model.handshake

import com.kseyko.veriimkb.data.model.Status

data class HandshakeResponse(
    val aesIV: String,
    val aesKey: String,
    val authorization: String,
    val lifeTime: String,
    val status: Status
)

