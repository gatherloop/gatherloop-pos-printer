package com.example.printer

import android.app.Service
import android.content.Intent
import android.util.Log
import org.java_websocket.server.WebSocketServer
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.WebSocket
import com.google.gson.Gson
import java.net.InetSocketAddress

class PrinterServer(private val context: Service, port: Int, private val printer: Printer) : WebSocketServer(InetSocketAddress(port)) {

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        Log.d("WebSocket", "Client connected: ${conn?.remoteSocketAddress}")
        conn?.send("Connected to printer server")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        if (message != null) {
            Log.d("WebSocket", "Received: $message")
            try {
                val transaction = Gson().fromJson(message, Transaction::class.java)
                val invoice = Invoice(printer, transaction)
                invoice.print()
                Thread.sleep(1000)
            } catch (e: Exception) {
                Log.e("WebSocket", "Cannot Print", e)
                conn?.send("Cannot Print : " + e.message)

                val intent = Intent("com.example.printer.ERROR")
                intent.putExtra("error_message", e.message ?: "Unknown error")
                context.sendBroadcast(intent)
            }
        }
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        Log.d("WebSocket", "Connection closed: ${conn?.remoteSocketAddress}")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        Log.e("WebSocket", "Error occurred", ex)
        val intent = Intent("com.example.printer.ERROR")
        intent.putExtra("error_message", ex?.message ?: "Unknown error")
        context.sendBroadcast(intent)
    }

    override fun onStart() {
        Log.e("WebSocket", "on start triggered")
    }
}