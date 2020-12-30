package com.example.myteamapphva.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.myteamapphva.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var fireBaseInstance: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        FirebaseApp.initializeApp(this)
        navController = findNavController(R.id.nav_host_fragment)

        fireBaseInstance = FirebaseAuth.getInstance()

        navigationForBottomNavBar()

        // listener for setting the bottom nav bar
        // if it does not represent one of these fragments, then the nav bar should be shown
        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.introFragment || destination.id == R.id.registerFragment ||
                destination.id == R.id.loginFragment
            ) {
                bottomNavBar.visibility = View.INVISIBLE
            } else {
                bottomNavBar.visibility = View.VISIBLE
            }
        }

        if (fireBaseInstance.currentUser != null) {
            navController.navigate(R.id.homeFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOut -> logOut()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun logOut() {
        fireBaseInstance.signOut()
        Toast.makeText(this, "U bent succesvol uitgelogd", Toast.LENGTH_SHORT).show()
        navController.navigate(R.id.introFragment)
    }

    /**
     * Method that takes care of the navigation for the bottom navbar
     */
    private fun navigationForBottomNavBar() {
        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.menu_program -> {
                    navController.navigate(R.id.programFragment)
                    true
                }
                R.id.menu_result -> {
                    navController.navigate(R.id.resultsFragment)
                    true
                }
                R.id.menu_players -> {
                    navController.navigate(R.id.playerFragment)
                    true
                }
                else -> false
            }
        }
    }
}