package com.example.myteamapphva.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myteamapphva.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        button.setOnClickListener {
            signIn()
        }
    }


    private fun signIn() {
        firebaseAuth.signInWithEmailAndPassword(etEmail.text.toString().trim(),
                etPassword.text.toString()).addOnCompleteListener {
            task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "User successfully logged in",
                        Toast.LENGTH_SHORT).show();
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


}