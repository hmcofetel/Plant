package com.project.plantapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentHomeBinding
import com.project.plantapp.databinding.FragmentMainBinding
import com.project.plantapp.viewmodel.UserVM


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: UserVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserVM::class.java]

        getPermission()
        Handler(Looper.getMainLooper()).post {
            if (!onBoardingIsFinished()) {
                binding.root.findNavController()
                    .navigate(R.id.action_mainFragment_to_onBoardingFragment)
            } else if (viewModel.getCurrentUser() == null) {
                findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
            } else {
                Log.v("check: ","==========")
                findNavController().navigate(R.id.action_mainFragment_to_homeFragment)
            }

        }
        return binding.root
    }

    private fun onBoardingIsFinished(): Boolean {
        val sharePreferences =
            requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharePreferences.getBoolean("finished", false)
    }

    private fun getPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )

        }
    }
}