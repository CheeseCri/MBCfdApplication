package com.example.sportgameprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class PlayerDetailActvity : AppCompatActivity() {
    lateinit var tvPID : TextView
    lateinit var tvPlayerName : TextView
    lateinit var tvPlayerTeam : TextView
    lateinit var rvSeasonAvg : RecyclerView
    lateinit var rvRecentRecord : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail_activity)
        /*
         * 뷰 선언 부분
         */
        tvPID = findViewById<TextView>(R.id.tv_pid)
        tvPlayerName = findViewById(R.id.tv_player_name)
        tvPlayerTeam = findViewById(R.id.tv_player_team)
        rvSeasonAvg = findViewById(R.id.rv_season_avg)
        rvRecentRecord = findViewById(R.id.rv_recent_record)

        val playerID = intent.getStringExtra("playerID")
        val playerDetailInfo = HTTPtask().execute(getString(R.string.JSPURL), "player",playerID, "detail").get()
        val playerRecordInfo = HTTPtask().execute(getString(R.string.JSPURL), "player",playerID, "record").get()
        Log.d("USERLOG-detail", playerDetailInfo)
        Log.d("USERLOG-detail", playerRecordInfo)

        val detailJsonArr = JSONArray(playerDetailInfo)
        val detailJson : JSONObject = detailJsonArr[0] as JSONObject
        tvPlayerName.text = detailJson.getString("P_NM")
        tvPlayerTeam.text = detailJson.getString("T_ID")
        tvPID.text = playerID

        // seasonAvg : 시즌 평균
        // recentRecord : 최근 5경기 기록
        val (seasonAvg : ArrayList<AvgInfo>, recentRecord : ArrayList<PlayerRecord>) = addRecord(JSONArray(playerRecordInfo))

        for (avg in seasonAvg)
            Log.d("USERLOG-DETAIL", "${avg.SEASON_ID},${avg.AVG_LD},${avg.AVG_RTM}")
        for (record in recentRecord)
            Log.d("USERLOG-DETAIL", "${record.G_DT},${record.AVG_LD},${record.AVG_RTM},")
        rvSeasonAvg.layoutManager = LinearLayoutManager(this)
        rvSeasonAvg.adapter = SeasonAvgAdapter(this, seasonAvg)
        rvRecentRecord.layoutManager = LinearLayoutManager(this)
        rvRecentRecord.adapter = RecentRecordAdapter(this, recentRecord)



    }

    private fun addRecord(jsonArray : JSONArray) : Pair<ArrayList<AvgInfo>, ArrayList<PlayerRecord>>{
        var seaonAvg : ArrayList<AvgInfo> = ArrayList()
        var recentRecord : ArrayList<PlayerRecord> = ArrayList()
        var sumMap : MutableMap<Int, SumRecord> = mutableMapOf()

        for (i in 0 until jsonArray.length()){
            val item = jsonArray.getJSONObject(i) as JSONObject
            val year = item.getInt("SEASON_ID")
            val avgRtm = item.getDouble("AVG_RTM")
            val avgLd = item.getDouble("AVG_LD")
            if(!sumMap.containsKey(year)){
                sumMap.put(year, SumRecord(0.0,0.0))
            }
            sumMap[year]!!.sumLd += avgLd
            sumMap[year]!!.sumRtm += avgRtm

            if (i < 5){
                val input : PlayerRecord = PlayerRecord(
                        SEASON_ID = year,
                        G_ID = item.getString("G_ID"),
                        AVG_RTM = avgRtm,
                        AVG_LD = avgLd,
                        G_DT = item.getString("G_DT")
                )
                recentRecord.add(input)
            }
        }
        for ((key, value) in sumMap){
            seaonAvg.add(AvgInfo(key, value.sumLd / jsonArray.length(), value.sumRtm / jsonArray.length()))
        }
        return Pair(seaonAvg, recentRecord)
    }
}

class SumRecord(
        var sumLd : Double,
        var sumRtm : Double)