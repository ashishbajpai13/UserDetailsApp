package com.android.userdat.repository

import androidx.annotation.WorkerThread
import com.android.userdat.dao.UserDao
import com.android.userdat.model.UserDetails
import com.android.userdat.model.UserKYC
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    @WorkerThread
    suspend fun insertUserDetails(userDetails: UserDetails) {
        userDao.insertUserDetails(userDetails)
    }

    @WorkerThread
    suspend fun updateUserDetails(userDetails: UserDetails) {
        userDao.updateUser(userDetails)
    }

    @WorkerThread
    suspend fun insertUserKYCDetails(userKYC: UserKYC) {
        userDao.insertUserKYCDetails(userKYC)
    }

    @WorkerThread
    suspend fun updateUserKYCDetails(userKYC: UserKYC) {
        userDao.updateUserKYC(userKYC)
    }

    fun getUserDetails(userId: Int): Flow<UserDetails>? = userDao.getUserDetails(userId = userId)

    fun getUserKYCDetails(userId: Int) = userDao.getUserKYCDetails(userId = userId)

    fun getUsersDetailsAndKYC() = userDao.getUsersDetailsAndKYC()
}