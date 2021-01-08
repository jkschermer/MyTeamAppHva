package com.example.myteamapphva.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class GameResultRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collection = "GameResults"


    /**
     *  Method that gets the game results object
     */
    fun getGameResults(): Task<QuerySnapshot> {
        return db.collection(collection).get()
    }

    /**
     *  Method that sets the game results object in the db
     */
    fun setGameResults() {

    }

    /**
     *  Method that gets the collection reference of gameresults
     */
    fun getCollectionOfGameResults(): CollectionReference {
        return db.collection(collection)
    }
}