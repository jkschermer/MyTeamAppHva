package com.example.myteamapphva.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myteamapphva.models.User
import com.example.myteamapphva.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val currentUser = MutableLiveData<User>()
    val userData = MutableLiveData<List<User>>()
    val error = MutableLiveData<String>()


    /**
     *  Method that gets the current player
     */
    fun getPlayer(id: String) {
        viewModelScope.launch {
            userRepo.getPlayer(id).addOnSuccessListener {
                doc -> currentUser.value = doc.toObject<User>()
            }
        }
    }


    /**
     *  Method that first gets the current player, so that the list of players for a team can be
     *  displayed
     */
    fun getPlayers(id: String) {
        viewModelScope.launch {
            userRepo.getPlayer(id).addOnSuccessListener {
                doc -> currentUser.value = doc.toObject<User>()
                val user = doc.toObject<User>()!!

                userRepo.getPlayers(user).addOnSuccessListener {
                    userData.value = it.toObjects()
                }.addOnFailureListener {
                    error.value = "An error has occurred, please try to reboot the app"
                }
            }
        }
    }

    /**
     *  Method that first gets the current player, so that the list of players for a team can be
     *  displayed
     */
//    fun getPlayers(id: String, user: User) {
//        val docRefGetPlayer = db.collection(collection).document(id).get()
//        val docRefGetPlayers = db.collection(collection).whereEqualTo("team", user.team)
//
//        viewModelScope.launch {
//           docRefGetPlayer.addOnCompleteListener(MetadataChanges.INCLUDE) {
//
//           }
//        }
//    }
}