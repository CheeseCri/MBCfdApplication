package com.example.sportgameprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PlayerDetailActvity : AppCompatActivity() {
    lateinit var tvPID : TextView
    lateinit var tvPlayerName : TextView
    lateinit var tvDebugSql : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail_activity)
        /*
         * 뷰 선언 부분
         */
        tvPID = findViewById<TextView>(R.id.tv_pid)
        tvPlayerName = findViewById(R.id.tv_player_name)
        tvDebugSql = findViewById(R.id.tv_debug_sql)
        val playerID = intent.getStringExtra("playerID")



        tvPID.text = playerID


    }
}