package com.example.sportgameprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PlayerDetailActvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail_activity)
        val playerID = intent.getStringExtra("playerID")
        val tvPID = findViewById<TextView>(R.id.tvPid)
        tvPID.text = playerID
    }
}