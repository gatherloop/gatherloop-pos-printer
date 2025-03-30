package com.example.printer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class PrinterServerService: Service() {
    private var port: Int = PrinterServerServiceConfig.port
    private val printer: Printer? = PrinterServerServiceConfig.printer

    private lateinit var server: PrinterServer

    override fun onCreate() {
        startWebSocketServer()
        startForegroundService()
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun startForegroundService() {
        val channelId = "websocket_server"
        val channelName = "WebSocket Server"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Printer Server Running")
            .setContentText("Listening on port ${port}")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        startForeground(1, notification)
    }

    private fun startWebSocketServer() {
        if (printer != null) {
            server = PrinterServer(this, port, printer)
            server.start()
        }
    }

    override fun onDestroy() {
        server.stop()
        super.onDestroy()
    }
}