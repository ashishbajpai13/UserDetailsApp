package com.android.userdat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "user_details")
@Entity(tableName = "UserDetailsTable")
data class UserDetails(
    @PrimaryKey @ColumnInfo(name = "id") val userId: Int,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "email_id") val emailId: String?,
    @ColumnInfo(name = "contact_no") val contactNo: String?,
    @ColumnInfo(name = "date_of_birth") val dateOfBirth: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "state") val state: String?,
    @ColumnInfo(name = "country") val country: String?,
    @ColumnInfo(name = "pincode") val pincode: String?
) {
    override fun toString(): String {
        return "User Details: \n\n" +
                " Username = $userName\n"+
                " Email = $emailId\n" +
                " Contact number = $contactNo\n" +
                " Date of birth = $dateOfBirth\n" +
                " Address = $address\n" +
                " City = $city\n" +
                " State = $state\n" +
                " Country = $country\n" +
                " Pincode = $pincode"
    }
}