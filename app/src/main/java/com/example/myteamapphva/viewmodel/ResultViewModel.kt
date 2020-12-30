package com.example.myteamapphva.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myteamapphva.models.Result
import com.example.myteamapphva.repository.ResultRepository
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch

class ResultViewModel(application: Application) : AndroidViewModel(application) {
    private val resultRepository = ResultRepository()

    val results = MutableLiveData<List<Result>>()
    val error = MutableLiveData<String>()


    fun getResults() {
        viewModelScope.launch {
            resultRepository.getResults().addOnSuccessListener {
                results.value = it.toObjects()
            }.addOnFailureListener {
                error.value = it.message
            }
        }
    }
}