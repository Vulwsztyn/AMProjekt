package com.example.myapplication

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.concurrent.thread
import com.github.kittinunf.result.Result
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    val MY_PREFS_NAME = "Preferences"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            if(registerBox.isChecked){
                register()
            }
            else {
                login()
            }

        }
    }

    fun login() {
        val response = serveCredentialsAsync(loginField.text.toString(), passwdField.text.toString(), "login").execute().get()
        checkResponse(response)
    }

    fun register(){
        val response = serveCredentialsAsync(loginField.text.toString(), passwdField.text.toString(), "register").execute().get()
        checkResponse(response)
    }

    fun checkResponse(response: String){
        if(response == "OK") {
            val editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
            editor.putString("login", loginField.text.toString())
            editor.putString("password", passwdField.text.toString())
            editor.apply()
            this.finish()
        } else {
            Toast.makeText(this@LoginActivity, "Niepoprawne dane", Toast.LENGTH_SHORT).show()
        }
    }
}

