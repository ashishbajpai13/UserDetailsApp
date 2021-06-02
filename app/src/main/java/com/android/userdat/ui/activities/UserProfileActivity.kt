package com.android.userdat.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.android.userdat.R
import com.android.userdat.UserDatApplication
import com.android.userdat.databinding.ActivityUserProfileBinding
import com.android.userdat.databinding.CustomKeyValueViewBinding
import com.android.userdat.model.UserDetails
import com.android.userdat.model.UserKYC
import com.android.userdat.ui.interfaces.DownloadInterface
import com.android.userdat.util.*
import com.android.userdat.viewmodels.UserViewModel
import com.android.userdat.viewmodels.ViewModelFactory
import java.lang.StringBuilder

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private var userDetails: UserDetails? = null
    private var userKYCDetails: UserKYC? = null
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory((application as UserDatApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.backIv.setOnClickListener {
            onBackPressed()
        }
        binding.downloadBtn.setOnClickListener {
            if (checkStoragePermissionGranted()) {
                if (userDetails != null || userKYCDetails != null) {
                    val dataToWrite = StringBuilder()
                    userDetails?.apply {
                        dataToWrite.append(toString())
                    }
                    userKYCDetails?.apply {
                        dataToWrite.append("\n\n\n")
                        dataToWrite.append(toString())
                    }
                    FileUtils.writeAndSaveFile(data = dataToWrite.toString(), context = this,
                        object : DownloadInterface {
                            override fun onComplete(message: String?) {
                                showSnackBar(message = String.format(this@UserProfileActivity.resources.getString(R.string.file_downloaded_message), message ?: ""), view = binding.root)
                            }

                            override fun onDownloadFailed() {
                                showToast(this@UserProfileActivity.resources.getString(R.string.download_failed))
                            }
                        })
                } else {
                    showToast(this.resources.getString(R.string.no_data_found))
                }
            }
        }
        userViewModel.getUsersDetailsAndKYC()?.observe(this, {
            if (it?.userDetails != null) {
                binding.userDetailsContainerLayout.removeAllViews()
                it.userDetails.apply {
                    userDetails = this
                    binding.fillUserDetailsTv.visibility = View.GONE
                    binding.userDetailsContainerLayout.addView(getKeyValueView("Name", userName))
                    binding.userDetailsContainerLayout.addView(getKeyValueView("Email Id", emailId))
                    binding.userDetailsContainerLayout.addView(
                        getKeyValueView(
                            "Contact No.",
                            contactNo
                        )
                    )
                    binding.userDetailsContainerLayout.addView(
                        getKeyValueView(
                            "Date of birth",
                            dateOfBirth
                        )
                    )
                    binding.userDetailsContainerLayout.addView(getKeyValueView("Address", address))
                    binding.userDetailsContainerLayout.addView(getKeyValueView("City", city))
                    binding.userDetailsContainerLayout.addView(getKeyValueView("State", state))
                    binding.userDetailsContainerLayout.addView(getKeyValueView("Country", country))
                    binding.userDetailsContainerLayout.addView(getKeyValueView("PinCode", pincode))
                }
                it.userKYC?.apply {
                    setUserKYCData(it.userKYC)
                }
            } else {
                userViewModel.getUserKYCDetails(1001)?.observe(this, {
                    setUserKYCData(it)
                })
            }
        })
    }

    private fun setUserKYCData(kycData: UserKYC?) {
        kycData?.apply {
            binding.userKYCDetailsContainerLayout.removeAllViews()
            userKYCDetails = UserKYC(
                userId = userId,
                gstinNo = CryptoUtil.aesDecrypt(gstinNo ?: "", AppConstants.SECRET_KEY),
                panNo = CryptoUtil.aesDecrypt(panNo ?: "", AppConstants.SECRET_KEY),
                aadharNo = CryptoUtil.aesDecrypt(aadharNo ?: "", AppConstants.SECRET_KEY),
                drivingLicence = CryptoUtil.aesDecrypt(
                    drivingLicence ?: "",
                    AppConstants.SECRET_KEY
                ),
                voterId = CryptoUtil.aesDecrypt(voterId ?: "", AppConstants.SECRET_KEY),
                upiId = CryptoUtil.aesDecrypt(upiId ?: "", AppConstants.SECRET_KEY)
            ).apply {
                binding.userKYCDetailsTitleText.visibility = View.VISIBLE
                binding.userKYCDetailsContainerLayout.addView(
                    getKeyValueView(
                        "GSTIN No.",
                        gstinNo
                    )
                )
                binding.userKYCDetailsContainerLayout.addView(
                    getKeyValueView(
                        "PAN No.",
                        panNo
                    )
                )
                binding.userKYCDetailsContainerLayout.addView(
                    getKeyValueView(
                        "Aadhar No.",
                        aadharNo
                    )
                )
                binding.userKYCDetailsContainerLayout.addView(
                    getKeyValueView(
                        "Driving licence",
                        drivingLicence
                    )
                )
                binding.userKYCDetailsContainerLayout.addView(
                    getKeyValueView(
                        "Voter Id",
                        voterId
                    )
                )
                binding.userKYCDetailsContainerLayout.addView(
                    getKeyValueView(
                        "UPI Id",
                        upiId
                    )
                )
            }
        }
    }

    private fun getKeyValueView(key: String?, value: String?): View {
        val keyValueBinding = CustomKeyValueViewBinding.inflate(layoutInflater, binding.root, false)
        keyValueBinding.key.text = key ?: ""
        keyValueBinding.value.text = value ?: ""
        return keyValueBinding.root
    }
}