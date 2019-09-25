package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.am2.SQLHelper
import com.github.kittinunf.fuel.httpGet

class downloadHighscore(context: Context) : AsyncTask<Void, Void, String>() {
    val myContext: Context = context

    @Synchronized private fun DB_addScore(login: String, score:String) {
        val database = SQLHelper(myContext).writableDatabase
        val values = ContentValues()
        values.put(SQLHelper.COLUMN_NAME_LOGIN, login)
        values.put(SQLHelper.COLUMN_NAME_SCORE, score)
        val newRowId = database.insert(SQLHelper.TABLE_NAME_HS, null, values)
        Log.d("newRoWId", newRowId.toString())
        database.close()
    }

    private fun processResponse(rawResponse: String): String {
        SQLHelper(myContext).deleteDB(SQLHelper(myContext).writableDatabase)
        val matcher = "\"login\":\"[\\s\\p{L}]+\"".toRegex()
        val matcher2 = "\"points\":\\d+".toRegex()
        val logins = matcher.findAll(rawResponse).map{it.value}.toList()
        val scores = matcher2.findAll(rawResponse).map{it.value}.toList()
        for (i in logins.indices) {
            DB_addScore(logins[i].substring(9,logins[i].length-1),scores[i].substring(9,scores[i].length))
        }
        return "OK"

    }

    override fun doInBackground(vararg params: Void?): String {
        val url = "https://am-projekt.herokuapp.com/getBest"
        val (_, _, result) = url
            .httpGet()
            .header("Content-Type" to "application/json")
            .responseString()

        return processResponse(result.component1().toString())
    }
}