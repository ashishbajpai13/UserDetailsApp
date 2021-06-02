package com.android.userdat.util

import android.content.Context
import com.android.userdat.ui.interfaces.DownloadInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object FileUtils {

    const val READ_WRITE_EXTERNAL_STORAGE_REQUEST = 100

    fun writeAndSaveFile(data: String, context: Context, downloadInterface: DownloadInterface) {
        val fileName = "profile.txt"
        val sdcard: File? = context.getExternalFilesDir("")
        var dir: File? = File(sdcard?.absolutePath.toString())
        if (sdcard?.exists() == false)
            dir?.mkdir()
        else dir = sdcard
        val file = File(dir, fileName)
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(file)
            os.write(data.toByteArray())
            os.close()
            downloadInterface.onComplete(dir?.absolutePath)
        } catch (e: IOException) {
            os?.flush()
            e.printStackTrace()
            downloadInterface.onDownloadFailed()
        }
    }
}