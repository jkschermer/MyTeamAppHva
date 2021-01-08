package com.example.myteamapphva.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myteamapphva.R
import com.example.myteamapphva.models.GameResult
import com.example.myteamapphva.viewmodel.GameResultViewModel
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {
    private val gameResult = arrayListOf<GameResult>()
    private lateinit var resultAdapter: ResultAdapter
    private val gameResultViewModel: GameResultViewModel by viewModels()

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

        // init collection of game program
        gameResultViewModel.getGameProgram()
        observeGameResults()

        initView()
    }

    private fun initView() {
        // initialize some dummy data for checking if data gets displayed in the right manner
        resultAdapter = ResultAdapter(gameResult)
        rvResults.layoutManager = LinearLayoutManager(context)
        rvResults.adapter = resultAdapter
    }

    private fun observeGameResults() {
        gameResultViewModel.gameResults.observe(viewLifecycleOwner, Observer {
            gameResult.clear()
            gameResult.addAll(it)
            resultAdapter.notifyDataSetChanged()
        })
    }
}