package com.example.myteamapphva.models

data class Result (
    var goals: Int?,
    var wonGames: Int?,
    var lostGames: Int?,
    var drawnGames: Int?,
    var gamesPlayed:Int?,
    var points: Int?
) {
    constructor(): this(0,0,0,0,0,0)
}