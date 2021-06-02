package com.android.userdat

import android.app.Application
import com.android.userdat.db.UserDatabase
import com.android.userdat.repository.UserRepository

class UserDatApplication : Application() {
    val database by lazy { UserDatabase.getInstance(this) }
    val repository by lazy { database?.let { database -> UserRepository( database.userDao()) } }
}