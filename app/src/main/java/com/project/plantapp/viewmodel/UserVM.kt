package com.project.plantapp.viewmodel


import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.merge
import kotlin.properties.Delegates




class UserVM : ViewModel() {
    private var _isSuccessEvent: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessEvent: LiveData<Boolean>
        get() = _isSuccessEvent

    private var _isMessageEvent: MutableLiveData<String> = MutableLiveData()
    val isMessageEvent: LiveData<String>
        get() = _isMessageEvent

    private var _auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var _db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return _auth.currentUser
    }


    private lateinit var name: String
    private lateinit var email: String
    private lateinit var photoUrl: Uri
    private var emailVerified by Delegates.notNull<Boolean>()
    private lateinit var uid: String


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
            _isMessageEvent.postValue("password không hợp lệ")
            return false
        }

        return true

    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        if (!checkEmailAndPassword(email, password)) return

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

    fun addFavorite(id : String, category: String)
    {
        _auth.currentUser?.let {
            _db.collection("users").document(it.uid).set(
                hashMapOf(
                    "favorite" to
                            hashMapOf(
                                category to FieldValue.arrayUnion(id)
                            )
                ), SetOptions.merge()
            )
        }
    }


    fun removeFavorite(id : String, category: String)
    {
        _auth.currentUser?.let {
            _db.collection("users").document(it.uid).set(
                hashMapOf(
                    "favorite" to
                            hashMapOf(
                                category to FieldValue.arrayRemove(id)
                            )
                ), SetOptions.merge()
            )
        }
    }



    fun signOut(){
        _auth.signOut()
    }



    private fun checkConfirmPassword(password: String, confrim_password: String): Boolean {
        if (password != confrim_password) {
            _isMessageEvent.postValue("Password does not match")
            return false
        }
        return true
    }

    fun updateProfile(firstName: String, lastName: String)
    {
        val profileUpdates = UserProfileChangeRequest.Builder()
        profileUpdates.displayName = "$lastName $firstName"
        _auth.currentUser?.updateProfile(profileUpdates.build())
        createUserFireStore(firstName, lastName)
    }

    private fun createUserFireStore(firstName: String, lastName: String) {
        _auth.currentUser?.let {
            Log.v("hmco: ", it.email.toString())
            Log.v("hmco: ", it.displayName.toString())
            Log.v("hmco: ", it.uid)
            Log.v("hmco: ", it.photoUrl.toString())
            Log.v("hmco: ", firstName)
            Log.v("hmco: ", lastName)

            val profile = hashMapOf(
                "email" to it.email,
                "first" to firstName,
                "last" to lastName,
            )

            val favorite = hashMapOf(
                "articles" to emptyList<String>(),
                "species" to emptyList()
            )
            _db.collection("users").document(it.uid).set(hashMapOf("profile" to profile))
            _db.collection("users").document(it.uid).set(hashMapOf("favorite" to favorite),
                SetOptions.merge())
        }
    }


    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 8
    }
}




