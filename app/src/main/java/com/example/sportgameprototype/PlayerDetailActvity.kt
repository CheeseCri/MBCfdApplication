package com.example.sportgameprototype

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        val playerDetailInfo = HTTPtask().execute(getString(R.string.JSPURL), "player",playerID, "detail").get()
        val playerRecordInfo = HTTPtask().execute(getString(R.string.JSPURL), "player",playerID, "record").get()
        Log.d("USERLOG-detail", playerDetailInfo)
        Log.d("USERLOG-detail", playerRecordInfo)
        val (avgRecord, recordSummary) = addRecord(JSONArray(playerRecordInfo))
        tvPID.text = playerID
        tvDebugSql.text = playerRecordInfo

        for (avg in avgRecord)
            Log.d("USERLOG-DETAIL", "${avg.SEASON_ID},${avg.AVG_LD}${avg.AVG_RTM}")
        for (record in recordSummary)
            Log.d("USERLOG-DETAIL", "${record.G_DT},${record.AVG_LD},${record.AVG_RTM},")
    }
    private fun addRecord(jsonArray : JSONArray) : Pair<ArrayList<AvgInfo>, ArrayList<PlayerRecord>>{
        var avgResult : ArrayList<AvgInfo> = ArrayList()
        var recordSummary : ArrayList<PlayerRecord> = ArrayList()
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
                recordSummary.add(input)
            }
        }

        for ((key, value) in sumMap){
            avgResult.add(AvgInfo(key, value.sumLd / jsonArray.length(), value.sumRtm / jsonArray.length()))
        }

        return Pair(avgResult, recordSummary)
    }
}

class AvgInfo(
        var SEASON_ID : Int,
        var AVG_LD : Double,
        var AVG_RTM : Double){
}
class SumRecord(
        var sumLd : Double,
        var sumRtm : Double)