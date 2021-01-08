package com.example.myteamapphva.models


data class GameResult(
        var id: Int?,
        var date: String?,
        var homeTeam: String?,
        var awayTeam: String?,
        var homeScore: Int?,
        var awayScore: Int?
)  {
    constructor() : this(0,"", "","",0,0)
}