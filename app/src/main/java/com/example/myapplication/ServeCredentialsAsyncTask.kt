package com.example.myapplication

import android.os.AsyncTask
import android.util.Log
import com.github.kittinunf.fuel.httpPost

class serveCredentialsAsync(login: String?, password: String?, endpoint: String?) : AsyncTask<Void, Void, String>() {
    val innerLogin: String? = login
    val innerPassword: String? = password
    val innerEndpoint: String? = endpoint

    private fun processResponse(rawResponse: String): String {
        val matcher = "[A-Za-z ]+".toRegex()
        return matcher.findAll(rawResponse).map{it.value}.toList()[1]

    }

    override fun doInBackground(vararg params: Void?): String {
        val jsonPayload = "{ \"login\" : \"$innerLogin\",  \"password\" : \"$innerPassword\"}"
        val url = "https://am-projekt.herokuapp.com/$innerEndpoint"
        val (_, _, result) = url
            .httpPost()
            .body(jsonPayload, Charsets.UTF_8)
            .header("Content-Type" to "application/json")
            .responseString()

        return processResponse(result.component1().toString())
    }
}