package com.example.myteamapphva.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myteamapphva.R
import com.example.myteamapphva.models.Team
import com.example.myteamapphva.models.Result
import kotlinx.android.synthetic.main.item_ranking.view.*

class HomeAdapter(private val result: ArrayList<Result>, private val team: ArrayList<Team>) :
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
        return team.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val results = result[position]
        val t = team[position]

        if(team.size != 0) {
            addRows(holder)
        }

        holder.itemView.tvNumber.text = (position+1).toString() + "."
        holder.itemView.tvClubName.text = t.name.toString()

        holder.itemView.tvPlayedGames.text = results.gamesPlayed.toString()
        holder.itemView.tvWonGames.text = results.wonGames.toString()
        holder.itemView.tvDrawnGames.text = results.drawnGames.toString()
        holder.itemView.tvLostGames.text = results.lostGames.toString()
        holder.itemView.tvGoals.text = results.goals.toString()
        holder.itemView.tvPoints.text = results.points.toString()
    }

    private fun addRows(holder: ViewHolder) {
        val tableRow = holder.itemView.tableRowRanking
        val tableLayout = holder.itemView.tl

        for(i in 0 until result.size) {
            tableLayout?.removeView(tableRow)
            tableLayout.addView(tableRow)
        }
    }
}