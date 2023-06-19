package com.project.plantapp.viewmodel


import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.properties.Delegates


class UserVM : ViewModel() {
    private var _isSuccessEvent: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessEvent: LiveData<Boolean>
        get() = _isSuccessEvent

    private var _isMessageEvent: MutableLiveData<String> = MutableLiveData()
    val isMessageEvent: LiveData<String>
        get() = _isMessageEvent

    private var _auth: FirebaseAuth = FirebaseAuth.getInstance()

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
                    _isMessageEvent.postValue(task.exception.toString())
                }
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

    private fun getUserProfile() {
        _auth.currentUser?.let {
            name = it.displayName.toString()
            email = it.email.toString()
            photoUrl = it.photoUrl!!
            emailVerified = it.isEmailVerified


        }
    }


    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 8
    }
}




