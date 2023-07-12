package com.example.safesite.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.safesite.MainActivity
import com.example.safesite.R
import com.example.safesite.databinding.FragmentAboutBinding
import com.example.safesite.databinding.FragmentSettingBinding


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_about, container, false)
        binding = FragmentAboutBinding.inflate(layoutInflater)

        /*
        binding.aboutBack.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_aboutFragment_to_settingsFragment)

        }
         */
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        //this will set the title in the action bar
        (requireActivity() as MainActivity).supportActionBar?.title = "About"
    }
}
