package com.example.printer

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val BLUETOOTH_PERMISSION_REQUEST_CODE = 1

    private lateinit var spinnerDeviceName: Spinner
    private lateinit var editTextPort: EditText
    private lateinit var btnStart: Button
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var printerServer: PrinterServer

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun onCreate(savedInstanceState: Bundle?) {
        val filter = IntentFilter("printer_server_error")
        registerReceiver(errorReceiver, filter, Context.RECEIVER_NOT_EXPORTED)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        spinnerDeviceName = findViewById(R.id.spinner_device_name)
        editTextPort = findViewById(R.id.edit_text_port)
        btnStart = findViewById(R.id.btn_start)

        if (isServiceRunning(PrinterServerService::class.java)) {
            btnStart.text = "Stop"
        } else {
            btnStart.text = "Start"
        }

        btnStart.setOnClickListener {
            if (btnStart.text.equals("Start")) {
                startServer()
            } else if (btnStart.text.equals("Stop")) {
                stopServer()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                ) {

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.POST_NOTIFICATIONS),
                    BLUETOOTH_PERMISSION_REQUEST_CODE)
            } else {
                initializeBluetooth()
            }
        } else {
            initializeBluetooth()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(errorReceiver)
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == BLUETOOTH_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeBluetooth()
            } else {
                Log.e("Bluetooth", "Bluetooth permission denied.")
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun initializeBluetooth() {
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, bluetoothAdapter?.bondedDevices?.toTypedArray() ?: arrayOf())
        spinnerDeviceName.adapter = arrayAdapter
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    private fun startServer() {
        val device = spinnerDeviceName.selectedItem
        val port = editTextPort.text.toString().toInt()

        if (device is BluetoothDevice) {
            bluetoothManager = BluetoothManager(device)

            try {
                bluetoothManager.connect()
                val printer = Printer(bluetoothManager)

                PrinterServerServiceConfig.port = port
                PrinterServerServiceConfig.printer = printer

                val serviceIntent = Intent(this, PrinterServerService::class.java)
                serviceIntent.action = "START_SERVER"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent)
                } else {
                    startService(serviceIntent)
                }

                btnStart.text = "Stop"
            } catch (error: Exception) {
                Toast.makeText(this, "Error connecting to printer : " + error.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private val errorReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val errorMessage = intent?.getStringExtra("error_message")
            Toast.makeText(this@MainActivity, "Printer Server Error: $errorMessage", Toast.LENGTH_LONG).show()
            stopServer()
        }
    }

    private fun stopServer() {
        val serviceIntent = Intent(this, PrinterServerService::class.java)
        stopService(serviceIntent)
        btnStart.text = "Start"
    }
}