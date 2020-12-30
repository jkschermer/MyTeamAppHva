package com.example.myteamapphva.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.myteamapphva.R
import kotlinx.android.synthetic.main.fragment_intro.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class IntroFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        introRegisterBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_registerFragment)
        }

        introLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_loginFragment)
        }
    }
}