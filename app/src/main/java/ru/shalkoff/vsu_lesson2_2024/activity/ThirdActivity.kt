package ru.shalkoff.vsu_lesson2_2024.activity

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.shalkoff.vsu_lesson2_2024.R
import ru.shalkoff.vsu_lesson2_2024.receiver.AirplaneModeReceiver
import ru.shalkoff.vsu_lesson2_2024.receiver.BluetoothStateReceiver

class ThirdActivity : AppCompatActivity() {

    private val airplaneModeReceiver = AirplaneModeReceiver()
    private val bluetoothStateReceiver = BluetoothStateReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (savedInstanceState != null) {
//            val surfKey = savedInstanceState.getString("surf_key")
//            Toast.makeText(this, surfKey, Toast.LENGTH_SHORT).show()
//        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_third)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Регистрация BroadcastReceiver'а для ACTION_AIRPLANE_MODE_CHANGED
        val filterAirplane = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, filterAirplane)

        initSenderBroadcastMessage()

        // Регистрация BroadcastReceiver'а для Bluetooth
        val filterBluetooth = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothStateReceiver, filterBluetooth)


        initSenderBroadcastMessage()
    }

    override fun onDestroy() {
        // Обязательно отменяем регистрацию BroadcastReceiver'а
        unregisterReceiver(airplaneModeReceiver)
        unregisterReceiver(bluetoothStateReceiver)
        super.onDestroy()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val surfKey = savedInstanceState.getString("surf_key")
        Toast.makeText(
            this,
            surfKey,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            "surf_key",
            "Произошло пересоздание Activity"
        )
    }

    private fun initSenderBroadcastMessage() {
        val sendBroadcastBtn = findViewById<Button>(R.id.send_broadcast_btn)
        sendBroadcastBtn.setOnClickListener {
            val intent = Intent("ru.shalkoff.vsu_lesson2_2024.SURF_ACTION")
            intent.putExtra("message", "Привет, Surf!")
            sendBroadcast(intent)
        }
    }
}