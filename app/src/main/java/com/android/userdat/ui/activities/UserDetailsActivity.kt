package com.android.userdat.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.android.userdat.R
import com.android.userdat.UserDatApplication
import com.android.userdat.databinding.ActivityUserDetailsBinding
import com.android.userdat.model.UserDetails
import com.android.userdat.util.AppConstants
import com.android.userdat.util.Validator.isFieldNotEmpty
import com.android.userdat.util.showToast
import com.android.userdat.viewmodels.UserViewModel
import com.android.userdat.viewmodels.ViewModelFactory

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private var toUpdate: Boolean = false
    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory((application as UserDatApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.stateTextBox.apply {
            keyListener = null
            setAdapter(ArrayAdapter(this@UserDetailsActivity, android.R.layout.simple_list_item_1, AppConstants.statesList))
        }
        binding.countryTextBox.apply {
            keyListener = null
            setAdapter(ArrayAdapter(this@UserDetailsActivity, android.R.layout.simple_list_item_1, AppConstants.countryList))
        }
        binding.backIv.setOnClickListener {
            onBackPressed()
        }
        binding.submitBtn.setOnClickListener {
            if (binding.nameTIL.isFieldNotEmpty() &&
                binding.emailTIL.isFieldNotEmpty() &&
                binding.contactNumTIL.isFieldNotEmpty() &&
                binding.dobTIL.isFieldNotEmpty() &&
                binding.addressTIL.isFieldNotEmpty() &&
                binding.cityTIL.isFieldNotEmpty() &&
                binding.stateTIL.isFieldNotEmpty() &&
                binding.countryTIL.isFieldNotEmpty() &&
                binding.pincodeTIL.isFieldNotEmpty()
            ) {
                val userDetails = UserDetails(
                    userId = 1001,
                    userName = binding.nameTextBox.text.toString().trim(),
                    emailId = binding.emailTextBox.text.toString().trim(),
                    contactNo = binding.contactNumTextBox.text.toString().trim(),
                    dateOfBirth = binding.dobTextBox.text.toString().trim(),
                    address = binding.addressTextBox.text.toString().trim(),
                    city = binding.cityTextBox.text.toString().trim(),
                    state = binding.stateTextBox.text.toString().trim(),
                    country = binding.countryTextBox.text.toString().trim(),
                    pincode = binding.pincodeTextBox.text.toString().trim()
                )
                when (toUpdate) {
                    true -> userViewModel.updateUserDetails(userDetails)
                    false -> userViewModel.insertUserDetails(userDetails)
                }
                showToast(this.getString(R.string.data_updated))
                onBackPressed()
            }
        }
        userViewModel.getUserDetails(1001)?.observe(this, {
            if (it != null) {
                toUpdate = true
                binding.nameTextBox.setText(it.userName ?: "")
                binding.emailTextBox.setText(it.emailId ?: "")
                binding.contactNumTextBox.setText(it.contactNo ?: "")
                binding.dobTextBox.setText(it.dateOfBirth ?: "")
                binding.addressTextBox.setText(it.address ?: "")
                binding.cityTextBox.setText(it.city ?: "")
                binding.stateTextBox.setText(it.state ?: "")
                binding.countryTextBox.setText(it.country ?: "")
                binding.pincodeTextBox.setText(it.pincode ?: "")
            }
        })
    }
}