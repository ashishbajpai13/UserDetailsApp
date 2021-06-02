package com.android.userdat.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.android.userdat.R
import com.android.userdat.UserDatApplication
import com.android.userdat.databinding.ActivityUserKycdetailsBinding
import com.android.userdat.db.UserDatabase
import com.android.userdat.model.UserKYC
import com.android.userdat.util.AppConstants
import com.android.userdat.util.CryptoUtil
import com.android.userdat.util.Validator.isFieldNotEmpty
import com.android.userdat.util.showToast
import com.android.userdat.viewmodels.UserViewModel
import com.android.userdat.viewmodels.ViewModelFactory

class UserKYCDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserKycdetailsBinding
    private var toUpdate: Boolean = false
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory((application as UserDatApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserKycdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.backIv.setOnClickListener {
            onBackPressed()
        }
        val userDb = UserDatabase.getInstance(this)
        binding.submitBtn.setOnClickListener {
            if (binding.gstinTIL.isFieldNotEmpty() &&
                binding.panNoTIL.isFieldNotEmpty() &&
                binding.aadharNumTIL.isFieldNotEmpty() &&
                binding.dLTIL.isFieldNotEmpty() &&
                binding.voterIDTIL.isFieldNotEmpty() &&
                binding.upiIDTIL.isFieldNotEmpty()
            ) {
                val userKYCDetails = UserKYC(
                    userId = 1001,
                    gstinNo = CryptoUtil.aesEncrypt(binding.gstinTextBox.text.toString().trim(), AppConstants.SECRET_KEY),
                    panNo = CryptoUtil.aesEncrypt(binding.panNoTextBox.text.toString().trim(), AppConstants.SECRET_KEY),
                    aadharNo = CryptoUtil.aesEncrypt(binding.aadharNumTextBox.text.toString().trim(), AppConstants.SECRET_KEY),
                    drivingLicence = CryptoUtil.aesEncrypt(binding.dLTextBox.text.toString().trim(), AppConstants.SECRET_KEY),
                    voterId = CryptoUtil.aesEncrypt(binding.voterIDTextBox.text.toString().trim(), AppConstants.SECRET_KEY),
                    upiId = CryptoUtil.aesEncrypt(binding.upiIDTextBox.text.toString().trim(), AppConstants.SECRET_KEY)
                )
                when(toUpdate) {
                    true -> userViewModel.updateUserKYCDetails(userKYCDetails)
                    false -> userViewModel.insertUserKYCDetails(userKYCDetails)
                }
                showToast(this.getString(R.string.data_updated))
                onBackPressed()
            }
        }
        userViewModel.getUserKYCDetails(1001)?.observe(this, {
            if (it != null) {
                toUpdate = true
                binding.gstinTextBox.setText(CryptoUtil.aesDecrypt(it.gstinNo  ?: "", AppConstants.SECRET_KEY))
                binding.panNoTextBox.setText(CryptoUtil.aesDecrypt(it.panNo  ?: "", AppConstants.SECRET_KEY))
                binding.aadharNumTextBox.setText(CryptoUtil.aesDecrypt(it.aadharNo  ?: "", AppConstants.SECRET_KEY))
                binding.dLTextBox.setText(CryptoUtil.aesDecrypt(it.drivingLicence  ?: "", AppConstants.SECRET_KEY))
                binding.voterIDTextBox.setText(CryptoUtil.aesDecrypt(it.voterId  ?: "", AppConstants.SECRET_KEY))
                binding.upiIDTextBox.setText(CryptoUtil.aesDecrypt(it.upiId  ?: "", AppConstants.SECRET_KEY))
            }
        })
    }
}