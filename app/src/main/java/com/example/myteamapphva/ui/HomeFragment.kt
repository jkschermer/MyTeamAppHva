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
import com.example.myteamapphva.models.Statistic
import com.example.myteamapphva.viewmodel.GameResultViewModel
import com.example.myteamapphva.viewmodel.StatisticViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {
    private var statistic = arrayListOf<Statistic>()
    private lateinit var homeAdapter: HomeAdapter
    private val statisticViewModel: StatisticViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // init collection of results
        statisticViewModel.getStatistics()

        observeResults()
        initView()
    }

    private fun initView() {
        homeAdapter = HomeAdapter(statistic)
        rvRanking.layoutManager = LinearLayoutManager(context)
        rvRanking.adapter = homeAdapter
    }
    
    private fun observeResults() {
        statisticViewModel.stats.observe(viewLifecycleOwner, Observer{
            res ->
            statistic.clear()
            statistic.addAll(res)
            // sort the statistics on its points
            statistic.sortWith(compareByDescending { it.points})
            homeAdapter.notifyDataSetChanged()
        })
    }
}