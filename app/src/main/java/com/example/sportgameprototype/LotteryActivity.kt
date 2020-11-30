package com.example.sportgameprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray

class LotteryActivity : AppCompatActivity() {
    private lateinit var tvLotteryGameDate : TextView
    private lateinit var imgvHomeTeam : ImageView
    private lateinit var imgvAwayTeam : ImageView
    private lateinit var rvHomePlayerList : RecyclerView
    private lateinit var rvAwayPlayerList : RecyclerView
    private lateinit var btSendLottery : Button
    private lateinit var gameInfo: GameInfo
    private lateinit var homePlayerList : ArrayList<PlayerInfo>
    private lateinit var awayPlayerList : ArrayList<PlayerInfo>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottery)
        val sql : String = """
            SELECT * 
            FROM `sample_data_game` 
            WHERE G_ID = '%s'
        """.trimIndent()
        val player_sql : String = """
            SELECT * FROM `sample_data_player` 
            WHERE T_ID = '%s' AND POS_NO IS NOT NULL AND BACK_NO IS NOT NULL
        """.trimIndent()
        val insert_lottery_sql : String = """
            INSERT INTO 
            `sample_user_lottery_record`(`U_ID`, `G_ID`, `BEST_RM_PID`, `BET_LD_PID`) 
            VALUES ('%s', '%s', '%s', '%s') 
        """.trimIndent()

        val homeRtmCheckBoxList = ArrayList<CheckBoxData>()
        val homeLdCheckBoxList = ArrayList<CheckBoxData>()
        val awayRtmCheckBoxList = ArrayList<CheckBoxData>()
        val awayLdCheckBoxList = ArrayList<CheckBoxData>()

        tvLotteryGameDate = findViewById(R.id.tv_lottery_game_date)
        imgvHomeTeam = findViewById(R.id.imgv_lottery_home_team)
        imgvAwayTeam = findViewById(R.id.imgv_lottery_away_team)
        rvHomePlayerList = findViewById(R.id.rv_lottery_hplayer_list)
        rvAwayPlayerList = findViewById(R.id.rv_lottery_aplayer_list)
        btSendLottery = findViewById(R.id.bt_send_lottery)

        //userID의 경우 추후 shardPreference에서 처리 해야함.
        val userID = "yunghn"
        val gameID = intent.getStringExtra("gameID")
        val gameData = HTTPtask().execute(getString(R.string.JSPURL), "query", String.format(sql,gameID)).get()
        gameInfo = GameListActivity().addGameListArray(JSONArray(gameData)).get(0)
        changeImage(imgvAwayTeam, gameInfo.awayTeam)
        changeImage(imgvHomeTeam, gameInfo.homeTeam)

        val homePlayerData = HTTPtask().execute(
                getString(R.string.JSPURL), "query",
                String.format(player_sql, gameInfo.homeTeam)).get()
        val awayPlayerData = HTTPtask().execute(
                getString(R.string.JSPURL), "query",
                String.format(player_sql, gameInfo.awayTeam)).get()
        homePlayerList = PlayerListActivity().addPlayerDataArray(JSONArray(homePlayerData))
        awayPlayerList = PlayerListActivity().addPlayerDataArray(JSONArray(awayPlayerData))

        rvHomePlayerList.layoutManager = LinearLayoutManager(this)
        rvHomePlayerList.adapter = LotteryAdapter(this, homePlayerList, homeRtmCheckBoxList, homeLdCheckBoxList)
        rvAwayPlayerList.layoutManager = LinearLayoutManager(this)
        rvAwayPlayerList.adapter = LotteryAdapter(this, awayPlayerList, awayRtmCheckBoxList, awayLdCheckBoxList)

        btSendLottery.setOnClickListener {
        // 버튼 클릭 시 DB에 선택된 데이터 전송.
        // DB 테이블 : `sample_user_lottery_record`
        // 데이터 : U_ID, G_ID, BEST_RM_PID, BEST_LD_PID

            /*
            구현 순서 :
            1. 홈, 어웨이 RTM list, LD list 체크된 갯수 각각 확인
            if 각각 1개 초과할 경우 토스트 메세지 출력
            else U_ID, G_ID, RTM p_id, LD p_id 가져와서 DB에 전송
             */
            val rtmList = ArrayList<CheckBoxData>()
            rtmList.addAll(homeRtmCheckBoxList)
            rtmList.addAll(awayRtmCheckBoxList)
            val ldList = ArrayList<CheckBoxData>()
            ldList.addAll(homeLdCheckBoxList)
            ldList.addAll(awayLdCheckBoxList)
//            Toast.makeText(this, "${rtmList.filter{it.checked}.size} ${ldList.filter{it.checked}.size} ", Toast.LENGTH_LONG).show()
            if(rtmList.filter{it.checked}.size > 1 || ldList.filter{it.checked}.size > 1){
                Toast.makeText(this, "선수는 종목당 1명만 선택할 수 있습니다.", Toast.LENGTH_LONG).show()
            } else {
                // 추후 서버쪽 응답코드를 만들어서 제대로 통신이 되었는지 검증하는 과정 필요.
                HTTPtask().execute(
                        // DB 테이블 구조 (`U_ID`, `G_ID`, `BEST_RM_PID`, `BET_LD_PID`)
                        getString(R.string.JSPURL), "query",
                        String.format(
                                insert_lottery_sql,
                                userID,
                                gameID,
                                rtmList.filter{it.checked}[0].id,
                                ldList.filter{it.checked}[0].id))
                Toast.makeText(this, "rtmP_ID : ${rtmList.filter{it.checked}[0].id}\n ldP_ID : ${ldList.filter{it.checked}[0].id}", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    fun changeImage(view : ImageView, teamName : String){
        when(teamName){
            "HH" -> view.setImageResource(R.drawable.logo_hh)
            "HT" -> view.setImageResource(R.drawable.logo_kia)
            "KT" -> view.setImageResource(R.drawable.logo_kt)
            "LG" -> view.setImageResource(R.drawable.logo_lg)
            "LT" -> view.setImageResource(R.drawable.logo_lt)
            "NC" -> view.setImageResource(R.drawable.logo_nc)
            "OB" -> view.setImageResource(R.drawable.logo_ob)
            "SK" -> view.setImageResource(R.drawable.logo_sk)
            "SS" -> view.setImageResource(R.drawable.logo_ss)
            "WO" -> view.setImageResource(R.drawable.logo_wo)
        }
    }

}