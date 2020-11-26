package com.example.sportgameprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        //val jData:String = Task().execute(JSPURL).get()

        val sData:String = HTTPtask().execute(getString(R.string.JSPURL), "spinner").get()
        //Log.i("JSON", jData)
        Log.i("USERLOG-JSON", sData.toString())
        seasonArray.add("--")
        teamArray.add("--")
        //addJDataArray(JSONArray(jData))
        addJSpinnerArray(JSONObject(sData))

        var spSeasonAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, seasonArray)
        var spTeamAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teamArray)
        spSeason.adapter = spSeasonAdapter
        spTeam.adapter = spTeamAdapter
        val myAdapter = PlayerAdapter(this, this)

        rvDataList.layoutManager = LinearLayoutManager(this)
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
                        //두 셀렉트 된 값을 모아서
                        //HTTPtask에 excute하고 집어넣어서 뿌려주는 과정 진행
                        //그 과정에서 데이터를 가져오는 함수를 어떻게 진행 할 것인가?

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



//        rv_data_list2 = findViewById(R.id.rv_data_list2)
//        rv_data_list2.layoutManager = LinearLayoutManager(this)
//        rv_data_list2.adapter = PlayerAdapter(playerDataArray, this)
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

    private fun addPlayerDataArray(jsonArray : JSONArray): ArrayList<PlayerInfo> {
        val playerDataArray : ArrayList<PlayerInfo> = ArrayList()
        for (i in 0 until jsonArray.length()){
            val item = jsonArray.getJSONObject(i) as JSONObject
            Log.i("USERLOG-ADDPLAYERDATA", item.toString())
            val input : PlayerInfo = PlayerInfo(
                item.getString("BACK_NO"),
                item.getString("P_NM"),
                item.getString("POS_NO"),
                item.getString("P_ID"))
            playerDataArray.add(input)
        }
        Log.i("USERLOG-ADDPLAYERDATA", playerDataArray.toString())
        return playerDataArray
    }

    override fun onItemButtonClick(playerID : String) {
        val intent = Intent(this, PlayerDetailActvity::class.java)
        intent.putExtra("playerID", playerID)
        startActivity(intent)
    }


    //예시코드, 현재 필요없음.
//    private fun addDataArray( ) {
//        dataArray.add("오리")
//        dataArray.add("호랑이")
//        dataArray.add("여우")
//        dataArray.add("늑대")
//        dataArray.add("오소리")
//        dataArray.add("원숭이")
//        dataArray.add("물개")
//        dataArray.add("펭귄")
//        dataArray.add("하마")
//        dataArray.add("미어캣")
//        dataArray.add("타조")
//        dataArray.add("강아지")
//        dataArray.add("고양이")
//    }

}

