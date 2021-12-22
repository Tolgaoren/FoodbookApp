package com.toren.foodbookapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private val auth = Firebase.auth
    var control = MutableLiveData(false)
    var isLogin = MutableLiveData(false)

    fun loginAccount(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        control.value = true
                    } else {
                        Log.d("TAG", "signInWithEmail:failure", task.exception)
                    }
                }
        }
    }

    fun isLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            if (auth.currentUser != null) {
                Log.d("TAG","MERHABALAR")
                withContext(Dispatchers.Main) {
                    isLogin.value = true
                }
            }
        }
    }

}