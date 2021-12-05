package com.kseyko.veriimkb.data.repository

import com.kseyko.veriimkb.data.model.detail.StockDetailRequest
import com.kseyko.veriimkb.data.model.detail.StockDetailResponse
import com.kseyko.veriimkb.data.model.handshake.HandshakeRequest
import com.kseyko.veriimkb.data.model.handshake.HandshakeResponse
import com.kseyko.veriimkb.data.model.stock.StockListRequest
import com.kseyko.veriimkb.data.model.stock.StockListResponse
import com.kseyko.veriimkb.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton


@Singleton
interface ImkbRepository {
    fun getHandShake(handshakeRequest: HandshakeRequest): Flow<Resource<HandshakeResponse>>

    fun getImkbList(
        stockListRequest: StockListRequest,
        XVPAuthorization: String
    ): Flow<Resource<StockListResponse>>

    fun getImkbDetail(
        stockDetailRequest: StockDetailRequest,
        XVPAuthorization: String
    ): Flow<Resource<StockDetailResponse>>
}