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
import com.example.myteamapphva.models.User
import com.example.myteamapphva.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : Fragment() {
    private var players = arrayListOf<User>()
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var auth: FirebaseAuth
    private val userViewModel: UserViewModel by viewModels()
    private var player = User()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // init user objects
        auth.currentUser?.uid?.let { userViewModel.getPlayers(it) }

        // init user object
        auth.currentUser?.uid?.let { userViewModel.getPlayer(it) }



        observeDataUsers()
        observeCurrentUser()
        initView()
    }

    /**
     *  Method for initializing the view
     */
    private fun initView() {
        playerAdapter = PlayerAdapter(players)
        rvPlayers.layoutManager = LinearLayoutManager(context)
        rvPlayers.adapter = playerAdapter
    }

    /**
     *  Observing from any changes in data
     */
    private fun observeDataUsers() {
        userViewModel.userData.observe(viewLifecycleOwner, Observer { user ->
            players.clear()
            players.addAll(user)
            playerAdapter.notifyDataSetChanged()
        })
    }

    /**
     *  Observe current user object
     */
    private fun observeCurrentUser() {
        userViewModel.currentUser.observe(viewLifecycleOwner, Observer { user ->
            player = user
            // set textview
            tvClubNamePlayers.text = player.team
        })
    }
}

