package com.example.databaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Кнопка "Начать работу"
        val btnStart = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener {
            // Переход на второй экран
            val intent = Intent(this, DatabaseActivity::class.java)
            startActivity(intent)
        }
    }
}