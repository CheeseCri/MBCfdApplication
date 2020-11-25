package com.example.sportgameprototype

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

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
        val context = holder.itemView.context
        holder.tvHomeTeam.text = gameList[position].homeTeam
        holder.tvAwayTeam.text = gameList[position].awayTeam
        holder.tvGameStation.text = gameList[position].stationName
        holder.tvGameTime.text = gameList[position].gameTime
        holder.btDoLottery.setOnClickListener {
            val intent = Intent(context, LotteryActivity::class.java)
            intent.putExtra("gameID", gameList[position].gameID)
            intent.putExtra("homeTeam", gameList[position].homeTeam)
            intent.putExtra("awayTeam", gameList[position].awayTeam)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return gameList.size
    }


    class gameViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val tvHomeTeam = view.findViewById<TextView>(R.id.tv_home_team)
        val tvAwayTeam = view.findViewById<TextView>(R.id.tv_away_team)
        val tvGameStation = view.findViewById<TextView>(R.id.tv_game_station)
        val tvGameTime = view.findViewById<TextView>(R.id.tv_game_time)
        val btDoLottery = view.findViewById<Button>(R.id.bt_do_lottery)
    }
}





