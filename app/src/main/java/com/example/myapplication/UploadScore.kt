package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.am2.SQLHelper
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost

class uploadScore(login: String?, password: String?, score:Int?) : AsyncTask<Void, Void, String>() {
    val innerLogin: String? = login
    val innerPassword: String? = password
    val innerScore: Int? = score



    override fun doInBackground(vararg params: Void?): String {
        val jsonPayload = "{ \"login\" : \"$innerLogin\",  \"password\" : \"$innerPassword\", \"points\" : $innerScore}"
        Log.d("aaaaaaaaaaaaaaaaaaaaaaaa",jsonPayload)
        val url = "https://am-projekt.herokuapp.com/update"
        val (_, _, result) = url
            .httpPost()
            .body(jsonPayload, Charsets.UTF_8)
            .header("Content-Type" to "application/json")
            .responseString()

        return result.toString()
    }
}