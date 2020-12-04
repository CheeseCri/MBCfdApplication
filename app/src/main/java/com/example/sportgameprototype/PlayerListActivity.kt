package com.example.sportgameprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import customadapters.PlayerAdapter
import org.json.*


interface ItemClickListener{
    fun onItemButtonClick(playerID : String)
}

class PlayerListActivity : AppCompatActivity(), ItemClickListener {
//    val dataArray : ArrayList<String> = ArrayList()
    val seasonArray : ArrayList<String> = ArrayList()
    val teamArray : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_list)

        val spSeason : Spinner = findViewById(R.id.sp_season)
        val spTeam : Spinner = findViewById(R.id.sp_team)
        val rvDataList : RecyclerView = findViewById(R.id.rv_data_list)

        val playerListData:String = HTTPtask().execute(getString(R.string.JSPURL), "spinner").get()
        Log.i("USERLOG-JSON", playerListData.toString())
        seasonArray.add("--")
        teamArray.add("--")
        addJSpinnerArray(JSONObject(playerListData))

        var spSeasonAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, seasonArray)
        var spTeamAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teamArray)
        spSeason.adapter = spSeasonAdapter
        spTeam.adapter = spTeamAdapter
        val myAdapter = PlayerAdapter(this, this)

        rvDataList.layoutManager = LinearLayoutManager(this)
        rvDataList.addItemDecoration(DividerItemDecoration(this,1))
        rvDataList.adapter = myAdapter

        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("USERLOG-SPINNER", spSeason.selectedItem.toString())
                Log.d("USERLOG-SPINNER", spTeam.selectedItem.toString())

                Log.d("USERLOG-SPINNER", spSeason.selectedItemPosition.toString())
                Log.d("USERLOG-SPINNER", spTeam.selectedItemPosition.toString())

                if((spSeason.selectedItemPosition != 0  && spTeam.selectedItemPosition != 0)){
                    Log.d("USERLOG-SPINNER", "IF PASS, GET DATA")
                    val jData:String = HTTPtask().execute(getString(R.string.JSPURL), "recycle", spSeason.selectedItem.toString(), spTeam.selectedItem.toString()).get()
                    Log.d("USERLOG", jData)
                    val playerDataArray = addPlayerDataArray(JSONArray(jData))
                    myAdapter.setFilter(playerDataArray)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        spSeason.onItemSelectedListener = spinnerListener
        spTeam.onItemSelectedListener = spinnerListener


    }

    private fun addJSpinnerArray(inputJson : JSONObject){
        val season  = inputJson.get("season") as JSONArray
        val team  = inputJson.get("team") as JSONArray
        for ( i in 0 until season.length()){
            seasonArray.add(season[i].toString())
        }
        for (i in 0 until team.length()){
            teamArray.add(team[i].toString())
        }
        Log.i("USERLOG-SPINNERARRAY", seasonArray.toString())
        Log.i("USERLOG-SPINNERARRAY", teamArray.toString())
    }

    fun addPlayerDataArray(jsonArray : JSONArray): ArrayList<PlayerInfo> {
        val playerDataArray : ArrayList<PlayerInfo> = ArrayList()
        for (i in 0 until jsonArray.length()){
            val item = jsonArray.getJSONObject(i) as JSONObject
            Log.i("USERLOG-ADDPLAYERDATA", item.toString())
            val input : PlayerInfo = PlayerInfo(
                    if(item.getString("BACK_NO") == "null") 0 else item.getInt("BACK_NO"),
                    item.getString("P_NM"),
                    if(item.getString("POS_NO") == "null") 0 else item.getInt("POS_NO"),
                    item.getString("P_ID"))
            playerDataArray.add(input)
        }
        playerDataArray.sortBy{ data -> data.backNumber}
        Log.i("USERLOG-ADDPLAYERDATA", playerDataArray.toString())
        return playerDataArray
    }

    override fun onItemButtonClick(playerID : String) {
        val intent = Intent(this, PlayerDetailActvity::class.java)
        intent.putExtra("playerID", playerID)
        startActivity(intent)
    }
}

