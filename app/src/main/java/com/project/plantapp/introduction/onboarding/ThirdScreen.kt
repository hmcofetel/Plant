package com.project.plantapp.introduction.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.project.plantapp.R


class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_on_boarding_third_screen, container, false)

        val finish = view.findViewById<MaterialButton>(R.id.onboardingThirdScreenBnt)

        finish.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
            onBoardingIsFinished()
        }

        return view
    }

    private fun onBoardingIsFinished(){
        val sharePreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharePreferences.edit()
        editor.putBoolean("finished", true)
        editor.apply()
    }

}