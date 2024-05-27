package com.github.dust2.tools.ui

import android.os.Bundle
import androidx.core.view.isVisible
import com.github.dust2.tools.R
import com.github.dust2.tools.databinding.LayoutStunBinding
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
        binding.natStunServer.text = Utils.getEditable("stun.syncthing.net:3478")

        binding.stunTest.setOnClickListener {
            doTest()
        }
    }

    private fun doTest() {
        binding.idProgress.isVisible = true
        binding.stunTest.isVisible = false
        runOnDefaultDispatcher {
            val result = Libcore.stunTest(binding.natStunServer.text.toString())
            onMainDispatcher {
                binding.natResult.text = result
                binding.idProgress.isVisible = false
                binding.stunTest.isVisible = true
            }
        }
    }

}