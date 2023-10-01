package com.example.ui.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.userapp.R
import com.example.userapp.Utility

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if(!Utility.isNetworkAvailable(requireContext()))
        {
            Toast.makeText(requireContext(), "Check your Intenet Connection !", Toast.LENGTH_SHORT).show()
        }
        return inflater.inflate(R.layout.fragment_about, container, false)


    }

    override fun onResume() {
        super.onResume()
        if(!Utility.isNetworkAvailable(requireContext()))
        {
            Toast.makeText(requireContext(), "Check your Intenet Connection !", Toast.LENGTH_SHORT).show()
        }
    }
}