package com.project.plantapp

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.project.plantapp.databinding.FragmentSpeciesDetailBinding
import com.project.plantapp.util.GlideApp
import com.project.plantapp.viewmodel.UserVM


class SpeciesDetailFragment : Fragment() {
    private lateinit var binding: FragmentSpeciesDetailBinding
    private lateinit var userVm: UserVM
    private val args : SpeciesDetailFragmentArgs by navArgs()
    private val _plantRef = FirebaseStorage.getInstance().reference
    private var _db = FirebaseFirestore.getInstance()
    private var _usersDB = _db.collection("users")
    private var _auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var _liked: Boolean = false



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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userVm = ViewModelProvider(this)[UserVM::class.java]
        binding = FragmentSpeciesDetailBinding.inflate(inflater, container, false)
        binding.apply {

            speciesDetailBackBnt.setOnClickListener{
                findNavController().popBackStack()
            }
            tvSpecieDetailTitle.text = args.title
            familyContentDetail.text = args.family
            kingdomContentDetail.text = args.kingdom
            descriptionContentDetail.text = args.description
            GlideApp.with(ivSpecieDetailTitle.context).load(_plantRef.child("plants").child(args.image)).centerCrop().into(ivSpecieDetailTitle)


            addSpecieFavoriteBnt.setOnClickListener {
                if (_liked) {
                    userVm.removeFavorite(args.id, "species")
                } else {
                    userVm.addFavorite(args.id, "species")
                }

            }


            val docRef = _auth.currentUser?.let { _db.collection("users").document(it.uid) }
            docRef?.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val favMap = snapshot.data?.get("favorite") as HashMap<*, *>
                    val favArray = favMap["species"] as ArrayList<*>
                    _liked = favArray.contains(args.id)
                    if (_liked) {
                        addSpecieFavoriteBnt.background.setTint(Color.parseColor("#FF6262"))
                    } else {
                        addSpecieFavoriteBnt.background.setTint(Color.parseColor("#5C5C5C"))
                    }

                }
            }
        }
        // Inflate the layout for this fragment

        return binding.root
    }

}