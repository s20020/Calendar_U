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
            _id LONG,
            date TEXT,
            title TEXT,
            s_time TEXT,
            e_time TEXT,
            memo TEXT
            );
        """.trimIndent()
        db?.execSQL(creatTable_main)

        val creatTable_template = """
            CREATE TABLE TEMPLATE (
            _id LONG,
            title TEXT,
            s_time TEXT,
            e_time TEXT,
            memo TEXT
            );
        """.trimIndent()
        db?.execSQL(creatTable_template)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}