package com.kseyko.veriimkb.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Singleton

object AESFunction {
    @Singleton
    @JvmStatic
    fun encrypt(encryptedValue: String, aesKey: String, aesIV: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            SecretKeySpec(Base64.decode(aesKey.toByteArray(), Base64.DEFAULT), "AES"),
            IvParameterSpec(Base64.decode(aesIV.toByteArray(), Base64.DEFAULT))
        )
        val encryptedPeriod = cipher.doFinal(encryptedValue.toByteArray())
        return Base64.encodeToString(encryptedPeriod, Base64.DEFAULT)
    }

    @Singleton
    @JvmStatic
    fun decrypt(symbol: String, aesKey: String, aesIV: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            SecretKeySpec(Base64.decode(aesKey.toByteArray(), Base64.DEFAULT), "AES"),
            IvParameterSpec(Base64.decode(aesIV.toByteArray(), Base64.DEFAULT))
        )
        val decryptedSymbol = cipher.doFinal(Base64.decode(symbol.toByteArray(), Base64.DEFAULT))
        return decryptedSymbol.decodeToString()
    }
}