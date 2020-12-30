package com.example.myteamapphva.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myteamapphva.models.Team
import com.example.myteamapphva.repository.TeamRepository
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch

class TeamViewModel(application: Application) : AndroidViewModel(application) {

    val teamRepository = TeamRepository()
    val teams = MutableLiveData<List<Team>>()
    val error = MutableLiveData<String>()

    fun getTeams() {
        viewModelScope.launch {
            teamRepository.getTeams().addOnSuccessListener { team ->
                teams.value = team.toObjects()
            }.addOnFailureListener {
                error.value = it.message
            }
        }
    }
}