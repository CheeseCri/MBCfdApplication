package com.example.sportgameprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jetbrains.anko.startActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val introTime : Long = 3000
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        Thread.sleep(introTime)
        startActivity<MainMenuActivity>()
        finish()
    }
}