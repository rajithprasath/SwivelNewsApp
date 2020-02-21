package com.rajith.codetest.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ProfileViewModel : ViewModel() {

    class Factory @Inject constructor(
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                return ProfileViewModel(
                ) as T
            }
            throw IllegalArgumentException("ViewModel not found")
        }
    }
    val isRegisteredSuccess = MutableLiveData<Boolean>()

    fun onLoginClicked(email:String,password: String) {
        isRegisteredSuccess.value = isInputDataValid(email,password)
    }

    private fun isInputDataValid(email:String?,password: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches() && password.length > 5
    }


}

