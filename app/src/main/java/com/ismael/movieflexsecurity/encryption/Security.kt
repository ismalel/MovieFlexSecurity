package com.ismael.movieflexsecurity.encryption

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Base64
import java.lang.Exception
import java.lang.RuntimeException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.PBEParameterSpec

class Security(val context: Context) {
    val keyPass = ";jo;sojd;aoj;sdfj;sdfj;dsofj;sdofj;dsfj"
    val password : CharArray = keyPass.toCharArray()


    @SuppressLint("HardwareIds")
    fun encrypt(value: String?): String? {
        return try {
            val bytes = if (value != null) value.toByteArray(Charsets.UTF_8) else ByteArray(0)
            val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
            val key: SecretKey = keyFactory.generateSecret(PBEKeySpec(password))
            val pbeCipher: Cipher = Cipher.getInstance("PBEWithMD5AndDES")
            pbeCipher.init(
                Cipher.ENCRYPT_MODE,
                key,
                PBEParameterSpec(
                    Settings.Secure.getString(
                        context.contentResolver,
                        Settings.System.ANDROID_ID
                    ).toByteArray(Charsets.UTF_8), 20
                )
            )
            String(Base64.encode(pbeCipher.doFinal(bytes), Base64.NO_WRAP),Charsets.UTF_8)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @SuppressLint("HardwareIds")
    fun decrypt(value: String?): String? {
        return try {
            val bytes = if (value != null) Base64.decode(value, Base64.DEFAULT) else ByteArray(0)
            val keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
            val key = keyFactory.generateSecret(PBEKeySpec(password))
            val pbeCipher = Cipher.getInstance("PBEWithMD5AndDES")
            pbeCipher.init(
                Cipher.DECRYPT_MODE,
                key,
                PBEParameterSpec(
                    Settings.Secure.getString(
                        context.contentResolver,
                        Settings.System.ANDROID_ID
                    ).toByteArray(Charsets.UTF_8), 20
                )
            )
            String(pbeCipher.doFinal(bytes), Charsets.UTF_8)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }



}