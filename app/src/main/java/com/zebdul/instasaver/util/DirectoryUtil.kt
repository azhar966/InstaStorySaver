package com.zebdul.instasaver.util

import android.os.Environment
import java.io.File

class DirectoryUtil {
    val DIRECTORY = "/InstaSaver/"
    val DIRECTORY_FOLDER = File("${Environment.getExternalStorageDirectory()}/Download/InstaSaver/")

    fun createFile(){
        if (!DIRECTORY_FOLDER.exists()){
            DIRECTORY_FOLDER.mkdirs()
        }
    }
}