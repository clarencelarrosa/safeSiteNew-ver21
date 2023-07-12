package com.example.safesite.about

import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.safesite.MainActivity




class AboutFragment : Fragment() {

    //private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.safesite.R.layout.fragment_about, container, false)
    }
    override fun onResume() {
        super.onResume()
        //this will set the title in the action bar
        (requireActivity() as MainActivity).supportActionBar?.title="About"
    }
}