package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emergency_button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_emergencyFragment)
        }

        food_button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_foodFragment)
        }

        medication_button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_medicationFragment)
        }

        settings_icon.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
        }
    }
}
