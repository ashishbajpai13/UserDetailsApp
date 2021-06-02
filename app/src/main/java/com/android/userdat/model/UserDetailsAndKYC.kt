package com.android.userdat.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserDetailsAndKYC(
    @Embedded val userDetails: UserDetails?,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val userKYC: UserKYC?
)
