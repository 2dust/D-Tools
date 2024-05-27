package com.github.dust2.tools.dto.cf

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class RegisterRequest(
    @SerializedName("fcm_token") var fcmToken: String = "",
    @SerializedName("install_id") var installedId: String = "",
    var key: String = "",
    var locale: String = "",
    var model: String = "",
    var tos: String = "",
    var type: String = ""
) {

    companion object {
        fun newRequest(publicKey: String): String {
            val request = RegisterRequest()
            request.fcmToken = ""
            request.installedId = ""
            request.key = publicKey
            request.locale = "en_US"
            request.model = "PC"
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'000000'+08:00", Locale.US)
            request.tos = format.format(Date())
            request.type = "Android"
            return Gson().toJson(request)
        }
    }
}