package customadapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportgameprototype.R
import com.example.sportgameprototype.UserRecordItem
import com.example.sportgameprototype.changeTeam

class UserRecordAdapter(private val context : Context,
                        private val userRecordItemList : ArrayList<UserRecordItem>)
    : RecyclerView.Adapter<UserRecordAdapter.RecordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(LayoutInflater.from(context).inflate(
                R.layout.user_record_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val tmpItem : UserRecordItem = userRecordItemList[position]
        holder.tvRecordHomeTeam.text = changeTeam(tmpItem.homeTeam)
        holder.tvRecordAwayTeam.text = changeTeam(tmpItem.awayTeam)
        // 연, 월일 나눠서 출력할 수 있도록 함.
        Log.d("USERLOG-RecordAdapter", "${tmpItem.gameDate.toString()}")
        holder.tvRecordGameYear.text = tmpItem.gameYear
        holder.tvRecordGameMMDD.text = tmpItem.gameMMDD

        holder.tvRecordLdBp.text = tmpItem.bestLdPlayerName
        holder.tvRecordRtmBp.text = tmpItem.bestRtmPlayerName

        holder.imgvRecordLdResult.setImageResource( if(tmpItem.isBestLd) R.drawable.ic_success else R.drawable.ic_failure)
        holder.imgvRecordRtmResult.setImageResource( if(tmpItem.isBestRtm) R.drawable.ic_success else R.drawable.ic_failure)

    }

    override fun getItemCount(): Int {
        return userRecordItemList.size
    }
    class RecordViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val tvRecordHomeTeam : TextView = view.findViewById(R.id.tv_record_home_team)
        val tvRecordAwayTeam : TextView = view.findViewById(R.id.tv_record_away_team)

        val tvRecordGameYear : TextView = view.findViewById(R.id.tv_record_game_year)
        val tvRecordGameMMDD : TextView = view.findViewById(R.id.img_away_team)

        val tvRecordRtmBp : TextView = view.findViewById(R.id.tv_record_rtm_bp)
        val tvRecordLdBp : TextView = view.findViewById(R.id.tv_record_ld_bp)

        val imgvRecordRtmResult : ImageView = view.findViewById(R.id.imgv_record_rtm_result)
        val imgvRecordLdResult : ImageView = view.findViewById(R.id.imgv_record_ld_result)
    }
}
