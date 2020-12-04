package customadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportgameprototype.R
import com.example.sportgameprototype.UserRecordItem

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
        holder.tvRecordGameDate.text = tmpItem.gameDate
        holder.tvRecordHomevsaway.text = tmpItem.homeTeam + " VS " + tmpItem.awayTeam
        holder.tvRecordLdBp.text = tmpItem.bestLdPlayerName
        holder.tvRecordRtmBp.text = tmpItem.bestRtmPlayerName
        holder.tvRecordLdResult.text = if(tmpItem.isBestLd) "성공" else "실패"
        holder.tvRecordRtmResult.text = if(tmpItem.isBestRtm) "성공" else "실패"
    }

    override fun getItemCount(): Int {
        return userRecordItemList.size
    }
    class RecordViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tvRecordGameDate : TextView = view.findViewById(R.id.tv_record_game_date)
        val tvRecordHomevsaway : TextView = view.findViewById(R.id.tv_record_homevsaway)
        val tvRecordRtmBp : TextView = view.findViewById(R.id.tv_record_rtm_bp)
        val tvRecordLdBp : TextView = view.findViewById(R.id.tv_record_ld_bp)
        val tvRecordRtmResult : TextView = view.findViewById(R.id.tv_record_rtm_result)
        val tvRecordLdResult : TextView = view.findViewById(R.id.tv_record_ld_result)
    }
}
