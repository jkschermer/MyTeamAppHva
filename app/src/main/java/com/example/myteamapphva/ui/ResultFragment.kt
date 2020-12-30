package com.example.myteamapphva.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myteamapphva.R
import com.example.myteamapphva.helper.DateFormatHelper
import com.example.myteamapphva.models.Game
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {
    private val gameResult = arrayListOf<Game>()
    private lateinit var resultAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        // initialize some dummy data for checking if data gets displayed in the right manner

        val game = Game()
        val game1 = Game()
        game.id = 1000
        game.homeTeam = "AJAX"
        game.awayTeam = "FEYENOORD"
        game.homeScore = 1
        game.awayScore = 1

        val currentTime = DateFormatHelper().getCurrentDateTime()
        val currentTimeInString = DateFormatHelper().formatToString(currentTime, "dd-MM-yyyy")
        game.date = currentTimeInString

        game1.id = 1001
        game1.homeTeam = "PSV"
        game1.awayTeam = "AZ"
        game1.homeScore = 1
        game1.awayScore = 3

        game1.date = currentTimeInString

        gameResult.add(game)
        gameResult.add(game1)

        resultAdapter = ResultAdapter(gameResult)
        rvResults.layoutManager = LinearLayoutManager(context)
        rvResults.adapter = resultAdapter
    }
}