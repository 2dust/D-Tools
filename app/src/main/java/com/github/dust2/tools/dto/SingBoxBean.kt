package com.github.dust2.tools.dto

data class SingBoxBean(
    var dns: Dns?,
    var experimental: Experimental?,
    var inbounds: List<Inbound?>?,
    var log: Log?,
    var outbounds: List<OutboundBean?>?,
    var route: Route?
) {
    data class Dns(
        var rules: List<Rule?>?,
        var servers: List<Server?>?
    ) {
        data class Rule(
            var disable_cache: Boolean?,
            var rule_set: List<String?>?,
            var server: String?
        )

        data class Server(
            var address: String?,
            var detour: String?,
            var strategy: String?,
            var tag: String?
        )
    }

    data class Experimental(
        var cache_file: CacheFile?,
        var clash_api: ClashApi?
    ) {
        data class CacheFile(
            var enabled: Boolean?
        )

        data class ClashApi(
            var external_controller: String?
        )
    }

    data class Inbound(
        var listen: String?,
        var listen_port: Int?,
        var sniff: Boolean?,
        var sniff_override_destination: Boolean?,
        var tag: String?,
        var type: String?
    )

    data class Log(
        var level: String?,
        var timestamp: Boolean?
    )

    data class OutboundBean(
        var local_address: List<String?>?,
        var mtu: Int?,
        var peer_public_key: String?,
        var private_key: String?,
        var reserved: List<Int?>?,
        var server: String,
        var server_port: Int,
        var tag: String? = null,
        var type: String? = null
    )

    data class Route(
        var rule_set: List<RuleSet?>?,
        var rules: List<Rule?>?
    ) {
        data class RuleSet(
            var download_detour: String?,
            var format: String?,
            var tag: String?,
            var type: String?,
            var url: String?
        )

        data class Rule(
            var ip_is_private: Boolean?,
            var outbound: String?,
            var port_range: List<String?>?,
            var protocol: List<String?>?,
            var rule_set: List<String?>?
        )
    }
}