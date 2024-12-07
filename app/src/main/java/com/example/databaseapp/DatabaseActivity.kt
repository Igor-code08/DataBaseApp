package com.example.databaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar

class DatabaseActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        // Инициализация базы данных
        dbHelper = DatabaseHelper(this)

        // Элементы интерфейса
        val etName = findViewById<EditText>(R.id.etName)
        val spinnerPosition = findViewById<Spinner>(R.id.spinnerPosition)
        val etPhone = findViewById<EditText>(R.id.etPhone)

        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnGetData = findViewById<Button>(R.id.btnGetData)
        val btnDeleteData = findViewById<Button>(R.id.btnDeleteData)

        val tvOutput = findViewById<TextView>(R.id.tvOutput)

        // Заполнение Spinner данными (пример должностей)
        val positions = arrayOf("Менеджер", "Программист", "Дизайнер")
        spinnerPosition.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, positions)

        // Сохранение данных в базу данных
        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val position = spinnerPosition.selectedItem.toString()
            val phone = etPhone.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                dbHelper.insertData(name, position, phone)
                Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show()
                etName.text.clear()
                etPhone.text.clear()
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        // Получение данных из базы данных
        btnGetData.setOnClickListener {
            tvOutput.text = dbHelper.getAllData()
        }

        // Удаление всех данных из базы данных
        btnDeleteData.setOnClickListener {
            dbHelper.deleteAllData()
            Toast.makeText(this, "Все данные удалены", Toast.LENGTH_SHORT).show()
            tvOutput.text = ""
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                finish() // Закрыть активность
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}