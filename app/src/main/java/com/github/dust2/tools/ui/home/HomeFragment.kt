package com.github.dust2.tools.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.dust2.tools.R
import com.github.dust2.tools.databinding.FragmentHomeBinding
import com.github.dust2.tools.ui.AboutActivity
import com.github.dust2.tools.ui.BestCdnIpActivity
import com.github.dust2.tools.ui.CfWarpActivity
import com.github.dust2.tools.ui.StunActivity
import com.github.dust2.tools.util.Utils
import com.github.dust2.tools.util.onMainDispatcher
import com.github.dust2.tools.util.runOnIoDispatcher
import libcore.Libcore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.layoutBestCdnIp.setOnClickListener {
            startActivity(Intent(requireContext(), BestCdnIpActivity::class.java))
        }

        binding.layoutWarpGenerate.setOnClickListener {
            startActivity(Intent(requireContext(), CfWarpActivity::class.java))
        }

        binding.layoutStunTest.setOnClickListener {
            startActivity(Intent(requireContext(), StunActivity::class.java))
        }

//        binding.layoutScanVpnApps.setOnClickListener {
//            startActivity(Intent(requireContext(), VPNScanerActivity::class.java))
//        }

        binding.layoutAbout.setOnClickListener {
            startActivity(Intent(requireContext(), AboutActivity::class.java))
        }

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}