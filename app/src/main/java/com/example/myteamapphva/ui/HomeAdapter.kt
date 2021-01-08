package com.example.myteamapphva.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myteamapphva.R
import com.example.myteamapphva.models.Statistic
import kotlinx.android.synthetic.main.item_ranking.view.*

class HomeAdapter(private val statistic: ArrayList<Statistic>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ranking,
            parent, false))
    }

    override fun getItemCount(): Int {
        return statistic.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stats = statistic[position]

        if(statistic.size != 0) {
            addRows(holder)
        }

        holder.itemView.tvNumber.text = (position+1).toString() + "."
        holder.itemView.tvClubName.text = stats.team.toString()

        holder.itemView.tvPlayedGames.text = stats.gamesPlayed.toString()
        holder.itemView.tvWonGames.text = stats.wonGames.toString()
        holder.itemView.tvDrawnGames.text = stats.drawnGames.toString()
        holder.itemView.tvLostGames.text = stats.lostGames.toString()
        holder.itemView.tvGoals.text = stats.goals.toString()
        holder.itemView.tvPoints.text = stats.points.toString()
    }

    private fun addRows(holder: ViewHolder) {
        val tableRow = holder.itemView.tableRowRanking
        val tableLayout = holder.itemView.tl

        for(i in 0 until statistic.size) {
            tableLayout?.removeView(tableRow)
            tableLayout.addView(tableRow)
        }
    }
}