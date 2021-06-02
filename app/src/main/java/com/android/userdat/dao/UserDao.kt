package com.android.userdat.dao

import androidx.room.*
import com.android.userdat.model.UserDetails
import com.android.userdat.model.UserDetailsAndKYC
import com.android.userdat.model.UserKYC
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert //(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetails(vararg users: UserDetails)

    @Query("SELECT * FROM USERDETAILSTABLE WHERE id LIKE :userId")
    fun getUserDetails(userId: Int): Flow<UserDetails>

    @Update
    suspend fun updateUser(vararg users: UserDetails)

    @Delete
    suspend fun deleteUserDetails(user: UserDetails)

    @Insert
    suspend fun insertUserKYCDetails(vararg users: UserKYC)

    @Query("SELECT * FROM USERKYCTABLE WHERE user_id LIKE :userId")
    fun getUserKYCDetails(userId: Int): Flow<UserKYC>

    @Update
    suspend fun updateUserKYC(vararg userKyc: UserKYC)

    @Delete
    suspend fun deleteUserKYCDetails(user: UserKYC)

    @Transaction
    @Query("SELECT * FROM USERDETAILSTABLE")
    fun getUsersDetailsAndKYC(): Flow<UserDetailsAndKYC>
}