package com.example.safesite.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safesite.MainActivity
import com.example.safesite.R


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
    override fun onResume() {
        super.onResume()
        //this will set the title in the action bar
        (requireActivity() as MainActivity).supportActionBar?.title="About"
    }
}