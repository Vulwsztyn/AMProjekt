package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.opengl.Visibility
import android.view.View


class MainActivity : AppCompatActivity() {
    val MY_PREFS_NAME = "Preferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateLabel()

        play_button.setOnClickListener {
            val myIntent = Intent(this@MainActivity, PlayActivity::class.java)
            this@MainActivity.startActivity(myIntent)
        }
        hs_button.setOnClickListener {
            val myIntent = Intent(this@MainActivity, HSActivity::class.java)
            this@MainActivity.startActivity(myIntent)
        }
        login_button.setOnClickListener {
            val myIntent = Intent(this@MainActivity, LoginActivity::class.java)
            this@MainActivity.startActivity(myIntent)
        }
    }

    override fun onResume() {
        updateLabel()
        super.onResume()
    }

    fun updateLabel(){
        val prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
        val score = prefs.getInt("score", 0)
        score_text.text = "Score: ${score}"

        val login = prefs.getString("login", "")
        if(login != ""){
            name_text.visibility= View.VISIBLE
            name_text.text = "Zalogowao jako $login"
        } else {
            name_text.visibility= View.GONE
        }
    }
}
