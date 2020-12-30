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
import com.example.myteamapphva.models.Team
import com.example.myteamapphva.models.Result
import com.example.myteamapphva.viewmodel.ResultViewModel
import com.example.myteamapphva.viewmodel.TeamViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var result = arrayListOf<Result>()
    private var teams = arrayListOf<Team>()
    private lateinit var homeAdapter: HomeAdapter
    private val teamViewModel: TeamViewModel by viewModels()
    private val resultViewModel: ResultViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // init collection of club
        teamViewModel.getTeams()

        // init collection of results
        resultViewModel.getResults()

        observeTeams()
        observeResults()
        initView()
    }

    private fun initView() {
        homeAdapter = HomeAdapter(result, teams)
        rvRanking.layoutManager = LinearLayoutManager(context)
        rvRanking.adapter = homeAdapter
    }

    private fun observeTeams() {
        teamViewModel.teams.observe(viewLifecycleOwner, Observer { team ->
            teams.clear()
            teams.addAll(team)
            homeAdapter.notifyDataSetChanged()
        })
    }
    
    private fun observeResults() {
        resultViewModel.results.observe(viewLifecycleOwner, Observer{
            res ->
            result.clear()
            result.addAll(res)
            homeAdapter.notifyDataSetChanged()
        })
    }
}