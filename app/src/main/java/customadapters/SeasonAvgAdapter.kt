package customadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportgameprototype.AvgInfo
import com.example.sportgameprototype.R

class SeasonAvgAdapter(private val context: Context,
                       private val avgList : ArrayList<AvgInfo>)
    : RecyclerView.Adapter<SeasonAvgAdapter.avgViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): avgViewHolder {
        return avgViewHolder(LayoutInflater.from(context).inflate(
                R.layout.season_avg_item,
                parent,
                false
        ))
    }

    override fun onBindViewHolder(holder: avgViewHolder, position: Int) {
        holder.tvSeasonId.text = avgList[position].SEASON_ID.toString()
//        holder.tvSeasonAvgLd.text = avgList[position].AVG_LD.toString()
        holder.tvSeasonAvgLd.text = "%.2f M".format(avgList[position].AVG_LD)
        holder.tvSeasonAvgRtm.text = "%.2f 초".format(avgList[position].AVG_RTM)
    }

    override fun getItemCount(): Int {
        return avgList.size
    }

    class avgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSeasonId = view.findViewById<TextView>(R.id.tv_game_date)
        val tvSeasonAvgLd = view.findViewById<TextView>(R.id.tv_game_avg_ld)
        val tvSeasonAvgRtm = view.findViewById<TextView>(R.id.tv_game_avg_rtm)
    }

}