package com.example.myteamapphva.repository

import com.example.myteamapphva.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class UserRepository() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    // name of the collection in the firebase database
    private val collection = "Users"

    /**
     *  Method to get the current player from a club, which is basically the user
     */
    fun getPlayer(id: String): Task<DocumentSnapshot> {
        return db.collection(collection).document(id).get()
    }

    /**
     *  Method to get the players from the club where the current player belongs to
     */
    fun getPlayers(user: User): Task<QuerySnapshot> {
        return db.collection(collection).whereEqualTo("team", user.team).get()
    }

    /**
     * Method that gets the reference of the collection
     */
    fun getCollectionOfPlayers(): CollectionReference {
        return db.collection(collection)
    }
}
