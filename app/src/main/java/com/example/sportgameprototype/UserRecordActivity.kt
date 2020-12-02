package com.example.sportgameprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class UserRecordActivity : AppCompatActivity() {
    lateinit var tvLdTryCount : TextView
    lateinit var tvLdSuccessCount : TextView
    lateinit var tvRtmTryCount : TextView
    lateinit var tvRtmSuccessCount : TextView
    lateinit var rvLotteryUserRecord : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_record)

        // 뷰 선언
        tvLdTryCount = findViewById(R.id.tv_ld_try_count)
        tvLdSuccessCount = findViewById(R.id.tv_ld_success_count)
        tvRtmTryCount = findViewById(R.id.tv_rtm_try_count)
        tvRtmSuccessCount = findViewById(R.id.tv_rtm_success_count)
        rvLotteryUserRecord = findViewById(R.id.rv_lottery_user_record)

        // ***임시로 넣어놓은 아이디임!***
        val userID = "test1"
        // 전송받은 데이터 분류 및 데이터 클래스에 삽입.
        val lotteryRecordData : String = HTTPtask().execute(getString(R.string.JSPURL), "userlottery", userID).get()
        val lotteryRecordJson : JSONObject = JSONObject(lotteryRecordData)
        val lotteryRecordList : JSONArray = lotteryRecordJson.getJSONArray("gamelist")
        val lotteryRecordItems : ArrayList<UserRecordItem> = addRecordListArray(lotteryRecordList)

        // 데이터 뷰에 전달.
        tvLdTryCount.text = lotteryRecordJson.getString("try_count")
        tvRtmTryCount.text = lotteryRecordJson.getString("try_count")
        tvLdSuccessCount.text = lotteryRecordJson.getString("ld_success")
        tvRtmSuccessCount.text = lotteryRecordJson.getString("rtm_success")

        rvLotteryUserRecord.addItemDecoration(DividerItemDecoration(this, 1))
        rvLotteryUserRecord.layoutManager = LinearLayoutManager(this)
        rvLotteryUserRecord.adapter = UserRecordAdapter(this, lotteryRecordItems)




    }

    fun addRecordListArray(jsonArray : JSONArray): ArrayList<UserRecordItem> {
        val recordArray : ArrayList<UserRecordItem> = ArrayList()
        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i) as JSONObject
            Log.d("USERLOG-UserRecord", item.toString())
            val input = UserRecordItem(
                    gameDate = item.getString("G_DT"),
                    homeTeam = item.getString("HOME_ID"),
                    awayTeam = item.getString("AWAY_ID"),
                    bestLdPlayerName = item.getString("BEST_LD_PMN"),
                    bestRtmPlayerName = item.getString("BEST_RTM_PMN"),
                    isBestLd = item.getString("BEST_LD_BOOLEAN") != "0",
                    isBestRtm = item.getString("BEST_RTM_BOOLEAN") != "0"
            )
            recordArray.add(input)
        }
        Log.d("USERLOG-ADDPLAYERDATA", recordArray.toString())
        return recordArray
    }
}