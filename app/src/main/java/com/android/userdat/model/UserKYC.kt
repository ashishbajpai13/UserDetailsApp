package com.android.userdat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "user_kyc")
@Entity(tableName = "UserKYCTable")
data class UserKYC(
    @PrimaryKey @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "gstin_no") val gstinNo: String?,
    @ColumnInfo(name = "pan_no") val panNo: String?,
    @ColumnInfo(name = "aadhar_no") val aadharNo: String?,
    @ColumnInfo(name = "driving_licence") val drivingLicence: String?,
    @ColumnInfo(name = "voter_id") val voterId: String?,
    @ColumnInfo(name = "upi_id") val upiId: String?
) {
    override fun toString(): String {
        return "User KYC Details:\n\n" +
                " GSTIN Number = $gstinNo\n" +
                " PAN Number = $panNo\n" +
                " Aadhar number = $aadharNo\n" +
                " Driving Licence = $drivingLicence\n" +
                " Voter Id = $voterId\n" +
                " UPI Id = $upiId"
    }
}