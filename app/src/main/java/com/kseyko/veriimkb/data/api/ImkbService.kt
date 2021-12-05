package com.kseyko.veriimkb.data.api

import com.kseyko.veriimkb.data.model.detail.StockDetailRequest
import com.kseyko.veriimkb.data.model.detail.StockDetailResponse
import com.kseyko.veriimkb.data.model.stock.StockListRequest
import com.kseyko.veriimkb.data.model.stock.StockListResponse
import com.kseyko.veriimkb.helper.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface ImkbService {

    @POST(Constants.STOCK_LIST_END_POINT)
    @Headers("Content-Type: application/json")
    suspend fun requestList(
        @Header("X-VP-Authorization") XVPAuthorization: String,
        @Body request: StockListRequest
    ): Response<StockListResponse>

    @POST(Constants.STOCK_DETAIL_END_POINT)
    @Headers("Content-Type: application/json")
    suspend fun requestDetail(
        @Header("X-VP-Authorization") XVPAuthorization: String,
        @Body request: StockDetailRequest
    ): Response<StockDetailResponse>

}