package com.android.userdat.ui.interfaces

interface DownloadInterface {
    fun onComplete(message: String?)
    fun onDownloadFailed()
}