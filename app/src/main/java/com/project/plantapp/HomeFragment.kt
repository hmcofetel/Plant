package com.project.plantapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.project.plantapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        Handler(Looper.getMainLooper()).post{
            if(!onBoardingIsFinished()){
                binding.root.findNavController().navigate(R.id.action_homeFragment_to_onBoardingFragment)
            }
            else
            {
                val navHostChild = childFragmentManager.findFragmentById(R.id.fragmentProfileContainerView) as NavHostFragment
                val navControllerChild = navHostChild.navController
                binding.bottomNavigationView.setupWithNavController(navControllerChild)
            }

//            if (onBoardingIsFinished()) {
//                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
//            }else
//            {
//                findNavController().navigate(R.id.action_homeFragment_to_onBoardingFragment)
//            }
        }



        return binding.root
    }

    private fun onBoardingIsFinished(): Boolean{
        val sharePreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharePreferences.getBoolean("finished", false)
    }
}