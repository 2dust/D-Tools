package com.github.dust2.tools.dto.cf

import com.google.gson.Gson

data class UpdateDeviceRequest(
    var name: String, var active: Boolean
) {
    companion object {
        fun newRequest(name: String = "Client", active: Boolean = true) =
            Gson().toJson(UpdateDeviceRequest(name, active))
    }
}