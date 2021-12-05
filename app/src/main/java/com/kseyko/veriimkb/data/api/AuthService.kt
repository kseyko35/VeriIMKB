package com.kseyko.veriimkb.data.api

import com.kseyko.veriimkb.data.model.handshake.HandshakeRequest
import com.kseyko.veriimkb.data.model.handshake.HandshakeResponse
import com.kseyko.veriimkb.helper.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @POST(Constants.HANDSHAKE_END_POINT)
    @Headers("Content-Type: application/json")
    suspend fun requestHandshake(
        @Body request: HandshakeRequest
    ): Response<HandshakeResponse>
}