package com.github.dust2.tools.ui

import android.os.Bundle
import androidx.core.view.isVisible
import com.github.dust2.tools.AppConfig
import com.github.dust2.tools.R
import com.github.dust2.tools.databinding.LayoutStunBinding
import com.github.dust2.tools.util.MmkvManager
import com.github.dust2.tools.util.Utils
import com.github.dust2.tools.util.onMainDispatcher
import com.github.dust2.tools.util.runOnDefaultDispatcher
import libcore.Libcore

class StunActivity : BaseActivity() {

    private lateinit var binding: LayoutStunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTitle(R.string.stun_test)
        binding = LayoutStunBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.natStunServer.text = Utils.getEditable(
            MmkvManager.getSetting()
                .decodeString(AppConfig.NAT_STUN_SERVER, "stun.syncthing.net:3478")
        )

        binding.stunTest.setOnClickListener {
            doTest()
        }

        binding.natResult.text =
            MmkvManager.getSetting().decodeString(AppConfig.NAT_STUN_RESULT, "")
    }

    private fun doTest() {
        binding.natResult.text = ""
        binding.idProgress.isVisible = true
        binding.stunTest.isVisible = false

        val server = binding.natStunServer.text.toString()
        MmkvManager.getSetting().encode(AppConfig.NAT_STUN_SERVER, server)

        runOnDefaultDispatcher {
            val result =
                try {
                    Libcore.stunTest(server)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ""
                }
            onMainDispatcher {
                binding.natResult.text = result
                MmkvManager.getSetting().encode(AppConfig.NAT_STUN_RESULT, result)
                binding.idProgress.isVisible = false
                binding.stunTest.isVisible = true
            }
        }
    }

}