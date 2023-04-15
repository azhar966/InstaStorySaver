package com.zebdul.instasaver.util

import android.app.DownloadManager
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.telephony.mbms.DownloadRequest
import android.widget.Toast
import java.io.File

class Utils {

    fun startDownload(downloadPath:String, destinationPath:String, context: Context, filename:String){

        Toast.makeText(context, "Downloading  Started", Toast.LENGTH_SHORT).show()

        val request = DownloadManager.Request(Uri.parse(downloadPath))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)

        request.setTitle(filename)
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, destinationPath+filename)

        (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)

        MediaScannerConnection.scanFile(context, arrayOf(File("$DIRECTORY_DOWNLOADS/$destinationPath$filename").absolutePath),
        null){
            _,_ ->
        }
    }

}