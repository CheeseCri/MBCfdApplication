package customadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportgameprototype.PlayerRecord
import com.example.sportgameprototype.R

class RecentRecordAdapter(private val context: Context,
private val recentList : ArrayList<PlayerRecord>)
: RecyclerView.Adapter<RecentRecordAdapter.recordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recordViewHolder {
        return recordViewHolder(LayoutInflater.from(context).inflate(
                R.layout.recent_record_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: recordViewHolder, position: Int) {
        holder.tvGameDate.text = recentList[position].G_DT
        holder.tvGameAvgLd.text = "%.2f M".format(recentList[position].AVG_LD)
        holder.tvGameAvgRtm.text = "%.2f 초".format(recentList[position].AVG_RTM)
    }

    override fun getItemCount(): Int {
        return recentList.size
    }

    class recordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvGameDate = view.findViewById<TextView>(R.id.tv_game_date)
        val tvGameAvgLd = view.findViewById<TextView>(R.id.tv_game_avg_ld)
        val tvGameAvgRtm = view.findViewById<TextView>(R.id.tv_game_avg_rtm)
    }
}