package com.example.printer

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import androidx.annotation.RequiresPermission
import java.io.IOException
import java.util.UUID

class BluetoothManager(val device: BluetoothDevice) {
    lateinit var socket: BluetoothSocket

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun connect() {
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        socket = device.createRfcommSocketToServiceRecord(uuid)
        socket.connect()
    }


    fun sendData(data: ByteArray) {
        socket.outputStream.write(data)
        socket.outputStream.flush()
    }

    fun disconnect() {
        try {
            socket.outputStream.close()
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}