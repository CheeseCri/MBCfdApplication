package com.example.sportgameprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenuActivity : AppCompatActivity() {
    lateinit var btPlayerRecord : Button
    lateinit var btJoinGame : Button
    lateinit var btLiveGame : Button
    lateinit var btLotteryRecord : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        btPlayerRecord = findViewById(R.id.bt_player_record)
        btPlayerRecord.setOnClickListener {
            val intent = Intent(this, PlayerListActivity::class.java)
            startActivity(intent)
        }
        btJoinGame = findViewById(R.id.bt_join_game)
        btJoinGame.setOnClickListener {
            val intent = Intent(this, GameListActivity::class.java)
            startActivity(intent)
        }
    }
}