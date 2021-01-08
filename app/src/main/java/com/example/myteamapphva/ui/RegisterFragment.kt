package com.example.myteamapphva.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myteamapphva.R
import com.example.myteamapphva.models.Statistic
import com.example.myteamapphva.models.User
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

        submitBtn.setOnClickListener {
            registerAccount(etEmail.text.toString(),
                    etPassword.text.toString())
        }
    }

    private fun validate(inputField: String, regex: String? = null): Boolean {
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

    private fun validateAll(): Boolean {
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

        if (validateFirstName && validateLastName && validateEmail &&
                validatePassword && validateConfirmPassword && validateClubName) {
            return true
        }

        return false
    }

    private fun registerAccount(userEmail: String, userPassword: String) {
        fireBaseInstance.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->

            if (task.isSuccessful && validateAll()) {
                Toast.makeText(this.activity, getString(R.string.register_succesfully_text),
                        Toast.LENGTH_SHORT).show()

                // gets current user if registration is successful
                val currentUser = fireBaseInstance.currentUser

                // make user object for storing values in the database
                val user = User()

                // make stats object for keeping track of the statistics by default all values are zero
                val stat = Statistic()

                // set values of user object and id of stats object
                user.name = etFirstName.text.toString().trim()
                user.lastName = etLastName.text.toString().trim()
                user.email = etEmail.text.toString().trim()
                user.username = etEmail.text.toString()

                // id of statistic
                stat.team = etTeam.text.toString().trim()
                user.team = stat.team!!

                if (currentUser != null) {
                    // sets the id of the user
                    user.id = currentUser.uid

                    // sets the values into the database
                    Firebase.firestore.collection(getString(R.string.collectionFirebaseUser)
                    ).document(user.id!!).set(user)

                    // sets the values of the result
                    setDefaultStatistics(user.team!!, stat)
                }

                // navigate to home screen
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            } else {
                Log.w("TAG", "Failure cannot register account", task.exception)
                Toast.makeText(this.activity, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    /**
     *  Ensures that id of club is unique
     */
    private fun createRandomID(): Int {
        return Random().nextInt(100000)
    }

    /**
     *  check firestore if id with the name of the team already exists in the database,
     *  if it does not then you can set the result, otherwise do nothing
     */
    private fun setDefaultStatistics(userTeam: String, statistic: Statistic) {
        Firebase.firestore.collection(getString(R.string.collection_path_statistic)).document(userTeam).get().addOnSuccessListener { doc ->
            // if there is no data for the corresponding id, then stats can be added
            if (!doc.exists()) {
                // add empty statistics object for team
                Firebase.firestore.collection("Statistic").document(userTeam).set(statistic)
            }
        }
    }
}