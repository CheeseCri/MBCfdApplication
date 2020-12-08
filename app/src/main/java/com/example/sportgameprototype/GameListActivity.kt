package com.example.sportgameprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import customadapters.GameAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GameListActivity : AppCompatActivity(){
    lateinit var rvGameList : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)

        val tmpDate = "2020-10-26"
        val gameData : String = HTTPtask().execute(getString(R.string.JSPURL), "gamelist", tmpDate).get()
        Log.d("GAMELIST", gameData)
        val gameListArray = addGameListArray(JSONArray(gameData))
        val gameListAdapter = GameAdapter(this, gameListArray)
        rvGameList = findViewById(R.id.rv_game_list)
        rvGameList.layoutManager = LinearLayoutManager(this)
        rvGameList.adapter = gameListAdapter
        rvGameList.addItemDecoration(RecyclerDecoration(120))
    }

    fun addGameListArray(jsonArray : JSONArray): ArrayList<GameInfo> {
        val gameListArray : ArrayList<GameInfo> = ArrayList()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i) as JSONObject
            Log.d("GAMELIST", item.toString())
            val dateString = item.getString("G_DT") + " " + item.getString("G_TM")
            val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val gDate : Date = timeFormat.parse(dateString)
            val input = GameInfo(
                    item.getString("G_ID"),
                    item.getString("S_ID"),
                    item.getString("G_DT"),
                    item.getString("AWAY_ID"),
                    item.getString("HOME_ID"),
                    item.getString("S_ID"),
                    item.getString("G_TM"),
                    gDate
            )
            gameListArray.add(input)
        }
        Log.d("USERLOG-ADDPLAYERDATA", gameListArray.toString())
        return gameListArray
    }
}
