package com.project.plantapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Handler(Looper.getMainLooper()).post{
            if (onBoardingIsFinished()) {
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }else
            {
                findNavController().navigate(R.id.action_homeFragment_to_onBoardingFragment)
            }
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun onBoardingIsFinished(): Boolean{
        val sharePreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharePreferences.getBoolean("finished", false)
    }
}