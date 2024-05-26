package com.github.dust2.tools.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.dust2.tools.databinding.FragmentHomeBinding
import com.github.dust2.tools.ui.VPNScanerActivity
import com.github.dust2.tools.util.Utils

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

        binding.layoutScanVpnApps.setOnClickListener {
            startActivity(Intent(requireContext(), VPNScanerActivity::class.java))
        }

        binding.layoutSourceCode.setOnClickListener {
            Utils.openUri(activity, "https://github.com/2dust/D-Tools")
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