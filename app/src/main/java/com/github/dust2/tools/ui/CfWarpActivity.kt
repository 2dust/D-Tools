package com.github.dust2.tools.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.github.dust2.tools.R
import com.github.dust2.tools.databinding.LayoutCfWarpBinding
import com.github.dust2.tools.databinding.LayoutProgressBinding
import com.github.dust2.tools.dto.SingBoxBean
import com.github.dust2.tools.util.Cloudflare
import com.github.dust2.tools.util.Utils
import com.github.dust2.tools.util.onMainDispatcher
import com.github.dust2.tools.util.runOnDefaultDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import libcore.Libcore
import java.util.HashMap

class CfWarpActivity : BaseActivity() {

    private lateinit var binding: LayoutCfWarpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.cloudflare_wrap)
        binding = LayoutCfWarpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.warpGenerate.setOnClickListener {
            generateWarpConfiguration(this)
        }
    }


    private fun generateWarpConfiguration(context: Context) {
        binding.idProgress.isVisible = true
        binding.warpGenerate.isVisible = false
        runOnDefaultDispatcher {
            val bean = Cloudflare.makeWireGuardConfiguration()
            onMainDispatcher {
                if (bean != null) {
                    binding.warpResult.text = bean.toString()
                    Utils.setClipboard(context, genShare(bean))
                } else {
                    binding.warpResult.text = getString(R.string.toast_failure)
                }

                binding.idProgress.isVisible = false
                binding.warpGenerate.isVisible = true
            }
        }
    }

    private fun genShare(outbound: SingBoxBean.OutboundBean): String {
        val remark = "#" + Utils.urlEncode("Warp")

        val dicQuery = HashMap<String, String>()
        dicQuery["publickey"] =
            Utils.urlEncode(outbound.peer_public_key.toString())
        if (outbound.reserved != null) {
            dicQuery["reserved"] = Utils.urlEncode(
                Utils.removeWhiteSpace(outbound.reserved?.joinToString())
                    .toString()
            )
        }
        dicQuery["address"] = Utils.urlEncode(
            Utils.removeWhiteSpace((outbound.local_address as List<*>).joinToString())
                .toString()
        )
        if (outbound.mtu != null) {
            dicQuery["mtu"] = outbound.mtu.toString()
        }
        val query = "?" + dicQuery.toList().joinToString(
            separator = "&",
            transform = { it.first + "=" + it.second })

        val url = String.format(
            "%s@%s:%s",
            Utils.urlEncode(outbound.private_key ?: ""),
            Utils.getIpv6Address(outbound.server),
            outbound.server_port
        )
        return "wireguard://" + url + query + remark

    }

}