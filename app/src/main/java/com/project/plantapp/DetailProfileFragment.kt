package com.project.plantapp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.project.plantapp.adapter.ProfileDetailItemAdapt
import com.project.plantapp.databinding.FragmentProfileDetailBinding
import com.project.plantapp.model.ProfileDetailItem
import com.project.plantapp.util.GlideApp
import com.project.plantapp.viewmodel.UserVM


class DetailProfileFragment : Fragment(), ProfileDetailItemAdapt.MyClickListener {
    private lateinit var _detailList: ArrayList<ProfileDetailItem>
    private lateinit var _adapt: ProfileDetailItemAdapt
    private lateinit var _binding: FragmentProfileDetailBinding
    private lateinit var _viewModel: UserVM
    private val _db = FirebaseStorage.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDetailBinding.inflate(inflater, container, false)
        _viewModel = ViewModelProvider(this)[UserVM::class.java]
        _detailList = ArrayList()
        _detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_location, "My Address"))
        _detailList.add(
            ProfileDetailItem(
                R.drawable.ic_profile_detail_notification, "Notifications"
            )
        )
        _detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_bookmark, "My Species"))
        _detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_chat, "Favorites Article"))
        _detailList.add(ProfileDetailItem(R.drawable.ic_yard, "Favorites Plants"))
        _detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_settings, "Settings"))
        _detailList.add(ProfileDetailItem(R.drawable.ic_profile_detail_logout, "Logout"))

        _adapt = ProfileDetailItemAdapt(_detailList, this@DetailProfileFragment)
        _binding.profileDetailListItem.adapter = _adapt
        _binding.profileDetailListItem.setHasFixedSize(true)
        _binding.profileDetailListItem.layoutManager = LinearLayoutManager(activity)




        _binding.tvNameProfile.text = _viewModel.getProfile()?.get("name") as CharSequence?
        _binding.tvEmailProfile.text = _viewModel.getProfile()?.get("email") as CharSequence?
        _binding.profileDetailBackBnt.setOnClickListener {
            findNavController().popBackStack()
//            _binding.root.findNavController().navigate(R.id.mainProfileFragment)
        }
        _viewModel.loadProfileStograte()
        _viewModel.isProfileEvent.observe(viewLifecycleOwner) {
            GlideApp.with(requireContext()).load(_db.child("avatars").child(it["avt"] as String))
                .centerCrop().into(_binding.ivAvtProfile)
        }



        return _binding.root
    }

    override fun onClick(position: Int) {
        when (position) {

            2 -> _binding.root.findNavController()
                .navigate(R.id.action_detailProfileFragment_to_myArticlesFragment)

            3 -> _binding.root.findNavController()
                .navigate(R.id.action_detailProfileFragment_to_mySpeciesFragment)

            4 -> _binding.root.findNavController()
                .navigate(R.id.action_detailProfileFragment_to_favoriteFragment)

            6 -> {
                _viewModel.signOut()
                requireActivity().findNavController(R.id.fragmentPlantContainerView)
                    .navigate(R.id.mainFragment)
            }


        }
        requireActivity().findViewById<CoordinatorLayout>(R.id.coordinatorLayout).visibility =
            View.GONE

    }

}