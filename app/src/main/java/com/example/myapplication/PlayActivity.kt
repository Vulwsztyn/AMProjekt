package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_play.*


class PlayActivity : AppCompatActivity() {
    var score = 0
    var added_value = 1
    var cost = 10
    val MY_PREFS_NAME = "Preferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE)
        score = prefs.getInt("score", 0)
        added_value = prefs.getInt("added_value", 0)
        cost = prefs.getInt("cost", 10)

        updateTexts()

        add_button.setOnClickListener {
            add()
        }
        upgrade_button.setOnClickListener {
            upgrade()
        }
        reset_button.setOnClickListener {
            reset()
        }
    }

    fun add() {
        score += added_value
        val editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
        editor.putInt("score", score)
        editor.apply()
        updateScoreLabel()
    }

    fun upgrade() {
        if (score >= cost) {
            score -= cost
            added_value += 1
            cost *= 2
            val editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
            editor.putInt("added_value", added_value)
            editor.putInt("score", score)
            editor.putInt("cost", cost)
            editor.apply()
            updateTexts()

        }
    }

    fun updateScoreLabel(){
        score_text.text = "Score: ${score}"
    }

    fun updateTexts(){
        add_button.text = "+${added_value}"
        upgrade_button.text = "Upgrade to +${added_value+1} for ${cost}"
        updateScoreLabel()
    }

    fun reset(){
        score = 0
        added_value = 1
        cost = 10
        val editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
        editor.putInt("added_value", added_value)
        editor.putInt("cost", cost)
        editor.putInt("score", score)
        editor.apply()
        updateTexts()
    }
}
