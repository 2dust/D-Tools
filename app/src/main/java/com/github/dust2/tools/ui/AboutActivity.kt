package com.github.dust2.tools.ui

import android.os.Bundle
import com.github.dust2.tools.BuildConfig
import com.github.dust2.tools.R
import com.github.dust2.tools.databinding.ActivityAboutBinding
import com.github.dust2.tools.util.Utils

class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        title = getString(R.string.title_about)


        binding.layoutSoureCcode.setOnClickListener {
            Utils.openUri(this, "https://github.com/2dust/D-Tools")
        }

        binding.layoutFeedback.setOnClickListener {
            Utils.openUri(this, "https://github.com/2dust/D-Tools/issues")
        }

        binding.layoutTgChannel.setOnClickListener {
            Utils.openUri(this, "https://t.me/github_2dust")
        }

        binding.layoutPrivacyPolicy.setOnClickListener {
            Utils.openUri(this, "https://raw.githubusercontent.com/2dust/D-Tools/main/CR.md")
        }

        "v${BuildConfig.VERSION_NAME}".also {
            binding.tvVersion.text = it
        }
    }

}