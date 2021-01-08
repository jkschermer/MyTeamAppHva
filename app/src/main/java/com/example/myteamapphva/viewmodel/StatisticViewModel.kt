package com.example.myteamapphva.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myteamapphva.models.GameResult
import com.example.myteamapphva.models.Statistic
import com.example.myteamapphva.repository.GameResultRepository
import com.example.myteamapphva.repository.StatisticRepository
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch

class StatisticViewModel(application: Application) : AndroidViewModel(application) {
    private val statsRepo = StatisticRepository()
    private val gameResultsRepo = GameResultRepository()

    val stats = MutableLiveData<List<Statistic>>()
    val gameResults = MutableLiveData<List<GameResult>>()
    val error = MutableLiveData<String>()

    fun getGameResults() {
        viewModelScope.launch {

        }

    }


    /** Gets the statistics and updates the value via the background thread, here it
     *  it will also check for any caches to reduce reads on the actual server
     */
    fun getStatistics() {
        viewModelScope.launch {
            gameResultsRepo.getGameResults().addOnSuccessListener {
                gameResults.value = it.toObjects()


                statsRepo.getCollectionOfStatistics().addSnapshotListener(MetadataChanges.INCLUDE) { querySnapshot, exception ->

                    // checking for an exception, if there is then display the error message
                    if (exception != null) {
                        Log.d("TAG", exception.message.toString())
                        error.value = exception.message.toString()
                    }

                    // set values of statistics
                    stats.value = querySnapshot?.toObjects()

                    // find the index of the provided results
                    // get the right index
                    var indexTeam = 0

                    for(i in stats.value!!.indices) {
                        if(gameResults.value!![i].homeTeam == stats.value!![i].team) {
                            indexTeam = i
                            if(gameResults.value!!.size == 1) {
                                break
                            }
                        }
                    }
                    // first add the stats of the home team
                    val statistic1 = calculateResults(stats.value!!, gameResults.value!!, gameResults.value!![indexTeam].homeTeam!!)

                    // then add the stats of the away team
                    val statistic2 = calculateResults(stats.value!!, gameResults.value!!, gameResults.value!![indexTeam].awayTeam!!)
                    // set the stats of the first team
                    statistic1.team?.let { it1 -> statsRepo.getCollectionOfStatistics().document(it1).set(statistic1) }

                    // set the stats of the away team
                    statistic2.team?.let { it1 -> statsRepo.getCollectionOfStatistics().document(it1).set(statistic2) }

                    for (change in querySnapshot!!.documentChanges) {
                        if (change.type == DocumentChange.Type.ADDED) {
                            Log.d("TAG", "New team: ${change.document.data}")
                        }

                        val source = if (querySnapshot.metadata.isFromCache)
                            "local cache"
                        else
                            "server"
                        Log.d("TAG", "Data fetched from $source")
                    }

                }
            }
        }
    }


    /**
     *  method that calculates the result of the hometeam and the awayteam
     */
    private fun calculateResults(statistics: List<Statistic>, gameresults: List<GameResult>, team: String) : Statistic {
        val statistic = Statistic()
        statistic.team = team
        for(i in statistics.indices) {
            // check if team exists in the stats if it does then proceed

                // check if it is the home team
                if(team == gameresults[i].homeTeam) {
                    // add points to the statistic object
                    statistic.points =
                            gameresults[i].awayScore?.let {
                                gameresults[i].homeScore?.
                            let { it1 -> calculatePoints(it1, it) } }

                    // if the points equal 3 then one will be added to the games that are won
                    if (statistic.points == 3) {
                        statistic.wonGames = statistic.wonGames?.plus(1)
                    }
                    // if the points equal 1 then one will be added to the games drawn
                    if (statistic.points == 1) {
                        statistic.wonGames = statistic.drawnGames?.plus(1)
                    }

                    if(statistic.points == 0) {
                        statistic.lostGames = statistic.lostGames?.plus(1)
                    }

                    // homescore added to the goals scored
                    statistic.goals = gameresults[i].homeScore?.let { statistic.goals?.plus(it) }
                    statistic.gamesPlayed = statistic.gamesPlayed?.plus(1)

                    return statistic
                }

                // check if it is the away team
                if(team == gameresults[i].awayTeam) {
                    // add points to the statistic object
                    statistic.points = gameresults[i].awayScore?.let {
                        gameresults[i].homeScore?.let { it1 ->
                            calculatePoints(it,
                                    it1)
                        }
                    }

                    if (statistic.points == 3) {
                        statistic.wonGames = statistic.wonGames?.plus(1)
                    }

                    if (statistic.points == 1) {
                        statistic.drawnGames = statistic.drawnGames?.plus(1)
                    }

                    if(statistic.points == 0) {
                        statistic.lostGames = statistic.lostGames?.plus(1)
                    }

                    statistic.goals = gameresults[i].awayScore?.let { statistic.goals?.plus(it) }
                    statistic.gamesPlayed = statistic.gamesPlayed?.plus(1)

                    return statistic
                }
        }

        // if there is no object that qualifies just return an empty one
        return statistic
    }

    /**
     *  method that calculates points
     */
    private fun calculatePoints(homeScore: Int, awayScore: Int): Int{
        val WIN = 3
        val LOSS = 0
        val DRAW = 1


        if (homeScore > awayScore) {
            return WIN
        }

        if(homeScore == awayScore) {
            return DRAW
        }

        return LOSS
    }
}


