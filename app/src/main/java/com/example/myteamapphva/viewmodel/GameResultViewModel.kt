package com.example.myteamapphva.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myteamapphva.models.GameResult
import com.example.myteamapphva.repository.GameResultRepository
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch

class GameResultViewModel(application: Application) : AndroidViewModel(application) {

    private val gameResultRepository = GameResultRepository()
    val gameResults = MutableLiveData<List<GameResult>>()
    val error = MutableLiveData<String>()

    fun getGameProgram() {
        viewModelScope.launch {
            gameResultRepository.getGameResults().addOnSuccessListener {
                gameResults.value = it.toObjects()
            }.addOnFailureListener {
                error.value = it.message.toString()
            }
        }
    }
}