package com.project.plantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.plantapp.databinding.FragmentProfileDetailBinding


class DetailProfileFragment : Fragment(), ProfileDetailItemAdapt.MyClickListener {
    private lateinit var detailList: ArrayList<ProfileDetailItem>
    private lateinit var adapt: ProfileDetailItemAdapt
    private lateinit var binding:FragmentProfileDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileDetailBinding.inflate(inflater, container, false)
        detailList = ArrayList()
        detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_location,"My Address"))
        detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_notification,"Notifications"))
        detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_bookmark,"My Species"))
        detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_chat,"My Article"))
        detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_star,"My Favorites"))
        detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_settings,"Settings"))
        detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_logout,"Logout"))

        adapt = ProfileDetailItemAdapt(detailList, this@DetailProfileFragment)
        binding.profileDetailListItem.adapter = adapt
        binding.profileDetailListItem.setHasFixedSize(true)
        binding.profileDetailListItem.layoutManager = LinearLayoutManager(activity)


        binding.profileDetailBackBnt.setOnClickListener{
            binding.root.findNavController().navigate(R.id.action_detailProfileFragment_to_mainProfileFragment)
        }


        return binding.root
    }

    override fun onClick(position: Int) {
        when(position){
            2 -> binding.root.findNavController().navigate(R.id.action_detailProfileFragment_to_mySpeciesFragment)
            3 -> binding.root.findNavController().navigate(R.id.action_detailProfileFragment_to_myArticlesFragment)
            4 -> binding.root.findNavController().navigate(R.id.action_detailProfileFragment_to_favoriteFragment)
        }
        requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =View.GONE

    }

}