package com.android.userdat.util

import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout

object Validator {
    fun TextInputLayout.isFieldNotEmpty(): Boolean {
        this.editText?.addTextChangedListener {
            this.isErrorEnabled = false
        }
        if (this.editText?.text.toString().trim().isEmpty()) {
            this.error = "Required field"
            this.requestFocus()
            return false
        } else
            this.isErrorEnabled = false
        return true
    }
}