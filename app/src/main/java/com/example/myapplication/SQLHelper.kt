package com.example.am2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {

        val sqlCreateHighScores = "CREATE TABLE ${TABLE_NAME_HS} (" +
            "${COLUMN_NAME_LOGIN} TEXT PRIMARY KEY," +
            "${COLUMN_NAME_SCORE} INTEGER)"

        db.execSQL(sqlCreateHighScores)

    }

    fun deleteDB(sqLiteDatabase: SQLiteDatabase){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_HS")
        val sqlCreateHighScores = "CREATE TABLE ${TABLE_NAME_HS} (" +
                "${COLUMN_NAME_LOGIN} TEXT PRIMARY KEY," +
                "${COLUMN_NAME_SCORE} INTEGER)"

        sqLiteDatabase.execSQL(sqlCreateHighScores)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val DATABASE_VERSION = 5
        val DATABASE_NAME = "DatesDB"

        val TABLE_NAME_HS="HIGHSCORES"
        val COLUMN_NAME_SCORE="SCORE"
        val COLUMN_NAME_LOGIN="LOGIN"

    }
}