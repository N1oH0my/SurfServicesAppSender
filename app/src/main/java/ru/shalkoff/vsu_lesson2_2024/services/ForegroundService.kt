package ru.shalkoff.vsu_lesson2_2024.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import ru.shalkoff.vsu_lesson2_2024.R
import ru.shalkoff.vsu_lesson2_2024.activity.MainActivity

class ForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Toast.makeText(this, "ForegroundService запущен", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Создаем уведомление для Foreground Service
        val notification = createNotification()

        // Устанавливаем Foreground Service с уведомлением
        startForeground(FOREGROUND_NOTIFICATION_ID, notification)

        // Возвращаем флаг, чтобы сервис автоматически перезапускался
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText("Андройд школа ВГУ 2024 от Surf")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "ForegroundService остановлен", Toast.LENGTH_SHORT).show()
    }
    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
        private const val FOREGROUND_NOTIFICATION_ID = 101
    }
}


