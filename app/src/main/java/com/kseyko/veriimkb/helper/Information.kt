package com.kseyko.veriimkb.helper

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.kseyko.veriimkb.data.model.detail.StockDetailRequest
import com.kseyko.veriimkb.data.model.handshake.HandshakeRequest
import com.kseyko.veriimkb.utils.AESFunction
import javax.inject.Singleton


object Information {
    @Singleton
    @JvmStatic
    fun getAuthInfo(requireContext: Context): HandshakeRequest {
        return HandshakeRequest(
            Settings.Secure.getString(
                requireContext.contentResolver,
                Settings.Secure.ANDROID_ID
            ),
            Build.MODEL,
            Build.MANUFACTURER,
            if (Build.FINGERPRINT.contains("generic")) {
                "AndroidSimulator"
            } else {
                "Android"
            },
            Build.VERSION.SDK_INT.toString()
        )
    }

    @Singleton
    @JvmStatic
    fun getDetailInfo(id: String, aesKey: String, aesIV: String): StockDetailRequest {
        val encryptedId = AESFunction.encrypt(id, aesKey, aesIV)
        return StockDetailRequest(
            encryptedId
        )
    }

}
