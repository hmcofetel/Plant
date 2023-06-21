package com.project.plantapp

import android.graphics.Color
import android.os.Bundle
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
import com.google.firebase.firestore.Source
import com.google.firebase.storage.FirebaseStorage
import com.project.plantapp.databinding.FragmentArticleDetailBinding
import com.project.plantapp.viewmodel.UserVM


class ArticleDetailFragment : Fragment() {
    private lateinit var binding: FragmentArticleDetailBinding
    private lateinit var userVm: UserVM
    private var _storageRef = FirebaseStorage.getInstance().reference
    private var _db = FirebaseFirestore.getInstance()
    private var _usersDB = _db.collection("users")
    private var _auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val args: ArticleDetailFragmentArgs by navArgs()
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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        userVm = ViewModelProvider(this)[UserVM::class.java]
        binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        binding.apply {
            articleDetailBackBnt.setOnClickListener {
                findNavController().popBackStack()
            }
            tvArticleDetailTitle.text = args.title
            tvDetailArticleDescription.text = args.description
            tvArticleDate.text = args.date

            _usersDB.document(args.author).get(Source.CACHE).addOnSuccessListener { document ->
                val profile = document.data?.get("profile") as HashMap<*, *>
                val author = "${profile["first"] as String} ${profile["last"] as String}"
                tvArticleDetailName.text = author

                Glide.with(ivArticleDetailAvatar.context)
                    .load(_storageRef.child("avatars").child(profile["avt"] as String)).centerCrop()
                    .into(ivArticleDetailAvatar)

            }


            Glide.with(ivArticleDetailTitle.context)
                .load(_storageRef.child("articles").child(args.img)).centerCrop()
                .into(ivArticleDetailTitle)



            addFavoriteBnt.setOnClickListener {
                if (_liked) {
                    userVm.removeFavorite(args.id, "articles")
                } else {
                    userVm.addFavorite(args.id, "articles")
                }

            }


            val docRef = _auth.currentUser?.let { _db.collection("users").document(it.uid) }
            docRef?.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val favMap = snapshot.data?.get("favorite") as HashMap<*, *>
                    val favArray = favMap["articles"] as ArrayList<*>
                    _liked = favArray.contains(args.id)
                    if (_liked) {
                        addFavoriteBnt.background.setTint(Color.parseColor("#FF6262"))
                    } else {
                        addFavoriteBnt.background.setTint(Color.parseColor("#5C5C5C"))
                    }

                }
            }

        }

        return binding.root
    }


}
