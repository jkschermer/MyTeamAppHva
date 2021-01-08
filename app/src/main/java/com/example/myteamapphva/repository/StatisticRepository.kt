package com.example.myteamapphva.repository

import com.example.myteamapphva.models.GameResult
import com.example.myteamapphva.models.Statistic
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class StatisticRepository {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collection = "Statistic"

    /**
     *  Method to get the statistics
     */
    fun getStatistics() : Task<QuerySnapshot> {
        return db.collection(collection).get()
    }

    /**
     *  Method for getting the reference of the collection of statistics
     */
    fun getCollectionOfStatistics(): CollectionReference {
        return db.collection(collection)
    }


    /**
     *  Method to update the statisitics
     */
    fun updateStatistics(stat: Statistic, clubName: String): Task<Void> {
        return db.collection(collection).document(clubName).set(stat)
    }


    /**
     *  Method to get the results and check if the team already exists, if it already exists makes
     *  sure that it cannot be added
     */
//    fun scanResults(id: String): Boolean {
//        val temp = db.collection(collection).document(id).get()
//
//        temp.result.id
//
//    }
}