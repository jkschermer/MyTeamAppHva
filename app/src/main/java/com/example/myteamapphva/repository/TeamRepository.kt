package com.example.myteamapphva.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class TeamRepository {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collection = "Teams"

    /**
     *  Method to get all the clubs
     */
    fun getTeams() : Task<QuerySnapshot> {
        return db.collection(collection).get()
    }
}