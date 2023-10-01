package com.example.userapp

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class PdfShow : AppCompatActivity() {
    private lateinit var name : TextView
    private lateinit var download : ImageView
    private lateinit var show : PDFView
    private lateinit var title : String
    private lateinit var url : String
    val destinationPath = "${Environment.getExternalStorageDirectory().absolutePath}/Download/"
    private lateinit var progressBar: ProgressBar
    private  var downloadReceiver: BroadcastReceiver? = null
    private lateinit var pd :ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_show)
        name = findViewById(R.id.pdfName)
        download = findViewById(R.id.download)
        show = findViewById(R.id.pdfviewer)
        progressBar = findViewById(R.id.progressBar)

        pd = ProgressDialog(this)
        pd.setMessage("Loading!")
        pd.show()
        title = intent.getStringExtra("title")!!
        url = intent.getStringExtra("url")!!

        name.text = title
        if (!url.isNullOrEmpty()) {
            RetrievePDFFromURL(show,pd).execute(url)
        } else {
            // Handle the case where the URL is missing or empty
            // You might want to show an error message or take appropriate action
            pd.dismiss()
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show()
        }
        createNotificationChannel()

        download.setOnClickListener {
            Toast.makeText(this, "Clicked Download button", Toast.LENGTH_SHORT).show()
            val fileName = title + ".pdf"
            val downloadId = downloadPdf(url, fileName)
            pd.setMessage("downloading!")
            pd.show()
            // Register the BroadcastReceiver to listen for download completion
            val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            downloadReceiver = DownloadReceiver(this,downloadId,pd)
            registerReceiver(downloadReceiver, intentFilter)
        }

    }
    override fun onDestroy() {
        super.onDestroy()

        // Unregister the BroadcastReceiver if it's not null
        downloadReceiver?.let {
            unregisterReceiver(it)
        }
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "download_channel"
            val channelName = "Download Channel"
            val channelDescription = "Channel for download notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = channelDescription

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun downloadPdf(pdfUrl: String, fileName: String):Long {
        val request = DownloadManager.Request(Uri.parse(pdfUrl))
            .setTitle(fileName)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return downloadManager.enqueue(request)
    }
    // ...
}

    private class RetrievePDFFromURL(private val pdfView: PDFView, val pd: ProgressDialog) :
        AsyncTask<String, Void, InputStream>() {

        override fun doInBackground(vararg params: String?): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url = URL(params[0])
                val urlConnection: HttpURLConnection =
                    url.openConnection() as HttpURLConnection

                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    pd.dismiss()
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            } catch (e: Exception) {
                pd.dismiss()
                e.printStackTrace()
            }
            return inputStream
        }

        override fun onPostExecute(result: InputStream?) {
            result?.let {
                pdfView.fromStream(it).load()
            }
        }
    }