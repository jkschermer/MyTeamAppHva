package com.example.myteamapphva.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myteamapphva.R
import com.example.myteamapphva.models.GameResult
import kotlinx.android.synthetic.main.item_program.view.*

class ProgramAdapter(private var program: ArrayList<GameResult>) : RecyclerView.Adapter
    <ProgramAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun databind(gameResult: GameResult) {
            itemView.tvDate.text = gameResult.date.toString()
            itemView.tvHomeTeam.text = gameResult.homeTeam
            itemView.tvAwayTeam.text = gameResult.awayTeam
            itemView.tvLine.text = "-"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_program,
        parent, false))
    }

    override fun getItemCount(): Int {
        return program.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(program[position])
    }
}