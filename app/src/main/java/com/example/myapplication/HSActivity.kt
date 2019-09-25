package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableRow
import android.widget.TextView
import com.example.am2.SQLHelper
import kotlinx.android.synthetic.main.activity_hs.*

class HSActivity : AppCompatActivity() {
    val MY_PREFS_NAME = "Preferences"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hs)

//        showHS()

        button.setOnClickListener {
            sync()
        }
    }

    fun sync() {
        upload()
        downloadHighscore(this).execute().get()
        showHS()
    }

    fun upload(){
        val prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
        val login = prefs.getString("login", "")
        val password = prefs.getString("password", "")
        val score = prefs.getInt("score", 0)
        uploadScore(login,password,score).execute().get()
    }

    fun showHS(){
        runOnUiThread {
            tableLayout.removeAllViews()
        }

        val database = SQLHelper(this).readableDatabase

        val projection = arrayOf<String>(SQLHelper.COLUMN_NAME_LOGIN,SQLHelper.COLUMN_NAME_SCORE)

        val cursorNauczycieli = database.query(
            SQLHelper.TABLE_NAME_HS, // The table to query
            projection, // The columns to return
            null, // The columns for the WHERE clause
            null, // don't filter by row groups
            null,                                      // don't sort
            null,
            null
        )

        cursorNauczycieli.moveToFirst()


        while(!cursorNauczycieli.isAfterLast){
            val row = TableRow(tableLayout.context)


            val qty = TextView(row.context)
            val login = cursorNauczycieli.getString(0)
            val score = cursorNauczycieli.getString(1)
            qty.text = "$login $score"
            Log.d("Test", qty.text.toString())

            runOnUiThread {
                row.addView(qty)
                tableLayout.addView(row)
            }
            cursorNauczycieli.moveToNext()
        }

        cursorNauczycieli.close()
        database.close()
    }
}
