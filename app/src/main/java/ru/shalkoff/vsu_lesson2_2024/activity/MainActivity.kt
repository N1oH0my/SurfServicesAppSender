package ru.shalkoff.vsu_lesson2_2024.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.parcelize.Parcelize
import ru.shalkoff.vsu_lesson2_2024.R
import ru.shalkoff.vsu_lesson2_2024.provider.ContactsContentResolver
import ru.shalkoff.vsu_lesson2_2024.services.BackgroundService
import ru.shalkoff.vsu_lesson2_2024.services.ForegroundService

@Parcelize
data class User(val name: String?, val age: Int) : Parcelable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val testTv = findViewById<TextView>(R.id.main_activity_tv)
        testTv.setOnClickListener {
            openSecondActivity()
        }
        initForegroundService()
        initBackgroundService()
        initFetchContacts()
    }



    private fun openSecondActivity() {
        val user = User("Vladimir", 29)
        //TODO 1
        // Откройте SecondActivity и передайте туда объект user
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("USER_INFO_KEY", user)
        startActivity(intent)
    }

    private fun initForegroundService() {
        val startForegroundServiceBtn = findViewById<Button>(R.id.start_foreground_service_btn)
        startForegroundServiceBtn.setOnClickListener {
            //TODO 2
            // Напишите код для запуска ForegroundService
            val serviceIntent = Intent(this, ForegroundService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }

        val stopForegroundServiceBtn = findViewById<Button>(R.id.stop_foreground_service_btn)
        stopForegroundServiceBtn.setOnClickListener {
            val serviceIntent = Intent(this, ForegroundService::class.java)
            stopService(serviceIntent)
        }
    }

    private fun initBackgroundService() {
        val startServiceButton = findViewById<Button>(R.id.start_background_service_btn)
        startServiceButton.setOnClickListener {
            startService(Intent(this, BackgroundService::class.java))
        }

        val stopServiceButton = findViewById<Button>(R.id.stop_background_service_btn)
        stopServiceButton.setOnClickListener {
            stopService(Intent(this, BackgroundService::class.java))
        }
    }

    private fun initFetchContacts() {
        val contactsContentResolver = ContactsContentResolver(contentResolver)
        val fetchContactsBtn = findViewById<Button>(R.id.fetch_contacts_btn)
        fetchContactsBtn.setOnClickListener {
            contactsContentResolver.fetchContacts()
        }
    }
}