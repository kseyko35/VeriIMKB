package com.kseyko.veriimkb.data.repository

import com.kseyko.veriimkb.data.api.AuthService
import com.kseyko.veriimkb.data.api.ImkbService
import com.kseyko.veriimkb.data.model.detail.StockDetailRequest
import com.kseyko.veriimkb.data.model.detail.StockDetailResponse
import com.kseyko.veriimkb.data.model.handshake.HandshakeRequest
import com.kseyko.veriimkb.data.model.handshake.HandshakeResponse
import com.kseyko.veriimkb.data.model.stock.StockListRequest
import com.kseyko.veriimkb.data.model.stock.StockListResponse
import com.kseyko.veriimkb.utils.Resource
import com.kseyko.veriimkb.utils.getResourceByNetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ImkbRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val imkbService: ImkbService
) : ImkbRepository {

    override fun getHandShake(handshakeRequest: HandshakeRequest)
            : Flow<Resource<HandshakeResponse>> = flow {
        emit(getResourceByNetworkRequest { authService.requestHandshake(handshakeRequest) })
    }

    override fun getImkbList(
        stockListRequest: StockListRequest,
        XVPAuthorization: String,
    ): Flow<Resource<StockListResponse>> =
        flow {
            emit(getResourceByNetworkRequest {
                imkbService.requestList(
                    XVPAuthorization,
                    stockListRequest
                )
            })

        }

    override fun getImkbDetail(
        stockDetailRequest: StockDetailRequest,
        XVPAuthorization: String
    ): Flow<Resource<StockDetailResponse>> = flow {
        emit(getResourceByNetworkRequest {
            imkbService.requestDetail(
                XVPAuthorization,
                stockDetailRequest
            )
        })
    }

}