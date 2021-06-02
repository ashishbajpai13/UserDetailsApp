package com.android.userdat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.userdat.dao.UserDao
import com.android.userdat.model.UserDetails
import com.android.userdat.model.UserKYC


@Database(entities = [UserDetails::class, UserKYC::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: UserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): UserDatabase? {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "Demo.db"
                ).build().apply {
                    instance = this
                }
            }
        }
    }
}