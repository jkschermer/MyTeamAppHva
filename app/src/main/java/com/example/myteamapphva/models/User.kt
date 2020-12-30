package com.example.myteamapphva.models

data class User (
        var id: String?,
        var username: String?,
        var name: String?,
        var lastName: String?,
        var email: String?,
        val admin: Boolean?,
        var team: String?
) {
    constructor() : this("","", "","","", false, "")
}