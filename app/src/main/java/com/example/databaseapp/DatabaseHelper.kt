package com.example.databaseapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "database.db"
        private const val DATABASE_VERSION = 1

        // Таблица и колонки
        private const val TABLE_NAME = "employees"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_POSITION = "position"
        private const val COLUMN_PHONE = "phone"

        // SQL-запрос для создания таблицы
        private const val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_POSITION TEXT,
                $COLUMN_PHONE TEXT
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Добавление записи
    fun insertData(name: String, position: String, phone: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_POSITION, position)
            put(COLUMN_PHONE, phone)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    // Чтение всех записей
    fun getAllData(): String {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)

        val result = StringBuilder()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val position = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSITION))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))

            result.append("ID: $id\nИмя: $name\nДолжность: $position\nТелефон: $phone\n\n")
        }

        cursor.close()
        return result.toString()
    }

    // Удаление всех записей
    fun deleteAllData() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }
}