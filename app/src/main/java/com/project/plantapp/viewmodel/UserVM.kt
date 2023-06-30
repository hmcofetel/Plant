package com.project.plantapp.viewmodel


import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.properties.Delegates


class UserVM : ViewModel() {
    private var _isSuccessEvent: MutableLiveData<Boolean> = MutableLiveData()
    private var _isProfileEvent: MutableLiveData<Map<*,*>> = MutableLiveData()
    private var _profile : Map<*, *>? = null
    val isSuccessEvent: LiveData<Boolean>
        get() = _isSuccessEvent

    val isProfileEvent: LiveData<Map<*,*>>
        get() = _isProfileEvent

    private var _isMessageEvent: MutableLiveData<String> = MutableLiveData()
    val isMessageEvent: LiveData<String>
        get() = _isMessageEvent

    private var _auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var _db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return _auth.currentUser
    }



    private fun checkEmailAndPassword(email: String, password: String): Boolean {
        //kiem tra format email
        val isValidEmail = isEmailValid(email)
        if (!isValidEmail) {
            _isMessageEvent.postValue("email không hợp lệ")
            return false
        }
        //password length > 8 && < 10
        val isValidPassword = isPasswordValid(password)
        if (!isValidPassword) {
            _isMessageEvent.postValue("Password phải gồm 11 kí tự,chứa kí tự in hoa, in thường và kí tự đặc biệt ")
            return false
        }

        return true

    }

    fun signInWithEmailAndPassword(email: String, password: String) {
//        if (!checkEmailAndPassword(email, password)) return

        _auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _isMessageEvent.postValue("Đã đăng nhập")
                _isSuccessEvent.postValue(true)
            } else {
                _isMessageEvent.postValue("Sai email hoặc mật khẩu")
            }
        }
    }


    fun createUserWithEmailAndPassword(email: String, password: String, confrim_password: String) {
        if (!checkEmailAndPassword(email, password)) return

        if (!checkConfirmPassword(password, confrim_password)) return

        _auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _isMessageEvent.postValue("Tạo tài khoản thành công")
                _isSuccessEvent.postValue(true)

            } else {
                _isMessageEvent.postValue(task.exception?.message)
            }
        }

    }

    fun addFavorite(id: String, category: String) {
        _auth.currentUser?.let {
            _db.collection("users").document(it.uid).set(
                hashMapOf(
                    "favorite" to hashMapOf(
                        category to FieldValue.arrayUnion(id)
                    )
                ), SetOptions.merge()
            )
        }
    }


    fun removeFavorite(id: String, category: String) {
        _auth.currentUser?.let {
            _db.collection("users").document(it.uid).set(
                hashMapOf(
                    "favorite" to hashMapOf(
                        category to FieldValue.arrayRemove(id)
                    )
                ), SetOptions.merge()
            )
        }
    }

    fun getProfile(): Map<*, *>?
    {
        if (_profile != null)
            return _profile as Map<*, *>

        _auth.currentUser?.let {
            _profile = mapOf(
                "uid" to it.uid,
                "email" to it.email,
                "name" to it.displayName
            )
        }
        return _profile
    }


    fun signOut() {
        _auth.signOut()
    }

    fun loadProfileStograte(){
        viewModelScope.launch {
            _auth.currentUser?.let {
                var profile : Map<*,*>
                _db.collection("users").document(it.uid) .get().addOnSuccessListener { document ->
                    profile = document.get("profile") as Map<*, *>
                    _isProfileEvent.postValue(profile)
                }
            }
        }

    }


    private fun checkConfirmPassword(password: String, confrim_password: String): Boolean {
        if (password != confrim_password) {
            _isMessageEvent.postValue("Password does not match")
            return false
        }
        return true
    }

    fun updateProfile(firstName: String, lastName: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = "$lastName $firstName"
//            photoUri = "dark.png"
        }
        _auth.currentUser?.updateProfile(profileUpdates)
        createUserFireStore(firstName, lastName)
    }

    private fun createUserFireStore(firstName: String, lastName: String) {
        _auth.currentUser?.let {
            val profile = hashMapOf(
                "email" to it.email,
                "first" to firstName,
                "last" to lastName,
                "avt" to "dark.png"
            )

            val favorite = hashMapOf(
                "articles" to emptyList<String>(), "species" to emptyList()
            )
            _db.collection("users").document(it.uid).set(hashMapOf("profile" to profile))
            _db.collection("users").document(it.uid).set(
                hashMapOf("favorite" to favorite), SetOptions.merge()
            )
        }
    }


    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val pattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@])[A-Za-z\\d@]{11}$")
        return pattern.matches(password)
    }
}




