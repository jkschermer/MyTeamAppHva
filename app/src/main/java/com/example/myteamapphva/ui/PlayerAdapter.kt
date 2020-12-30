package com.example.myteamapphva.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myteamapphva.R
import com.example.myteamapphva.models.User
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerAdapter(private val players: ArrayList<User>) : RecyclerView.Adapter
    <PlayerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(player: User) {
            itemView.tvFullNamePlayer.text = player.name.toString().trim() +  " " + player.lastName.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_player,
        parent, false))
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(players[position])
    }
}