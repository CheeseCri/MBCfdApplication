package customadapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sportgameprototype.*
import java.text.SimpleDateFormat

class GameAdapter (private val context : Context,
                   private val gameList : ArrayList<GameInfo>
)
    : RecyclerView.Adapter<GameAdapter.gameViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): gameViewHolder {
        return gameViewHolder(LayoutInflater.from(context).inflate(
            R.layout.game_info_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: gameViewHolder, position: Int){
        val format = SimpleDateFormat("HH:mm")
        val context = holder.itemView.context
        // 홈, 어웨이 팀 로고 변경
        changeImage(holder.imgvHomeTeam, gameList[position].homeTeam)
        changeImage(holder.imgvAwayTeam, gameList[position].awayTeam)

        holder.tvGameStation.text = changeStation(gameList[position].stationName)

        holder.tvGameTime.text = format.format(gameList[position].gDate)
        holder.btDoLottery.setOnClickListener {
            val intent = Intent(context, LotteryActivity::class.java)
            intent.putExtra("gameID", gameList[position].gameID)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return gameList.size
    }


    class gameViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgvHomeTeam = view.findViewById<ImageView>(R.id.imgv_home_team)
        val imgvAwayTeam = view.findViewById<ImageView>(R.id.imgv_away_team)
        val tvGameStation = view.findViewById<TextView>(R.id.tv_game_station)
        val tvGameTime = view.findViewById<TextView>(R.id.tv_game_time)
        val btDoLottery = view.findViewById<Button>(R.id.bt_do_lottery)
    }
}





