package com.example.myteamapphva.models

data class Team(
        var clubid: Int?,
        var name: String?
) {
    constructor() : this(0, "")
}