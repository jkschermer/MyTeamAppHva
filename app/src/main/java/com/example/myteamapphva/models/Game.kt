package com.example.myteamapphva.models

data class Game(
        var id: Int?,
        var date: String?,
        var homeTeam: String?,
        var awayTeam: String?,
        var homeScore: Int?,
        var awayScore: Int?
)  {
    constructor() : this(0,null, "","",0,0)
}