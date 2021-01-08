package com.example.myteamapphva.models

data class Statistic (
    var goals: Int?,
    var wonGames: Int?,
    var lostGames: Int?,
    var drawnGames: Int?,
    var gamesPlayed:Int?,
    var points: Int?,
    var team: String?
) {
    constructor(): this(0,0,0,0,0,0, "")
}