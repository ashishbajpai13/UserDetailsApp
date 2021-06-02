package com.android.userdat.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.android.userdat.model.UserDetails
import com.android.userdat.model.UserDetailsAndKYC
import com.android.userdat.model.UserKYC
import com.android.userdat.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository?): ViewModel() {

    fun insertUserDetails(userDetails: UserDetails) = viewModelScope.launch {
        userRepository?.insertUserDetails(userDetails)
    }

    fun updateUserDetails(userDetails: UserDetails) = viewModelScope.launch {
        userRepository?.updateUserDetails(userDetails)
    }

    fun insertUserKYCDetails(userKYC: UserKYC) = viewModelScope.launch {
        userRepository?.insertUserKYCDetails(userKYC)
    }

    fun updateUserKYCDetails(userKYC: UserKYC) = viewModelScope.launch {
        userRepository?.updateUserKYCDetails(userKYC)
    }

    fun getUserDetails(userId: Int): LiveData<UserDetails>? = userRepository?.getUserDetails(userId = userId)?.asLiveData()

    fun getUserKYCDetails(userId: Int): LiveData<UserKYC>? = userRepository?.getUserKYCDetails(userId = userId)?.asLiveData()

    fun getUsersDetailsAndKYC(): LiveData<UserDetailsAndKYC>? = userRepository?.getUsersDetailsAndKYC()?.asLiveData()
}