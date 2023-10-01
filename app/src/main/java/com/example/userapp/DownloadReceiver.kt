package com.example.userapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat



class DownloadReceiver(val pdfShow: PdfShow, val downloadId: Long, val pd: ProgressDialog) : BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent?.action) {
            if (downloadId != -1L) {
                // Download completed
                pd.dismiss()
                val notificationManager = NotificationManagerCompat.from(context!!)
                val channelId = "download_channel" // Use the same channel ID as created in step 1

                val notification = NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Download Completed")
                    .setContentText("Your PDF has been downloaded.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH) // Change priority to HIGH
                    .build()
                // Show the notification
                if (ActivityCompat.checkSelfPermission(
                        pdfShow,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                notificationManager.notify(1001, notification) // Use a unique notification ID
            }
        }
    }
}

