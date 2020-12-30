package com.example.myteamapphva.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ResultRepository {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collection = "Result"

    /**
     *  Method to get the results of the fixtures
     */
    fun getResults() : Task<QuerySnapshot> {
        return db.collection(collection).get()
    }
}