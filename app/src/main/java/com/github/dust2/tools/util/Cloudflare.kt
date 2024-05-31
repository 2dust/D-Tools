package com.github.dust2.tools.util

import android.util.Base64
import com.github.dust2.tools.dto.SingBoxBean
import com.github.dust2.tools.dto.cf.DeviceResponse
import com.github.dust2.tools.dto.cf.RegisterRequest
import com.github.dust2.tools.dto.cf.UpdateDeviceRequest
import libcore.Libcore
import com.google.gson.Gson

// from wgcf
object Cloudflare {

    private const val API_URL = "https://api.cloudflareclient.com"
    private const val API_VERSION = "v0a1922"

    private const val CLIENT_VERSION_KEY = "CF-Client-Version"
    private const val CLIENT_VERSION = "a-6.3-1922"

    fun makeWireGuardConfiguration(): SingBoxBean.OutboundBean? {
        val keyPair = Libcore.newWireGuardKeyPair()
        val client = Libcore.newHttpClient().apply {
            pinnedTLS12()
        }

        try {
            val response = client.newRequest().apply {
                setMethod("POST")
                setURL("$API_URL/$API_VERSION/reg")
                setHeader(CLIENT_VERSION_KEY, CLIENT_VERSION)
                setHeader("Accept", "application/json")
                setHeader("Content-Type", "application/json")
                setContentString(RegisterRequest.newRequest(keyPair.publicKey))
                setUserAgent("okhttp/3.12.1")
            }.execute()

            Logs.d(response.contentString)
            val device = Gson().fromJson(response.contentString, DeviceResponse::class.java)
            val accessToken = device.token

            client.newRequest().apply {
                setMethod("PATCH")
                setURL(API_URL + "/" + API_VERSION + "/reg/" + device.id + "/account/reg/" + device.id)
                setHeader("Accept", "application/json")
                setHeader("Content-Type", "application/json")
                setHeader("Authorization", "Bearer $accessToken")
                setHeader(CLIENT_VERSION_KEY, CLIENT_VERSION)
                setContentString(UpdateDeviceRequest.newRequest())
                setUserAgent("okhttp/3.12.1")
            }.execute()

            val peer = device.config.peers[0]
            val localAddresses = device.config.interfaceX.addresses
            return SingBoxBean.OutboundBean(
                private_key = keyPair.privateKey,
                peer_public_key = peer.publicKey,
                server = peer.endpoint.host.substringBeforeLast(":"),
                server_port = peer.endpoint.host.substringAfterLast(":").toInt(),
                local_address = listOf(localAddresses.v4 + "/32", localAddresses.v6 + "/128"),
                mtu = 1280,
                reserved = genReserved(device.config.clientId)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            client.close()
        }
    }

    private fun genReserved(anyStr: String): List<Int>? {
        try {
            val data = Base64.decode(anyStr, Base64.NO_WRAP)
            val res = ArrayList<Int>()
            for (b in data) {
                res.add(String.format("%02x", b).toInt(16))
            }
            return res
        } catch (e: Exception) {
            return null
        }
    }

}
