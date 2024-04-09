package ru.shalkoff.vsu_lesson2_2024.application

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //TODO 3
        // Почему не выводиться этот лог в Logcat?
        // Сделайте так, чтобы он выводился
        //Toast.makeText(this, "Вызвался метод onCreate()", Toast.LENGTH_SHORT).show()
        Log.i("APPCREATE", "Вызвался метод onCreate()")
    }
}