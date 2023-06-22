package com.project.plantapp.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.plantapp.model.Articles
import com.project.plantapp.model.Species
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale

class DataApp private constructor() {
    companion object {
        @Volatile
        private var instance: DataApp? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: DataApp().also { instance = it }
        }
    }

    private var _db = FirebaseFirestore.getInstance()
    private var _articles = _db.collection("articles")
    private var _species = _db.collection("species")
    private var _plants = _db.collection("plants")
    private var _users = _db.collection("users")
    private var _auth = FirebaseAuth.getInstance()

    suspend fun getArticles(): ArrayList<Articles> {
        val articles = ArrayList<Articles>()
        _articles.get().addOnSuccessListener { result ->
            val sfd = SimpleDateFormat("dd-MM-yyyy", Locale("VN"))
            for (document in result) {
                val timestamp = document.data["date"] as com.google.firebase.Timestamp
                articles.add(
                    Articles(
                        document.id,
                        document.data["title"] as String,
                        document.data["author"] as String,
                        sfd.format(timestamp.toDate()).toString(),
                        document.data["img"] as String,
                        document.data["content"] as String,
                        false
                    )
                )
            }
        }.await()

        return articles
    }


    suspend fun getSpeciesCategory(): ArrayList<String> {
        val speciesCategory = ArrayList<String>()
        _species.get().addOnSuccessListener { result ->
            for (document in result) {
                speciesCategory.add(document.id)
            }
        }.await()


        return speciesCategory
    }

    suspend fun getSpecies(category: String): ArrayList<Species> {
        var speciesId: ArrayList<*> = ArrayList<String>()
        val plants = ArrayList<Species>()
        _species.document(category).get().addOnSuccessListener { document ->
            speciesId = document.data?.get("ids") as ArrayList<*>
        }.await()
        for (id in speciesId) {
            _plants.document(id as String).get().addOnSuccessListener {
                if (it.data != null) {
                    plants.add(
                        Species(
                            it.id,
                            it.data!!["name"] as String,
                            it.data!!["kingdom"] as String,
                            it.data!!["family"] as String,
                            it.data!!["description"] as String,
                            it.data!!["img"] as String,
                            false
                        )
                    )
                }
            }.await()
        }
        return plants
    }

    suspend fun getFavoriteSpecies(): ArrayList<Species> {
        val plants = ArrayList<Species>()
        for (id in getFavorite("species")) {
            _plants.document(id as String).get().addOnSuccessListener {
                if (it.data != null) {
                    plants.add(
                        Species(
                            it.id,
                            it.data!!["name"] as String,
                            it.data!!["kingdom"] as String,
                            it.data!!["family"] as String,
                            it.data!!["description"] as String,
                            it.data!!["img"] as String,
                            false
                        )
                    )
                }
            }.await()
        }

        return plants
    }

    suspend fun getFavoriteArticles(): ArrayList<Articles> {
        val articles = ArrayList<Articles>()
        for (id in getFavorite("articles")) {
            _articles.document(id as String).get().addOnSuccessListener {
                val sfd = SimpleDateFormat("dd-MM-yyyy", Locale("VN"))
                val timestamp = it.data!!["date"] as com.google.firebase.Timestamp
                articles.add(
                    Articles(
                        it.id,
                        it.data!!["title"] as String,
                        it.data!!["author"] as String,
                        sfd.format(timestamp.toDate()).toString(),
                        it.data!!["img"] as String,
                        it.data!!["content"] as String,
                        true
                    )
                )
            }.await()
        }
        return articles
    }


    private suspend fun getFavorite(category: String): ArrayList<*> {
        var favoriteArray: ArrayList<*> = ArrayList<String>()
        _auth.currentUser?.let {
            _db.collection("users").document(it.uid).get().addOnSuccessListener { document ->
                val mapFavorite = document.data?.get("favorite") as HashMap<*, *>
                favoriteArray = mapFavorite[category] as ArrayList<*>
            }

        }?.await()

        return favoriteArray

    }

}