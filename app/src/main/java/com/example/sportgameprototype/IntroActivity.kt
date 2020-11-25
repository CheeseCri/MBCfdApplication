package com.example.sportgameprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.jetbrains.anko.startActivity

class IntroActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT:Long = 2000 // 2 sec

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        Handler().postDelayed({
            startActivity(Intent(this,MainMenuActivity::class.java))
            finish()  // close this activity
        }, SPLASH_TIME_OUT)
    }
}
