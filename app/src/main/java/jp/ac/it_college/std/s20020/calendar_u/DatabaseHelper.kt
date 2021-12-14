package jp.ac.it_college.std.s20020.calendar_u

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,DATABASE_VERSION){

    companion object {
        private const val DATABASE_NAME = "SCHEDULE.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val creatTable_main = """
            CREATE TABLE SCHEDULE (
            _id LONG PRIMARY KEY,
            date TEXT,
            title TEXT,
            s_hour INTEGER
            s_minute INTEGER
            e_hour INTEGER
            e_minute INTEGER
            memo TEXT
            );
        """.trimIndent()

        db?.execSQL(creatTable_main)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}