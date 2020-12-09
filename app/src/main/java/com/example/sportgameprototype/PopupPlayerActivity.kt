package com.example.sportgameprototype

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView

class PopupPlayerActivity : AppCompatActivity() {
    lateinit var vvRecordPlayer : VideoView
    lateinit var btPlayerQuit : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_player)

        val mediaPath = intent.getStringExtra("path")
        btPlayerQuit = findViewById(R.id.bt_player_quit)
        btPlayerQuit.setOnClickListener {
            val intent = Intent()
            intent.putExtra("result", "Close Popup")
            setResult(RESULT_OK, intent)
            finish()
        }

        vvRecordPlayer = findViewById(R.id.vv_record_player)
        val mediaController = MediaController(this)
        vvRecordPlayer.setMediaController(mediaController)
        vvRecordPlayer.setVideoPath(mediaPath)
        vvRecordPlayer.requestFocus()
        vvRecordPlayer.start()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_OUTSIDE){
            return false
        }
        return true
    }
}