package com.example.myteamapphva.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myteamapphva.R
import com.example.myteamapphva.models.Result
import com.example.myteamapphva.models.Team
import com.example.myteamapphva.models.User
import com.example.myteamapphva.viewmodel.TeamViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.etEmail
import kotlinx.android.synthetic.main.fragment_register.etPassword
import java.util.*
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {
    private lateinit var fireBaseInstance: FirebaseAuth

    companion object {
        val EMAIL = Patterns.EMAIL_ADDRESS.toString()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fireBaseInstance = FirebaseAuth.getInstance()

        submitBtn.setOnClickListener { registerAccount(etEmail.text.toString(),
        etPassword.text.toString())}
    }

    private fun validate(inputField: String, regex: String? = null) : Boolean {
        var result = true
        if (inputField.isEmpty()) {
            result = false
        }

        if (regex != null) {
            if (!Pattern.compile(regex).matcher(inputField).matches()) {
                result = false
            }
        }

        return result
    }

    private fun validateAll() :Boolean {
        val validateFirstName = validate(etFirstName.text.toString())
        val validateLastName = validate(etLastName.text.toString())
        val validateEmail = validate(etEmail.text.toString(),
            EMAIL
        )
        val validatePassword = validate(etPassword.text.toString())
        var validateConfirmPassword = true

        // check if the second password is the same
        if (etPassword.text.toString() != etConfirmPassword.text.toString()) {
             validateConfirmPassword = false
        }

        // check if the user has a club name registered
        val validateClubName = validate(etTeam.text.toString().trim())

        if(validateFirstName && validateLastName && validateEmail &&
                validatePassword && validateConfirmPassword && validateClubName) {
            return true
        }

        return false
    }

    private fun registerAccount(userEmail: String, userPassword: String) {
        fireBaseInstance.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener {
            task ->

            if (task.isSuccessful && validateAll()) {
                Toast.makeText(this.activity, getString(R.string.register_succesfully_text),
                Toast.LENGTH_SHORT).show()

                // get current user
                val currentUser = fireBaseInstance.currentUser

                // make user object for storing it into the database
                val user = User()

                // make club object for storing the right club into the user
                val team = Team()

                // make result object for keeping track of the statistics by default all values are zero
                val result = Result()

                // some validation
                user.name = etFirstName.text.toString().trim()
                user.lastName = etLastName.text.toString().trim()
                user.email = etEmail.text.toString().trim()
                user.username = etEmail.text.toString()

                team.name = etTeam.text.toString().trim()
                user.team = team.name!!

                // set the club id to a random id with the method below

                team.clubid = createRandomID()

                if (currentUser != null) {
                    user.id = currentUser.uid
                    Firebase.firestore.collection(getString(R.string.collectionFirebaseUser)
                        ).document(user.id!!).set(user)

                    Firebase.firestore.collection("Teams").document(user.id!!).set(team)

                    // add empty result object to team
                    Firebase.firestore.collection("Result").document(user.id!!).set(result)
                }

                // navigate to homescreen
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            } else {
                Log.w("TAG", "Failure cannot register account", task.exception)
                Toast.makeText(this.activity, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     *  Ensures that id of club is unique
     */
    private fun createRandomID() : Int {
        return Random().nextInt(100000)
    }
}