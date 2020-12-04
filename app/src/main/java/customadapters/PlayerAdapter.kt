package customadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.*
import com.example.sportgameprototype.ItemClickListener
import com.example.sportgameprototype.PlayerInfo
import com.example.sportgameprototype.R
import com.example.sportgameprototype.posTransform

class PlayerAdapter( private val context: Context, 
                     private val itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<playerViewHolder>() {

    var mylist = arrayListOf<PlayerInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): playerViewHolder {
        return playerViewHolder(LayoutInflater.from(context).inflate(
            R.layout.data_list_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: playerViewHolder, position: Int) {
        val pID = mylist[position].playerId
        holder.tvNum.text = mylist[position].backNumber.toString()
        holder.tvName.text = mylist[position].playerName
        holder.tvTeam.text = posTransform(mylist[position].playerPos)

        holder.detailButton.setOnClickListener{
            itemClickListener.onItemButtonClick(pID)
        }
    }

    override fun getItemCount(): Int {
        return mylist.size
    }
    fun setFilter(newList : ArrayList<PlayerInfo>){
        mylist = ArrayList<PlayerInfo>()
        mylist.addAll(newList)
        notifyDataSetChanged()
    }
}

class playerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvNum = view.findViewById<TextView>(R.id.tvNum)
    val tvName= view.findViewById<TextView>(R.id.tvName)
    val tvTeam= view.findViewById<TextView>(R.id.tvPos)
    val detailButton = view.findViewById<Button>(R.id.btDetail)
}